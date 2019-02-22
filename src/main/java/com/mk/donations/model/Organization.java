package com.mk.donations.model;

import javax.persistence.*;

@Entity
@Table(name = "organization")
public class Organization {

    @Id
    @SequenceGenerator(name = "organization_id_seq",
            sequenceName = "organization_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "organization_id_seq")
    private Long id;

    private String name;

    private String phone;

    @Column(name="picture_url")
    private String pictureUrl;

    private String email;

    @ManyToOne
    @JoinColumn(name="category")
    private OrganizationCategory organizationCategory;

    public Organization() {}

    public Organization(String name, String phone, String pictureUrl, String email, OrganizationCategory organizationCategory) {
        this.name = name;
        this.phone = phone;
        this.pictureUrl = pictureUrl;
        this.email = email;
        this.organizationCategory = organizationCategory;
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
}
