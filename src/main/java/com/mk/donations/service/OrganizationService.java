package com.mk.donations.service;

import com.mk.donations.model.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface OrganizationService {

    Page<Organization> getOrganizationsPage(Pageable pageable);

    UserDetails loadUserByUsername(String email);

    Organization getOrganizationById(Long id);

    Organization addOrganization(Organization organization);

    Organization updateOrganization(Long id);

    void deleteOrganization(Long id);


}
