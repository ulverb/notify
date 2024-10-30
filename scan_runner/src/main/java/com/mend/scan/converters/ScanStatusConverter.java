package com.mend.scan.converters;


import com.mend.scan.types.ScanStatus;
import jakarta.persistence.Converter;

@Converter
public class ScanStatusConverter extends BaseEnumConverter<ScanStatus>
{
    public ScanStatusConverter() {
        super(ScanStatus.class);
    }
}
