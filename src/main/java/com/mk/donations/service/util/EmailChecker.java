package com.mk.donations.service.util;

import com.mk.donations.model.exception.EntityAlreadyExistsException;
import com.mk.donations.repository.AdminRepository;
import com.mk.donations.repository.DonorRepository;
import com.mk.donations.repository.OrganizationRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityExistsException;

@Component
public final class EmailChecker {

    private final AdminRepository adminRepository;
    private final OrganizationRepository organizationRepository;
    private final DonorRepository donorRepository;

    public EmailChecker(AdminRepository adminRepository, OrganizationRepository organizationRepository,
                         DonorRepository donorRepository) {
        this.adminRepository = adminRepository;
        this.organizationRepository = organizationRepository;
        this.donorRepository = donorRepository;
    }

    public void checkDuplicateEmail(String email) {
        boolean emailExists = adminRepository.existsByEmail(email) || donorRepository.existsByEmail(email)
                || organizationRepository.existsByEmail(email);
            if(emailExists)
                throw new EntityAlreadyExistsException("Корисник со email: " + email + " веќе постои.");
    }
}
