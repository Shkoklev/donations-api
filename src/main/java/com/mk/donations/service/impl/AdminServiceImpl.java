package com.mk.donations.service.impl;

import com.mk.donations.model.Admin;
import com.mk.donations.repository.AdminRepository;
import com.mk.donations.repository.DonorRepository;
import com.mk.donations.repository.OrganizationRepository;
import com.mk.donations.service.AdminService;
import com.mk.donations.service.util.EmailChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService, UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final AdminRepository adminRepository;
    private final DonorRepository donorRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, DonorRepository donorRepository,
                            OrganizationRepository organizationRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.donorRepository = donorRepository;
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin saveAdmin(String email, String password) {
        EmailChecker.checkDuplicateEmail(adminRepository, donorRepository, organizationRepository, email);
        Admin admin = new Admin(email);
        admin.setPassword(passwordEncoder.encode(password));
        return adminRepository.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
