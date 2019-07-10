package com.mk.donations.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "donor")
public class Donor implements UserDetails {

    @Id
    @SequenceGenerator(name = "donor_id_seq",
            sequenceName = "donor_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "donor_id_seq")
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotNull(message = "Името не смее да е празно !")
    @NotEmpty(message = "Името не смее да е празно !")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotNull(message = "Презимето не смее да е празно !")
    @NotEmpty(message = "Презимето не смее да е празно !")
    private String lastName;

    @Column(unique = true, nullable = false)
    @Email(message = "Невалиден формат на адреса")
    @NotNull(message = "Внесете Е-mail !")
    @NotEmpty(message = "Внесете Е-mail !")
    private String email;

    @Column(unique = true, nullable = false)
    @Size(min = 8, max = 100, message = "Пасвордот мора да содржи барем 8 карактери")
    @NotNull(message = "Внесете пасворд !")
    @NotEmpty(message = "Внесете пасворд !")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(unique = true, nullable = false)
    @Pattern(message = "Телефонскиот број мора да содржи само бројки", regexp = "^[0-9]+$")
    @NotNull(message = "Телефонскиот број не смее да е празен !")
    @NotEmpty(message = "Телефонскиот број не смее да е празен !")
    private String phone;

    @Column(name = "picture_url")
    private String pictureUrl;

    private int points;

    @Column(name = "failed_consecutive_donations")
    private int failedConsecutiveDonations;

    @Column(name = "number_of_current_pending_donations")
    private int numberOfCurrentPendingDonations;

    public Donor() {
    }

    public Donor(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.points = 0;
        this.failedConsecutiveDonations = 0;
        this.numberOfCurrentPendingDonations = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getFailedConsecutiveDonations() {
        return failedConsecutiveDonations;
    }

    public void setFailedConsecutiveDonations(int failedConsecutiveDonations) {
        this.failedConsecutiveDonations = failedConsecutiveDonations;
    }

    public int getNumberOfPendingDonations() {
        return numberOfCurrentPendingDonations;
    }

    public void setNumberOfPendingDonations(int numberOfPendingDonations) {
        this.numberOfCurrentPendingDonations = numberOfPendingDonations;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("DONOR"));
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

    @Override
    public String toString() {
        return "Donor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", points=" + points +
                ", failedConsecutiveDonations=" + failedConsecutiveDonations +
                ", numberOfCurrentPendingDonations=" + numberOfCurrentPendingDonations +
                '}';
    }
}
