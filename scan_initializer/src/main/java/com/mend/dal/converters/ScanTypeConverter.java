package com.mend.dal.converters;

import com.mend.dal.types.ScanType;
import jakarta.persistence.Converter;

@Converter
public class ScanTypeConverter extends BaseEnumConverter<ScanType>
{
    public ScanTypeConverter() {
        super(ScanType.class);
    }
}
