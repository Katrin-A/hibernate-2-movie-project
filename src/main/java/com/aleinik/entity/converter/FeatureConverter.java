package com.aleinik.entity.converter;

import com.aleinik.enums.Feature;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class FeatureConverter implements AttributeConverter<Set<Feature>, String> {
    @Override
    public String convertToDatabaseColumn(Set<Feature> features) {
        if (features == null || features.isEmpty()) {
            return null;
        }


        return features.stream()
                .filter(Objects::nonNull)
                .map(Feature::getValue)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<Feature> convertToEntityAttribute(String features) {
        if (features == null || features.isEmpty()) {
            return null;
        }

        return Arrays.stream(features.split(","))
                .map(Feature::fromValue)
                .collect(Collectors.toSet());
    }
}
