package com.taghavi.imdbsearchapi.da.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NullStringValueConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String value) {
        return "\\N".equals(value) ? null : value;
    }

    @Override
    public String convertToEntityAttribute(String value) {
        return "\\N".equals(value) ? null : value;
    }
}