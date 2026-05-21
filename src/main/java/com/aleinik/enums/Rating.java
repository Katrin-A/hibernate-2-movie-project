package com.aleinik.enums;


public enum Rating {
    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    R("R"),
    NC_17("NC-17");

    private final String value;

    Rating(String value) {

        this.value = value;
    }

    public static Rating fromDbValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Rating value is blank or null");
        }

        for (Rating rating : values()) {
            if (rating.value.equals(value)) {
                return rating;
            }
        }

        throw new IllegalArgumentException("Unknown rating value: " + value);
    }

    public String getValue() {
        return value;
    }
}
