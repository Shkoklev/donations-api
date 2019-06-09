package com.mk.donations.service.impl;

import com.mk.donations.model.Demand;
import com.mk.donations.model.Donation;
import com.mk.donations.model.Donor;
import com.mk.donations.model.Organization;
import com.mk.donations.model.exception.DonationRangeOutOfBoundException;
import com.mk.donations.model.exception.EntityNotFoundException;
import com.mk.donations.model.exception.PendingDonationsLimitExceeded;
import com.mk.donations.repository.*;
import com.mk.donations.service.DonationsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DonationServiceImpl implements DonationsService {

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
        Organization organization = getOrganizationIfExists(organizationId);
        Demand demand = getDemandIfExists(demandId);
        Donor donor = getDonorIfExists(donorId);

        checkDonorPendingDonationsLimit(donor);

        return demandPerOrganizationRepository.findByDemand_IdAndOrganizationId(demandId, organizationId)
                .map((demandPerOrganization) -> {
                    validateDonationQuantity(demandPerOrganization.getQuantity(), quantity);
                    String status = "Pending";
                    Donation donation = new Donation(quantity, status, organization, donor, demand);
                    donor.setNumberOfPendingDonations(donor.getNumberOfPendingDonations()+1);
                    return donationRepository.save(donation);
                })
                .orElseThrow(() -> new EntityNotFoundException("барањето не е пронајдено."));

    }

    @Override
    public void acceptDonation(Long donationId) {

    }

    @Override
    public void declineDonation(Long donationId) {

    }

    @Override
    public List<Donation> getPendingDonationsForOrganization(Long organizationId) {
        String status = "Pending";
        return donationRepository.findByOrganization_IdAndStatus(organizationId,status);
    }

    @Override
    public List<Donation> getSuccessfulDonationsForOrganization(Long organizationId) {
        String status = "Success";
        return donationRepository.findByOrganization_IdAndStatus(organizationId,status);
    }

    @Override
    public void removeDonation(Long donationId) {

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
        if(donor.getNumberOfPendingDonations() > 10)
            throw new PendingDonationsLimitExceeded("не може истовремено да имате повеќе од 10 барања за донации");
    }
}