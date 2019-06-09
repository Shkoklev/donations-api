package com.mk.donations.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "demand")
public class Demand {

    @Id
    @SequenceGenerator(name = "demand_id_seq",
            sequenceName = "demand_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "demand_id_seq")
    private Long id;

    @NotNull(message = "не смее да е празно !")
    @NotEmpty(message = "не смее да е празно !")
    @Pattern(message = "мора да содржи само мали кирилични букви", regexp = "^[а-шѓѕјќџ]+$")
    private String name;

    @ManyToOne
    @JoinColumn(name = "category")
    private DemandCategory category;

    @ManyToOne
    @JoinColumn(name = "unit")
    private Unit unit;

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Demand() {
    }

    public Demand(String name, DemandCategory demandCategory, Unit unit) {
        this.name = name;
        this.category = demandCategory;
        this.unit = unit;
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

    public DemandCategory getCategory() {
        return category;
    }

    public void setCategory(DemandCategory category) {
        this.category = category;
    }
}
