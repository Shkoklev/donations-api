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
    public OrganizationCategory saveOrganizationCategory(OrganizationCategory organizationCategory) {
        organizationCategoryRepository.findByName(organizationCategory.getName())
                .ifPresent((category) -> {
                    throw new EntityAlreadyExistsException("Категорија" + " со име : " + organizationCategory.getName() + " веќе постои.");
                });
        return organizationCategoryRepository.save(organizationCategory);
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
    public OrganizationCategory updateOrganizationCategory(Long id, String name, String pictureUrl) {
        return organizationCategoryRepository.findById(id)
                .map((category) -> {
                    if (name != null && !name.isEmpty())
                        category.setName(name);
                    if (pictureUrl != null && !pictureUrl.isEmpty())
                        category.setPictureUrl(pictureUrl);
                    return organizationCategoryRepository.save(category);
                })
                .orElseThrow(() -> new EntityNotFoundException("Категорија" + "  со id : " + id + " не постои."));
    }
}