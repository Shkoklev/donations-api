package com.mk.donations.service.util;

import com.mk.donations.model.Quantity;
import org.springframework.stereotype.Component;

public final class DemandQuantityValidator {

    private DemandQuantityValidator() {
    }

    public static boolean isDemandQuantityValid(Quantity quantity) {
        return quantity.quantity > 0D && quantity.unit != null;
    }

}
