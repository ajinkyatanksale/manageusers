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
public class EnrollUserRequest {
    @NotNull
    @Size(min=5, max=40, message = "The length of username must be between 5 and 40 characters.")
    private String username;
    @NotNull
    @Size(min=8, max=15, message = "The length of password must be between 8 and 15 characters.")
    private String password;
    @NotNull
    @Size(min=10, max=10)
    private String dob;
    @NotNull
    private String gender;
    @NotNull
    @Size(min=10, max=10)
    private String phoneNumber;
}
