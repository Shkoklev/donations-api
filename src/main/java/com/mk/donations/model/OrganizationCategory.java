package com.mk.donations.model;

import javax.persistence.*;

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

    private String name;

    public OrganizationCategory() {}

    public OrganizationCategory(String name) {
        this.name = name;
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
}
