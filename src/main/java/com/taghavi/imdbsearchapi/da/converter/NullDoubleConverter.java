package com.taghavi.imdbsearchapi.da.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class NullDoubleConverter implements AttributeConverter<Double, String> {

    @Override
    public String convertToDatabaseColumn(Double value) {
        return value == null ? null : value.toString();
    }

    @Override
    public Double convertToEntityAttribute(String value) {
        if (value == null || "\\N".equals(value) || value.isBlank()) {
            return null;
        }

        return Double.valueOf(value);
    }
}