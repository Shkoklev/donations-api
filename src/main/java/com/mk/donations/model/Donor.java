package com.mk.donations.model;

import javax.persistence.*;

@Entity
@Table(name = "donor")
public class Donor {

    @Id
    @SequenceGenerator(name = "donor_id_seq",
            sequenceName = "donor_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "donor_id_seq")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(name = "picture_url")
    private String pictureUrl;

    private int points;

    private int failedConsecutiveDonations;

    public Donor() {
    }

    public Donor(String firstName, String lastName, String email, String password, String phone, String pictureUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.pictureUrl = pictureUrl;
        this.points = 0;
        this.failedConsecutiveDonations = 0;
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
}
