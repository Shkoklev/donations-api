package com.mk.donations.repository;

import com.mk.donations.model.DemandPerOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DemandPerOrganizationRepository extends JpaRepository<DemandPerOrganization, Long> {

    Optional<DemandPerOrganization> findByDemand_IdAndOrganizationId(Long demandId, Long organizationId);

    List<DemandPerOrganization> findByOrganization_Id(Long id);

}
