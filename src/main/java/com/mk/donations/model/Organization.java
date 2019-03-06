package com.mk.donations.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "organization")
public class Organization implements UserDetails {

    @Id
    @SequenceGenerator(name = "organization_id_seq",
            sequenceName = "organization_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "organization_id_seq")
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Името не смее да е празно !")
    @NotEmpty(message = "Името не смее да е празно !")
    private String name;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Телефонскиот број не смее да е празен !")
    @NotEmpty(message = "Телефонскиот број не смее да е празен !")
    private String phone;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Внесете Е-mail !")
    @NotEmpty(message = "Внесете Е-mail !")
    private String email;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Внесете пасворд !")
    @NotEmpty(message = "Внесете пасворд !")
    private String password;

    @ManyToOne
    @JoinColumn(name = "category")
    private OrganizationCategory organizationCategory;

    public Organization() {
    }

    public Organization(String name, String phone, String pictureUrl, String email, OrganizationCategory organizationCategory) {
        this.name = name;
        this.phone = phone;
        this.pictureUrl = pictureUrl;
        this.email = email;
        this.organizationCategory = organizationCategory;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OrganizationCategory getOrganizationCategory() {
        return organizationCategory;
    }

    public void setOrganizationCategory(OrganizationCategory organizationCategory) {
        this.organizationCategory = organizationCategory;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ORGANIZATION"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
