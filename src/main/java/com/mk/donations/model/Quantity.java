package com.mk.donations.model;

import com.mk.donations.model.exception.UnComparableQuantitiesException;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Quantity implements Comparable<Quantity>, Serializable {

    @Column(unique = true, nullable = false)
    @NotNull(message = "Внесете количина !")
    public Double quantity;

    public Unit unit;

    public Quantity() {}

    public Quantity(double quantity,Unit unit) {
        if(unit==null)
            throw new IllegalArgumentException("Unit can not be null");

        this.quantity = quantity;
        this.unit = unit;
    }

    @Override
    public int hashCode() {
        int result = quantity.hashCode();
        result = 31 * result + unit.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quantity quantity1 = (Quantity) o;

        if (!quantity.equals(quantity1.quantity)) return false;
        return unit == quantity1.unit;
    }

    @Override
    public int compareTo(Quantity o) {
        if (!o.unit.equals(this.unit)) {
            throw new UnComparableQuantitiesException("не може да се споредува " + this.unit.toString() + " со " + o.unit.toString());
        }
        return this.quantity.compareTo(o.quantity);
    }

    public Quantity subtract(Quantity value) {
        if (!value.unit.equals(this.unit)) {
            throw new UnComparableQuantitiesException("не може да се споредува " + this.unit.toString() + " со " + value.unit.toString());
        }
        return new Quantity(this.quantity - value.quantity, this.unit);
    }

    public boolean isLessThan(Quantity quantity) {
        return this.compareTo(quantity) < 0;
    }

    public Quantity add(Quantity value) {
        if (!value.unit.equals(this.unit)) {
            throw new UnComparableQuantitiesException("не може да се споредува " + this.unit.toString() + " со " + value.unit.toString());
        }
        return new Quantity(this.quantity + value.quantity, this.unit);
    }

    public String columnSerialize() {
        return this.quantity + "|" + this.unit.toString();
    }

}
