package com.mk.donations.api;

import com.mk.donations.model.Admin;
import com.mk.donations.model.request.AdminRequest;
import com.mk.donations.service.AdminService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public Admin registerAdmin(@RequestBody AdminRequest adminRequest) {
        return adminService.saveAdmin(adminRequest.email, adminRequest.password);
    }

    @GetMapping("/loggedAdmin")
    public Admin getLoggedAdmin(Authentication authentication) {
        return (Admin) authentication.getPrincipal();
    }
}
