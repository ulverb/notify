package com.mend.scan.converters;

import com.mend.scan.types.UserType;
import jakarta.persistence.Converter;

@Converter
public class UserTypeConverter extends BaseEnumConverter<UserType>
{
    public UserTypeConverter() {
        super(UserType.class);
    }
}
