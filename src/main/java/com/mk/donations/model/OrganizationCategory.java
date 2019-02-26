package com.mk.donations.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "organization_category")
public class OrganizationCategory {

    @Id
    @SequenceGenerator(name = "organization_category_id_seq",
            sequenceName = "organization_category_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "organization_category_id_seq")
    private Long id;

    @NotNull(message = "Името не смее да е празно !")
    @NotEmpty(message = "Името не смее да е празно !")
    private String name;

    @NotNull(message = "Url-то од сликата не смее да е празно !")
    @NotEmpty(message = "Url-то од сликата не смее да е празно !")
    @Column(name = "picture_url")
    private String pictureUrl;

    public OrganizationCategory() {
    }

    public OrganizationCategory(String name, String pictureUrl) {
        this.name = name;
        this.pictureUrl = pictureUrl;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
