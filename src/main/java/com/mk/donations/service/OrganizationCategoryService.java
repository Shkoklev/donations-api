package com.mk.donations.service;

import com.mk.donations.model.OrganizationCategory;

import java.util.List;
import java.util.Optional;

public interface OrganizationCategoryService {

    List<OrganizationCategory> getAllCategories();

    void deleteOrganizationCategoryById(Long id);

    OrganizationCategory saveOrganizationCategory(OrganizationCategory organizationCategory);

}
