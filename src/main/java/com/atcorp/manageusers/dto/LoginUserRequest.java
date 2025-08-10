package com.atcorp.manageusers.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserRequest {
    @NotNull
    @Size(min=5, max=20, message = "The length of username must be between 5 and 20 characters.")
    private String username;
    @NotNull
    @Size(min=1, max=15, message = "The length of password must be between 8 and 15 characters.")
    private String password;
}
