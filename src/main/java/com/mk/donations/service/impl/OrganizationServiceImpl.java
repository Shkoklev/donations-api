package com.mk.donations.service.impl;

import com.mk.donations.model.Demand;
import com.mk.donations.model.Organization;
import com.mk.donations.model.Quantity;
import com.mk.donations.model.exception.EntityAlreadyExistsException;
import com.mk.donations.model.exception.EntityNotFoundException;
import com.mk.donations.repository.DemandPerOrganizationRepository;
import com.mk.donations.repository.OrganizationCategoryRepository;
import com.mk.donations.repository.OrganizationRepository;
import com.mk.donations.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService, UserDetailsService {

    private Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;
    private final DemandPerOrganizationRepository demandPerOrganizationRepository;
    private final OrganizationCategoryRepository organizationCategoryRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, DemandPerOrganizationRepository demandPerOrganizationRepository,
                                   OrganizationCategoryRepository organizationCategoryRepository) {
        this.organizationRepository = organizationRepository;
        this.demandPerOrganizationRepository = demandPerOrganizationRepository;
        this.organizationCategoryRepository = organizationCategoryRepository;
    }

    @Override
    public Page<Organization> getOrganizationsPage(Pageable pageable) {
        return organizationRepository.findAll(pageable);
    }

    @Override
    public Page<Organization> getOrganizationsPageByCategory(Pageable pageable, Long categoryId) {
        return organizationCategoryRepository.findById(categoryId)
                .map((category) -> {
                    return organizationRepository.findAllByCategory_Name(pageable, categoryId);
                })
                .orElseThrow(() -> new EntityAlreadyExistsException("Категорија" + " со id : " + categoryId + " веќе постои."));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return organizationRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Организација со " + id + " не постои."));
    }

    @Override
    public Organization addOrganization(Organization organization) {
        checkOrganizationExistence(organization);
        return organizationRepository.save(organization);
    }

    @Override
    public void addNewDemandToOrganization(Demand demand, Long organizationId, Quantity quantity) {

    }

    @Override
    public void changeExistingDemandQuantity(Long demand, Long organizationId, Quantity quantity) {

    }

    @Override
    public void deleteDemandFromOrganization(Long demandId, Long organizationId) {

    }

    @Override
    public Organization updateOrganization(Long id) {
        return null;
    }

    @Override
    public void deleteOrganization(Long id) {
        organizationRepository.findById(id)
                .map((category) -> {
                    organizationRepository.deleteById(id);
                    return category;
                })
                .orElseThrow(() -> new EntityNotFoundException("Организација" + "  со id : " + id + " не постои."));
    }

    public void checkOrganizationExistence(Organization organization) {
        String email = organization.getEmail();
        String phone = organization.getPhone();
        String name = organization.getName();
        organizationRepository.findByEmail(email)
                .ifPresent((o) -> {
                    throw new EntityAlreadyExistsException("Ваков е-mail веќе постои. ");
                });
        organizationRepository.findByPhone(phone)
                .ifPresent((o) -> {
                    throw new EntityAlreadyExistsException("Овој телефонски број е веќе регистриран. ");
                });
        organizationRepository.findByName(name)
                .ifPresent((o) -> {
                    throw new EntityAlreadyExistsException("Името е зафатено. ");
                });
    }
}
