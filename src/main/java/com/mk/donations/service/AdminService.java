package com.mk.donations.service;

import com.mk.donations.model.Admin;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface AdminService {

    Admin saveAdmin(String email, String password);

    UserDetails loadUserByUsername(String email);

    void deleteAdmin(Admin admin);
}
