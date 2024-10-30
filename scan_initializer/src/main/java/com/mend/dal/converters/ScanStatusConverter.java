package com.mend.dal.converters;

import com.mend.dal.types.ScanStatus;
import jakarta.persistence.Converter;

@Converter
public class ScanStatusConverter extends BaseEnumConverter<ScanStatus>
{
    public ScanStatusConverter() {
        super(ScanStatus.class);
    }
}
