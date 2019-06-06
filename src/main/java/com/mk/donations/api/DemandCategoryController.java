package com.mk.donations.api;

import com.mk.donations.model.DemandCategory;
import com.mk.donations.service.DemandCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/demand_categories")
public class DemandCategoryController {

    private final DemandCategoryService demandCategoryService;

    public DemandCategoryController(DemandCategoryService demandCategoryService) {
        this.demandCategoryService = demandCategoryService;
    }

    @GetMapping
    public List<DemandCategory> getCategories() {
        return demandCategoryService.getAllCategories();
    }

    @DeleteMapping("/{id}")
    public void deleteDemandCategoryById(@PathVariable Long id) {
        demandCategoryService.deleteDemandCategoryById(id);
    }

    @PostMapping
    public DemandCategory saveOrganizationCategory(@Valid @RequestBody DemandCategory demandCategory) {
        return demandCategoryService.saveDemandCategory(demandCategory.getName());
    }

    @PutMapping("/{id}")
    public DemandCategory updateOrganizationCategory(@PathVariable Long id, @Valid @RequestBody DemandCategory demandCategory) {
        return demandCategoryService.updateOrganizationCategory(id, demandCategory.getName());
    }


}
