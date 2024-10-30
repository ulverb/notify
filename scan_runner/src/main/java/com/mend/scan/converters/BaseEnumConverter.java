package com.mend.scan.converters;

import jakarta.persistence.AttributeConverter;
import org.springframework.dao.InvalidDataAccessApiUsageException;

public abstract class BaseEnumConverter<E extends Enum<E>> implements AttributeConverter<E, String> {

    private final Class<E> enumClass;

    protected BaseEnumConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        return attribute != null ? attribute.name() : null;
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        
        try {
            return Enum.valueOf(enumClass, dbData);
        } catch (InvalidDataAccessApiUsageException | IllegalArgumentException e) {
            return null;
        }
    }
}
