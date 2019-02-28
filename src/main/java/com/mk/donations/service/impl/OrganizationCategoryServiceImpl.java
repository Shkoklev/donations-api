package com.mk.donations.service.impl;

import com.mk.donations.model.OrganizationCategory;
import com.mk.donations.model.exception.EntityAlreadyExistsException;
import com.mk.donations.model.exception.EntityNotFoundException;
import com.mk.donations.repository.OrganizationCategoryRepository;
import com.mk.donations.service.OrganizationCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationCategoryServiceImpl implements OrganizationCategoryService {

    private Logger LOGGER = LoggerFactory.getLogger(OrganizationCategoryServiceImpl.class);

    private final OrganizationCategoryRepository organizationCategoryRepository;

    public OrganizationCategoryServiceImpl(OrganizationCategoryRepository organizationCategoryRepository) {
        this.organizationCategoryRepository = organizationCategoryRepository;
    }

    @Override
    public List<OrganizationCategory> getAllCategories() {
        return organizationCategoryRepository.findAll();
    }

    @Override
    public void deleteOrganizationCategoryById(Long id) {
        organizationCategoryRepository.findById(id)
                .map((category) -> {
                    organizationCategoryRepository.deleteById(id);
                    return category;
                })
                .orElseThrow(() -> new EntityNotFoundException("Категорија" + "  со id : " + id + " не постои."));
    }

    @Override
    public OrganizationCategory saveOrganizationCategory(OrganizationCategory organizationCategory) {
        organizationCategoryRepository.findByName(organizationCategory.getName())
                .ifPresent((s) -> {
                    throw new EntityAlreadyExistsException(("Категорија" + " со име : " + organizationCategory.getName() + " веќе постои."));
                });
        return organizationCategoryRepository.save(organizationCategory);
    }
}