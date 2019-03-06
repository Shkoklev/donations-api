package com.mk.donations.service.impl;

import com.mk.donations.model.Organization;
import com.mk.donations.repository.DemandPerOrganizationRepository;
import com.mk.donations.repository.OrganizationRepository;
import com.mk.donations.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;
    private final DemandPerOrganizationRepository demandPerOrganizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, DemandPerOrganizationRepository demandPerOrganizationRepository) {
        this.organizationRepository = organizationRepository;
        this.demandPerOrganizationRepository = demandPerOrganizationRepository;
    }

    @Override
    public Page<Organization> getOrganizationsPage(Pageable pageable) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return null;
    }

    @Override
    public Organization getOrganizationById(Long id) {
        return null;
    }

    @Override
    public Organization addOrganization(Organization organization) {
        return null;
    }

    @Override
    public Organization updateOrganization(Long id) {
        return null;
    }

    @Override
    public void deleteOrganization(Long id) {

    }
}
