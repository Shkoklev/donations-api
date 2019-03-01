package com.mk.donations.service;

import com.mk.donations.model.OrganizationCategory;

import java.util.List;
import java.util.Optional;

public interface OrganizationCategoryService {

    List<OrganizationCategory> getAllCategories();

    OrganizationCategory saveOrganizationCategory(OrganizationCategory organizationCategory);

    void deleteOrganizationCategoryById(Long id);

    OrganizationCategory updateOrganizationCategory(Long id, String name, String pictureUrl);

}
