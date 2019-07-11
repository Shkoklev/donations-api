package com.mk.donations.api;

import com.mk.donations.model.Demand;
import com.mk.donations.model.request.OrganizationDemandRequest;
import com.mk.donations.repository.MailSenderRepository;
import com.mk.donations.service.DemandService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin({"*", "localhost:3000"})
@RestController
@RequestMapping("/demands")
public class DemandController {

    @Value("${admin.email}")
    private String adminEmail;

    private final DemandService demandService;
    private final MailSenderRepository mailSenderRepository;

    public DemandController(DemandService demandService, MailSenderRepository mailSenderRepository) {
        this.demandService = demandService;
        this.mailSenderRepository = mailSenderRepository;
    }

    @GetMapping
    public List<Demand> getAllDemands() {
        return demandService.getAllDemands();
    }

    @GetMapping("/{demandId}")
    public Demand getDemandById(@PathVariable Long demandId) {
        return demandService.getDemandById(demandId);
    }

    @GetMapping("/by_category/{categoryId}")
    public List<Demand> getAllByCategoryId(@PathVariable Long categoryId) {
        return demandService.getAllDemandsByCategoryId(categoryId);
    }

    @PostMapping("/new")
    public ResponseEntity<Void> registerDemand(@Valid @RequestBody OrganizationDemandRequest demandRequest) {
        System.out.println("gomna");
        String to = adminEmail;
        String subject = "Нова потреба за: " + demandRequest.organizationName;
        String message = demandRequest.organizationName + " сака да додаде " + demandRequest.quantity + " X " + demandRequest.demandName;
        this.mailSenderRepository.sentMail(to,subject,message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping
//    public Demand saveDemand(@Valid @RequestBody DemandRequest demandRequest) {
//        String demandName = demandRequest.name;
//        String demandCategory = demandRequest.category;
//        String demandUnitName = demandRequest.unitName;
//        return demandService.saveDemand(demandName, demandCategory, demandUnitName);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteDemandById(@PathVariable Long id) {
//        demandService.deleteDemandById(id);
//    }
//
//    @PutMapping("/{id}")
//    public Demand updateDemand(@PathVariable Long id, @Valid @RequestBody Demand demand) {
//        return demandService.updateDemand(id, demand.getName());
//    }
//
//    @PostMapping("/link_to_category")
//    public void linkDemandToCategory(@Valid @RequestBody DemandCategoryRequest request) {
//        demandService.linkDemandToCategory(request.demandName, request.demandCategoryName);
//    }
}
