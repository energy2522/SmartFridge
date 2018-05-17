package com.smartfridge.constants;

public enum CameraType {
    SIMPLE("simple"), PRODUCT("product"), DRINK("drink");

    private String value;

    CameraType(String str) {
        value = str;
    }

    @Override
    public String toString() {
        return value;
    }
}
