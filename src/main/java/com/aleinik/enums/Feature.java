package com.aleinik.enums;

import static java.util.Objects.isNull;


public enum Feature {
    TRAILERS("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes");

    private final String value;

    Feature(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Feature fromValue(String value) {
        if (isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException("Feature value is blank or null");
        }

        for (Feature feature : Feature.values()) {

            if (feature.getValue().equalsIgnoreCase(value)) {
                return feature;
            }
        }
        throw new IllegalArgumentException("Unknown feature: " + value);
    }


}
