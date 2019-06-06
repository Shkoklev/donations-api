package com.mk.donations.service.impl;

import com.mk.donations.model.DemandCategory;
import com.mk.donations.model.exception.EntityAlreadyExistsException;
import com.mk.donations.model.exception.EntityNotFoundException;
import com.mk.donations.repository.DemandCategoryRepository;
import com.mk.donations.service.DemandCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandCategoryServiceImpl implements DemandCategoryService {

    private Logger LOGGER = LoggerFactory.getLogger(OrganizationCategoryServiceImpl.class);

    private final DemandCategoryRepository demandCategoryRepository;

    public DemandCategoryServiceImpl(DemandCategoryRepository demandCategoryRepository) {
        this.demandCategoryRepository = demandCategoryRepository;
    }


    @Override
    public List<DemandCategory> getAllCategories() {
        return demandCategoryRepository.findAll();
    }

    @Override
    public DemandCategory saveDemandCategory(String demandCategoryName) {
        demandCategoryRepository.findByName(demandCategoryName)
                .ifPresent((category) -> {
                    throw new EntityAlreadyExistsException("Категорија" + " со име : " + demandCategoryName + " веќе постои.");
                });
        DemandCategory newDemandCategory = new DemandCategory(demandCategoryName);
        return demandCategoryRepository.save(newDemandCategory);
    }

    @Override
    public void deleteDemandCategoryById(Long id) {
        demandCategoryRepository.findById(id)
                .map((category) -> {
                    demandCategoryRepository.deleteById(id);
                    return category;
                })
                .orElseThrow(() -> new EntityNotFoundException("Категорија" + "  со id : " + id + " не постои."));
    }

    @Override
    public DemandCategory updateOrganizationCategory(Long id, String name) {
        return demandCategoryRepository.findById(id)
                .map((category) -> {
                    category.setName(name);
                    return demandCategoryRepository.save(category);
                })
                .orElseThrow(() -> new EntityNotFoundException("Категорија" + "  со id : " + id + " не постои."));
    }
}
