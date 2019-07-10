package com.mk.donations.service.impl;

import com.mk.donations.model.*;
import com.mk.donations.model.exception.*;
import com.mk.donations.repository.*;
import com.mk.donations.service.DonationsService;
import com.mk.donations.service.util.DemandQuantityValidator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DonationServiceImpl implements DonationsService {

    private static final String STATUS_PENDING = "Pending";
    private static final String STATUS_SUCCESSFUL = "Successful";
    private static final String STATUS_DECLINED = "Declined";
    private static final int POINTS_PER_DONATION = 10;

    private final DonorRepository donorRepository;
    private final OrganizationRepository organizationRepository;
    private final DemandRepository demandRepository;
    private final DemandPerOrganizationRepository demandPerOrganizationRepository;
    private final DonationRepository donationRepository;

    public DonationServiceImpl(DonorRepository donorRepository, OrganizationRepository organizationRepository,
                               DemandRepository demandRepository, DemandPerOrganizationRepository demandPerOrganizationRepository,
                               DonationRepository donationRepository) {
        this.donorRepository = donorRepository;
        this.organizationRepository = organizationRepository;
        this.demandRepository = demandRepository;
        this.demandPerOrganizationRepository = demandPerOrganizationRepository;
        this.donationRepository = donationRepository;
    }

    @Override
    @Transactional
    public Donation donate(Long donorId, Long organizationId, Long demandId, Double quantity) {
        if (!DemandQuantityValidator.isDemandQuantityValid(quantity))
            throw new InvalidQuantityException("Погрешно внесена количина");
        Organization organization = getOrganizationIfExists(organizationId);
        Demand demand = getDemandIfExists(demandId);
        Donor donor = getDonorIfExists(donorId);

        checkDonorFailedDonationsLimit(donor);
        checkDonorPendingDonationsLimit(donor);

        return demandPerOrganizationRepository.findByDemand_IdAndOrganizationId(demandId, organizationId)
                .map((demandPerOrganization) -> {
                    return donationRepository.findByOrganization_IdAndDemand_IdAndDonorId(organizationId, demandId, donorId)
                            .map((donation) -> {
                                validateDonationQuantity(demandPerOrganization.getQuantity(), donation.getQuantity() + quantity);
                                donation.setQuantity(donation.getQuantity() + quantity);
                                return donationRepository.save(donation);
                            })
                            .orElseGet(() -> {
                                validateDonationQuantity(demandPerOrganization.getQuantity(), quantity);
                                Donation newDonation = new Donation(quantity, STATUS_PENDING, organization, donor, demand);
                                donor.setNumberOfPendingDonations(donor.getNumberOfPendingDonations() + 1);
                                donorRepository.save(donor);
                                return donationRepository.save(newDonation);
                            });
                })
                .orElseThrow(() -> new EntityNotFoundException("барањето не е пронајдено."));
    }

    @Override
    @Transactional
    public void deleteDonation(Long donorId, Long donationId) {
        Donor donor = getDonorIfExists(donorId);
        donationRepository.findById(donationId)
                .ifPresent((donation) -> {
                    donor.setNumberOfPendingDonations(donor.getNumberOfPendingDonations() - 1);
                    donorRepository.save(donor);
                    donationRepository.deleteById(donationId);
                });
    }

    @Override
    @Transactional
    public void acceptDonation(Long donationId) {
        donationRepository.findById(donationId)
                .ifPresent((donation) -> {
                    if (!donation.getStatus().equals(STATUS_PENDING))
                        return;

                    Long demandId = donation.getDemand().getId();
                    Long organizationId = donation.getOrganization().getId();
                    demandPerOrganizationRepository.findByDemand_IdAndOrganizationId(demandId, organizationId)
                            .map((demandPerOrganization) -> {
                                demandPerOrganization.setQuantity(demandPerOrganization.getQuantity() - donation.getQuantity());
                                return demandPerOrganizationRepository.save(demandPerOrganization);
                            });
                    donation.setStatus(STATUS_SUCCESSFUL);
                    Donor donor = donation.getDonor();
                    donor.setNumberOfPendingDonations(donor.getNumberOfPendingDonations() - 1);
                    donor.setPoints(donor.getPoints() + POINTS_PER_DONATION);

                    donation.setAcceptedOn(LocalDateTime.now());
                    donorRepository.save(donor);
                    donationRepository.save(donation);
                });
    }

    @Override
    @Transactional
    public void declineDonation(Long donationId) {
        donationRepository.findById(donationId)
                .ifPresent((donation) -> {
                    if (!donation.getStatus().equals(STATUS_PENDING))
                        return;

                    Donor donor = donation.getDonor();
                    donor.setNumberOfPendingDonations(donor.getNumberOfPendingDonations() - 1);
                    donorRepository.save(donor);

                    donation.setStatus(STATUS_DECLINED);
                    donationRepository.save(donation);
                });
    }

    @Override
    public List<Donation> getPendingDonationsForOrganization(Long organizationId) {
        return donationRepository.findAllByOrganization_IdAndStatus(organizationId, STATUS_PENDING);
    }

    @Override
    public List<Donation> getSuccessfulDonationsForOrganization(Long organizationId) {
        return donationRepository.findAllByOrganization_IdAndStatus(organizationId, STATUS_SUCCESSFUL);
    }

    @Override
    public List<Donation> getDeclinedDonationsForOrganization(Long organizationId) {
        return donationRepository.findAllByOrganization_IdAndStatus(organizationId, STATUS_DECLINED);
    }

    @Override
    public List<Donation> getPendingDonationsForDonor(Long donorId) {
        return donationRepository.findAllByDonor_IdAndStatus(donorId, STATUS_PENDING);
    }

    @Override
    public List<Donation> getSuccessfulDonationsForDonor(Long donorId) {
        return donationRepository.findAllByDonor_IdAndStatus(donorId, STATUS_SUCCESSFUL);
    }

    @Override
    public List<Donation> getDeclinedDonationsForDonor(Long donorId) {
        return donationRepository.findAllByDonor_IdAndStatus(donorId, STATUS_DECLINED);
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 22 * * * *")
    public void removePendingDonations() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        donationRepository.findAllByStatus(STATUS_PENDING)
                .stream()
                .filter(donation -> currentDateTime.compareTo(donation.getValidUntil()) >= 0)
                .forEach((donation) -> {
                    Donor donor = donation.getDonor();
                    donor.setNumberOfPendingDonations(donor.getNumberOfPendingDonations() - 1);
                    donor.setFailedConsecutiveDonations(donor.getFailedConsecutiveDonations() + 1);
                    donorRepository.save(donor);
                    donationRepository.delete(donation);
                });

    }

    public Organization getOrganizationIfExists(Long id) {
        return this.organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("организација со id " + id + " не постои"));
    }

    public Demand getDemandIfExists(Long id) {
        return this.demandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Објектот не постои"));
    }

    public Donor getDonorIfExists(Long id) {
        return this.donorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Корисникот не постои"));
    }

    public void validateDonationQuantity(Double organizationDemandQuantity, Double donationQuantity) {
        if (organizationDemandQuantity < donationQuantity)
            throw new DonationRangeOutOfBoundException("не можете да донирате повеќе од потребното");
    }

    public void checkDonorPendingDonationsLimit(Donor donor) {
        if (donor.getNumberOfPendingDonations() > 10)
            throw new PendingDonationsLimitExceeded("не може истовремено да имате повеќе од 10 барања за донации");
    }

    public void checkDonorFailedDonationsLimit(Donor donor) {
        if (donor.getFailedConsecutiveDonations() >= 5)
            throw new FailedDonationsLimitExceeded("повеќе не можете да донирате бидејќи имавте 5 неуспешни обиди !");
    }
}
