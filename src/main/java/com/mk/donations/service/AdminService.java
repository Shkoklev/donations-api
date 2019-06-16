package com.mk.donations.service;

import com.mk.donations.model.Admin;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface AdminService extends UserDetailsService {

    Admin saveAdmin(String email, String password);

    UserDetails loadUserByUsername(String email);

    Admin getById(Long id);

    void deleteAdmin(Admin admin);
}
