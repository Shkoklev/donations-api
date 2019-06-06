package com.mk.donations.service.impl;

import com.mk.donations.model.Admin;
import com.mk.donations.repository.AdminRepository;
import com.mk.donations.service.AdminService;
import com.mk.donations.service.util.EmailChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final AdminRepository adminRepository;
    private final EmailChecker emailChecker;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, EmailChecker emailChecker, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.emailChecker = emailChecker;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin saveAdmin(String email, String password) {
        emailChecker.checkDuplicateEmail(email);
        Admin admin = new Admin(email);
        admin.setPassword(passwordEncoder.encode(password));
        return adminRepository.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("EMAILOT E : " + email);
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public void deleteAdmin(Admin admin) {
        adminRepository.delete(admin);
    }
}
