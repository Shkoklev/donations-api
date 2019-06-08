package com.mk.donations.service.impl;

import com.mk.donations.model.*;
import com.mk.donations.model.dto.OrganizationDemand;
import com.mk.donations.model.exception.DemandCategoryMismatchException;
import com.mk.donations.model.exception.EntityAlreadyExistsException;
import com.mk.donations.model.exception.EntityNotFoundException;
import com.mk.donations.model.exception.InvalidQuantityException;
import com.mk.donations.repository.DemandPerOrganizationRepository;
import com.mk.donations.repository.DemandRepository;
import com.mk.donations.repository.OrganizationCategoryRepository;
import com.mk.donations.repository.OrganizationRepository;
import com.mk.donations.service.OrganizationService;
import com.mk.donations.service.util.DemandQuantityValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;
    private final DemandPerOrganizationRepository demandPerOrganizationRepository;
    private final OrganizationCategoryRepository organizationCategoryRepository;
    private final DemandRepository demandRepository;

    private final PasswordEncoder passwordEncoder;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, DemandPerOrganizationRepository demandPerOrganizationRepository,
                                   OrganizationCategoryRepository organizationCategoryRepository, DemandRepository demandRepository,
                                   PasswordEncoder passwordEncoder) {
        this.organizationRepository = organizationRepository;
        this.demandPerOrganizationRepository = demandPerOrganizationRepository;
        this.organizationCategoryRepository = organizationCategoryRepository;
        this.demandRepository = demandRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<Organization> getOrganizationsPage(Pageable pageable) {
        return organizationRepository.findAll(pageable);
    }

    @Override
    public Page<Organization> getOrganizationsPageByCategory(Pageable pageable, Long categoryId) {
        return organizationCategoryRepository.findById(categoryId)
                .map((category) -> {
                    return organizationRepository.findAllByCategory_Name(pageable, category.getName());
                })
                .orElseThrow(() -> new EntityNotFoundException("Категорија" + " со id : " + categoryId + " не постои."));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return organizationRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Организација со id  " + id + " не постои."));
    }

    @Override
    public Organization addOrganization(String name, String phone, String email, String password, String categoryName) {
        checkOrganizationExistence(email,phone,name);
        checkCategoryExistence(categoryName);
        OrganizationCategory organizationCategory = organizationCategoryRepository.findByName(categoryName).get();
        Organization organization = new Organization(name,phone,email, organizationCategory);
        organization.setPassword(passwordEncoder.encode(password));
        return organizationRepository.save(organization);
    }

    @Override
    public void addDemandToOrganization(String demandName, String demandCategoryName, Long organizationId, Quantity quantity) {
        if (!DemandQuantityValidator.isDemandQuantityValid(quantity))
            throw new InvalidQuantityException("Погрешно внесена количина");
        if (!demandRepository.existsByName(demandName))
            throw new EntityNotFoundException("Објект со име: " + demandName + " не постои");
        if (!organizationRepository.existsById(organizationId))
            throw new EntityNotFoundException("Организација со id: " + organizationId + " не постои");

        Demand demand = demandRepository.findByName(demandName).get();
        if(!demand.getCategory().getName().equals(demandCategoryName))
            throw new DemandCategoryMismatchException("ова барање не влегува во оваа категорија");

        demandPerOrganizationRepository.findByDemand_IdAndOrganizationId(demand.getId(), organizationId)
                .map((demandPerOrganization) -> {
                    demandPerOrganization.setQuantity(demandPerOrganization.getQuantity().add(quantity));
                    return demandPerOrganizationRepository.save(demandPerOrganization);
                }).orElseGet(() -> {
            Organization organization = organizationRepository.findById(organizationId).get();
            DemandPerOrganization demandPerOrganization = new DemandPerOrganization(organization, demand, quantity);
            return demandPerOrganizationRepository.save(demandPerOrganization);
        });
    }

    @Override
    public void changeExistingDemandQuantity(Long organizationId, Long demandId, Quantity quantity) {
        if (!DemandQuantityValidator.isDemandQuantityValid(quantity))
            throw new InvalidQuantityException("Погрешно внесена количина");
        demandPerOrganizationRepository.findByDemand_IdAndOrganizationId(demandId,organizationId)
                .map((demandPerOrganization) -> {
                    demandPerOrganization.setQuantity(quantity);
                    return demandPerOrganizationRepository.save(demandPerOrganization);
                })
                .orElseThrow(() -> new EntityNotFoundException("објектот не постои"));
    }

    @Override
    public void deleteDemandFromOrganization(Long demandId, Long organizationId) {
        demandPerOrganizationRepository.findByDemand_IdAndOrganizationId(demandId, organizationId)
                .map((demandPerOrganization) -> {
                    demandPerOrganizationRepository.delete(demandPerOrganization);
                    return demandPerOrganization;
                })
                .orElseThrow(() -> new EntityNotFoundException("Објектот не е пронајден"));
    }

    @Override
    public Organization updateOrganization(Long id, String email, String password, String phone) {
        return organizationRepository.findById(id)
                .map((organization) -> {
                    if (email != null && !email.isEmpty())
                        organization.setEmail(email);
                    if (password != null && !password.isEmpty())
                        organization.setPassword(passwordEncoder.encode(password));
                    if (phone != null && !phone.isEmpty())
                        organization.setPhone(phone);
                    return organizationRepository.save(organization);
                })
                .orElseThrow(() -> new EntityNotFoundException("Организација" + "  со id : " + id + " не постои."));
    }

    @Override
    public List<OrganizationDemand> getDemandsForOrganization(Long id) {
        if(!organizationRepository.existsById(id))
            throw new EntityNotFoundException("Организацијата не постои");
        return demandPerOrganizationRepository.findByOrganization_Id(id)
                .stream()
                .map((demandPerOrganization) -> {
                    OrganizationDemand organizationDemand = new OrganizationDemand(demandPerOrganization.getDemand(),demandPerOrganization.getQuantity());
                    return organizationDemand;
                })
                .collect(Collectors.toList());
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

    public void checkOrganizationExistence(String email, String phone, String name) {
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

    public void checkCategoryExistence(String categoryName) {
        organizationCategoryRepository.findByName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException("Категорија со име : " + categoryName + " не постои!"));
    }
}
