package com.swd392.onlineshop.enums;

/** Trang thai chung cho Product, Post, Slider, Setting, User (show/hide). */
public enum Status {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
