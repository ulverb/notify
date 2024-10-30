package com.mend.mvc.requests;

import com.mend.dal.types.UserType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest
{
    @NotBlank(message = "username")
    private String username;

    @NotBlank(message = "userType")
    private UserType userType;
}
