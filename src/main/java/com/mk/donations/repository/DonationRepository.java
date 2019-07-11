package com.mk.donations.repository;

import com.mk.donations.model.Donation;
import com.mk.donations.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findAllByOrganization_IdAndStatus(Long organizationId, String status);

    List<Donation> findAllByDonor_IdAndStatus(Long donorId, String status);

    Optional<Donation> findByOrganization_IdAndDemand_IdAndDonorId(Long organizationId, Long demandId, Long donorId);

    List<Donation> findAllByStatus(String status);

}
