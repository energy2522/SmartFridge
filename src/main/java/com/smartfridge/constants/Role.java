package com.smartfridge.constants;

public enum Role {
    USER("user"), ADMIN("admin"), FRIDGE("fridge");

    private String value;

    Role(String str) {
        value = str;
    }

    @Override
    public String toString() {
        return value;
    }
}
