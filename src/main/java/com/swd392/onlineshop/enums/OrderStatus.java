package com.swd392.onlineshop.enums;

public enum OrderStatus {
    NEW("New"),
    SUBMITTED("Submitted"),
    ACCEPTED("Accepted"),
    SHIPPING("Shipping"),
    SUCCESS("Success"),
    CANCELLED("Cancelled");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
