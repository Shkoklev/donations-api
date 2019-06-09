package com.mk.donations.service.util;

public final class DemandQuantityValidator {

    private DemandQuantityValidator() {
    }

    public static boolean isDemandQuantityValid(Double quantity) {
        return quantity > 0D && quantity < 100000D;
    }

}
