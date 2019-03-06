package com.mk.donations.api;

import com.mk.donations.model.Demand;
import com.mk.donations.service.DemandService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/demands")
public class DemandController {

    private final DemandService demandService;

    public DemandController(DemandService demandService) {
        this.demandService = demandService;
    }

    @GetMapping
    public List<Demand> getAllDemands() {
        return demandService.getAllDemands();
    }

    @PostMapping
    public Demand saveDemand(@Valid @RequestBody Demand demand) {
        return demandService.saveDemand(demand);
    }

    @DeleteMapping("/{id}")
    public void deleteDemandById(@PathVariable Long id) {
        demandService.deleteDemandById(id);
    }

    @PutMapping("/{id}")
    public Demand updateDemand(@PathVariable Long id,@Valid @RequestBody Demand demand) {
        return demandService.updateDemand(id,demand.getName());
    }
}