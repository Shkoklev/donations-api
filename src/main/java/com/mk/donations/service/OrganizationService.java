package com.mk.donations.service;

import com.mk.donations.model.Demand;
import com.mk.donations.model.DemandPerOrganization;
import com.mk.donations.model.Organization;
import com.mk.donations.model.Quantity;
import com.mk.donations.model.dto.OrganizationDemand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface OrganizationService extends UserDetailsService {

    Page<Organization> getOrganizationsPage(Pageable pageable);

    Page<Organization> getOrganizationsPageByCategory(Pageable pageable, Long categoryId);

    UserDetails loadUserByUsername(String email);

    Organization getOrganizationById(Long id);

    List<OrganizationDemand> getDemandsForOrganization(Long id);

    void addDemandToOrganization(String demandName, String demandCategoryName, Long organizationId, Quantity quantity);

    void changeExistingDemandQuantity(Long organizationId, Long demandId, Quantity quantity);

    void deleteDemandFromOrganization(Long demandId, Long organizationId);

    Organization addOrganization(String name, String phone, String email, String password, String categoryName);

    Organization updateOrganization(Long id, String email, String password, String phone);

    void deleteOrganization(Long id);


}
