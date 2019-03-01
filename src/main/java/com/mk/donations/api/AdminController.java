package com.mk.donations.api;

import com.mk.donations.model.Admin;
import com.mk.donations.service.AdminService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public Admin registerAdmin(@Valid @RequestBody Admin adminRequest) {
        return adminService.saveAdmin(adminRequest.getEmail(), adminRequest.getPassword());
    }

    @GetMapping("/loggedAdmin")
    public Admin getLoggedAdmin(Authentication authentication) {
        return (Admin) authentication.getPrincipal();
    }

    @DeleteMapping("/delete")
    public void deleteAdmin(Authentication authentication) {
        SecurityContextHolder.clearContext();
        Admin admin = getLoggedAdmin(authentication);
        adminService.deleteAdmin(admin);
    }
}
