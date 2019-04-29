package com.mk.donations.model.factory;

import com.mk.donations.model.Quantity;
import com.mk.donations.model.Unit;

public final class QuantityFactory {

    public static Quantity of(double quantity, Unit unit) {
        return new Quantity(quantity,unit);
    }

    public static Quantity parse(String serialized) {
        String[] parts = serialized.split("\\|");
        Double quantity = Double.parseDouble(parts[0]);
        Unit unit = Unit.valueOf(parts[1]);
        return new Quantity(quantity,unit);
    }
}
