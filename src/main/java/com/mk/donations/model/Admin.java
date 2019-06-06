package com.mk.donations.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "administrator")
public class Admin implements UserDetails {

    @Id
    @SequenceGenerator(name = "administrator_id_seq",
            sequenceName = "administrator_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "administrator_id_seq")
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Email-Адресата не смее да е празна !")
    @NotEmpty(message = "Email-Адресата не смее да е празна !")
    @Email(message = "Невалиден формат на адреса")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "Пасвордот не смее да е празен !")
    @NotEmpty(message = "Пасвордот не смее да е празен !")
    @Size(min = 8, max = 100, message = "Пасвордот мора да содржи помеѓу 8 и 30 карактери")
    private String password;

    public Admin() {
    }

    public Admin(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
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
