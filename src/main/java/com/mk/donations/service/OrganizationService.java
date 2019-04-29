package com.mk.donations.service;

import com.mk.donations.model.Demand;
import com.mk.donations.model.Organization;
import com.mk.donations.model.Quantity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface OrganizationService {

    Page<Organization> getOrganizationsPage(Pageable pageable);

    Page<Organization> getOrganizationsPageByCategory(Pageable pageable, Long categoryId);

    UserDetails loadUserByUsername(String email);

    Organization getOrganizationById(Long id);

    void addNewDemandToOrganization(Demand demand, Long organizationId, Quantity quantity);

    void changeExistingDemandQuantity(Long demandId, Long organizationId, Quantity quantity);

    void deleteDemandFromOrganization(Long demandId, Long organizationId);

    Organization addOrganization(Organization organization);

    Organization updateOrganization(Long id);

    void deleteOrganization(Long id);


}
