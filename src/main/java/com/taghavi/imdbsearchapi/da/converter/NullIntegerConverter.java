package com.taghavi.imdbsearchapi.da.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class NullIntegerConverter implements AttributeConverter<Integer, String> {

    @Override
    public String convertToDatabaseColumn(Integer value) {
        return value == null ? null : value.toString();
    }

    @Override
    public Integer convertToEntityAttribute(String value) {
        if (value == null || "\\N".equals(value) || value.isBlank()) {
            return null;
        }

        return Integer.valueOf(value);
    }
}