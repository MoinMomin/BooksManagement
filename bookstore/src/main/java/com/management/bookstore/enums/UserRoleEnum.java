package com.management.bookstore.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public enum UserRoleEnum {
    ADMIN("admin", "admin", 1),
    MANAGER("manager", "manager", 2),
    CUSTOMER("customer", "customer", 3);
    private String text;
    private String displayName;
    private int rank;
    UserRoleEnum(String text, String displayName, int rank) {
        this.text = text;
        this.displayName = displayName;
        this.rank = rank;
    }

    @JsonIgnore
    public String getText() {
        return this.text;
    }

    @JsonCreator
    public static UserRoleEnum fromString(String text) {
        for (UserRoleEnum b : UserRoleEnum.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getRank() {
        return rank;
    }

    public String getValue() {
        return text;
    }
}
