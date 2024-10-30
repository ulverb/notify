package com.mend.scan.converters;


import com.mend.scan.types.ScanType;
import jakarta.persistence.Converter;

@Converter
public class ScanTypeConverter extends BaseEnumConverter<ScanType>
{
    public ScanTypeConverter() {
        super(ScanType.class);
    }
}
