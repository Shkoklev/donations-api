package com.mk.donations.api;

import com.mk.donations.model.Donation;
import com.mk.donations.model.Donor;
import com.mk.donations.model.exception.ParameterMissingException;
import com.mk.donations.model.request.EditDonorRequest;
import com.mk.donations.model.request.QuantityRequest;
import com.mk.donations.service.DonationsService;
import com.mk.donations.service.DonorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin({"*", "localhost:3000"})
@RestController
@RequestMapping("/donors")
public class DonorController {

    private final DonorService donorService;
    private final DonationsService donationService;

    public DonorController(DonorService donorService, DonationsService donationService) {
        this.donorService = donorService;
        this.donationService = donationService;
    }

    @GetMapping("/loggedDonor")
    public Donor getLoggedOrganization(Authentication authentication) {
        return (Donor) authentication.getPrincipal();
    }

    @GetMapping
    public Page<Donor> getUsers(Pageable pageable) {
        return donorService.getDonorsPage(pageable);
    }

    @GetMapping("/{donorId}")
    public Donor getDonorById(@PathVariable Long donorId) {
        return donorService.getDonorById(donorId);
    }

    @PostMapping
    public Donor addDonor(@Valid @RequestBody Donor donor) {
        return donorService.addDonor(donor);
    }

    @PutMapping("/{donorId}")
    public Donor updateDonor(@Valid @RequestBody EditDonorRequest editDonorRequest, @PathVariable Long donorId) {
        checkForEmptyRequest(editDonorRequest);
        String firstName = editDonorRequest.firstName;
        String lastName = editDonorRequest.lastName;
        String email = editDonorRequest.email;
        String password = editDonorRequest.password;
        String phone = editDonorRequest.phone;
        String pictureUrl = editDonorRequest.pictureUrl;
        return this.donorService.updateDonor(donorId, firstName, lastName, email, password, phone, pictureUrl);
    }

    @DeleteMapping("/{donorId}")
    public void deleteDonor(@PathVariable Long donorId) {
        this.donorService.deleteDonor(donorId);
    }

    private void checkForEmptyRequest(EditDonorRequest request) {
        if ((request.email == null || request.email.isEmpty()) && (request.password == null || request.password.isEmpty())
                && (request.phone == null || request.phone.isEmpty()) && (request.firstName == null || request.firstName.isEmpty())
                && (request.lastName == null || request.lastName.isEmpty()) && (request.pictureUrl == null || request.pictureUrl.isEmpty()))
            throw new ParameterMissingException("Внесете барем едно поле. ");
    }

    @PostMapping("/{donorId}/donate_to_organization/{organizationId}/for_demand/{demandId}")
    public Donation donate(@PathVariable Long donorId, @PathVariable Long organizationId, @PathVariable Long demandId, @Valid @RequestBody QuantityRequest quantityRequest) {
        return donationService.donate(donorId, organizationId, demandId, quantityRequest.quantity);
    }
}
