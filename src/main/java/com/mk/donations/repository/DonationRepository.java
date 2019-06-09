package com.mk.donations.repository;

import com.mk.donations.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findByOrganization_IdAndStatus(Long organizationId, String status);
}
