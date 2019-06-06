package com.mk.donations.service;

import com.mk.donations.model.DemandCategory;

import java.util.List;

public interface DemandCategoryService {

    List<DemandCategory> getAllCategories();

    DemandCategory saveDemandCategory(String demandCategoryName);

    void deleteDemandCategoryById(Long id);

    DemandCategory updateOrganizationCategory(Long id, String name);
}
