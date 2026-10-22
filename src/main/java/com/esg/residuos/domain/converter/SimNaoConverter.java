package com.esg.residuos.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/** Converte um Boolean Java para o padrao CHAR(1) 'S'/'N' utilizado no Oracle. */
@Converter
public class SimNaoConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute ? "S" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return "S".equalsIgnoreCase(dbData);
    }
}
