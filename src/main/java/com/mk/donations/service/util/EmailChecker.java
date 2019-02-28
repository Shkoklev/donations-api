package com.mk.donations.service.util;

import com.mk.donations.repository.AdminRepository;
import com.mk.donations.repository.DonorRepository;
import com.mk.donations.repository.OrganizationRepository;

import javax.persistence.EntityExistsException;

public final class EmailChecker {

    private EmailChecker() {
    }

    public static void checkDuplicateEmail(AdminRepository adminRepository, DonorRepository donorRepository,
                                    OrganizationRepository organizationRepository, String email) {
        boolean emailExists = adminRepository.existsByEmail(email) || donorRepository.existsByEmail(email)
                || organizationRepository.existsByEmail(email);
            if(emailExists)
                throw new EntityExistsException("Корисник со email: " + email + " веќе постои.");
    }
}
