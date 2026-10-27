package com.swd392.onlineshop.enums;

public enum FeedbackStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String label;

    FeedbackStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
