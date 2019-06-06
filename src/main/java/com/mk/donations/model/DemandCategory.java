package com.mk.donations.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "demand_category")
public class DemandCategory {

    @Id
    @SequenceGenerator(name = "organization_category_id_seq",
            sequenceName = "organization_category_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "organization_category_id_seq")
    private Long id;

    @NotNull(message = "Името не смее да е празно !")
    @NotEmpty(message = "Името не смее да е празно !")
    @Pattern(message = "мора да содржи само мали кирилични букви", regexp = "^[а-шѓѕјќџ]+$")
    private String name;

    public DemandCategory() {}

    public DemandCategory(String name) {
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
