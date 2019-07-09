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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public Donor getLoggedDonor(Authentication authentication) {
        Long id = Long.valueOf(authentication.getDetails().toString());
        return donorService.getDonorById(id);
    }

    @GetMapping
    public Page<Donor> getUsers(Pageable pageable) {
        return donorService.getDonorsPage(pageable);
    }

//    @GetMapping("/{donorId}")
//    public Donor getDonorById(@PathVariable Long donorId) {
//        return donorService.getDonorById(donorId);
//    }

    @PostMapping("/register")
    public Donor register(@Valid @RequestBody Donor donor) {
        return donorService.addDonor(donor);
    }

    @PutMapping("/{donorId}")
    public ResponseEntity<Donor> updateDonor(@Valid @RequestBody EditDonorRequest editDonorRequest, @PathVariable Long donorId, Authentication authentication) {
        long id = Long.valueOf(authentication.getDetails().toString());
        if (id != donorId) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        checkForEmptyRequest(editDonorRequest);
        String firstName = editDonorRequest.firstName;
        String lastName = editDonorRequest.lastName;
        String email = editDonorRequest.email;
        String password = editDonorRequest.password;
        String phone = editDonorRequest.phone;
        String pictureUrl = editDonorRequest.pictureUrl;
        Donor donor = this.donorService.updateDonor(donorId, firstName, lastName, email, password, phone, pictureUrl);
        return new ResponseEntity<>(donor, HttpStatus.OK);
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
    public ResponseEntity<Donation> donate(@PathVariable Long donorId,
                                           @PathVariable Long organizationId,
                                           @PathVariable Long demandId,
                                           @Valid @RequestBody QuantityRequest quantityRequest,
                                           Authentication authentication) {
        long id = Long.valueOf(authentication.getDetails().toString());
        if (id != donorId) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Donation donation = donationService.donate(donorId, organizationId, demandId, quantityRequest.quantity);
        return new ResponseEntity<>(donation, HttpStatus.OK);
    }

    @GetMapping("/{donorId}/successful_donations")
    public ResponseEntity<List<Donation>> getSuccessfulDonations(@PathVariable Long donorId, Authentication authentication) {
        long id = Long.valueOf(authentication.getDetails().toString());
        if (id != donorId) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Donation> donations = donationService.getSuccessfulDonationsForDonor(donorId);
        return new ResponseEntity<>(donations, HttpStatus.OK);
    }

    @GetMapping("/{donorId}/pending_donations")
    public ResponseEntity<List<Donation>> getPendingDonations(@PathVariable Long donorId, Authentication authentication) {
        long id = Long.valueOf(authentication.getDetails().toString());
        if (id != donorId) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Donation> donations = donationService.getPendingDonationsForDonor(donorId);
        return new ResponseEntity<>(donations, HttpStatus.OK);
    }

    @GetMapping("/{donorId}/declined_donations")
    public ResponseEntity<List<Donation>> getDeclinedDonations(@PathVariable Long donorId, Authentication authentication) {
        long id = Long.valueOf(authentication.getDetails().toString());
        if (id != donorId) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Donation> donations = donationService.getDeclinedDonationsForDonor(donorId);
        return new ResponseEntity<>(donations, HttpStatus.OK);
    }
}
