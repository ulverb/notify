package com.mend.dal.converters;

import com.mend.dal.types.UserType;
import jakarta.persistence.Converter;

@Converter
public class UserTypeConverter extends BaseEnumConverter<UserType>
{
    public UserTypeConverter() {
        super(UserType.class);
    }
}
