package com.mk.donations.repository;

import com.mk.donations.model.DemandPerOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandPerOrganizationRepository extends JpaRepository<DemandPerOrganization, Long> {

}
