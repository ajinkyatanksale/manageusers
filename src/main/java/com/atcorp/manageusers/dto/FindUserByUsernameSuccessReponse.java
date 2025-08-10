package com.atcorp.manageusers.dto;

import com.atcorp.manageusers.dto.base.SuccessResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserByUsernameSuccessReponse implements SuccessResponse {
    private String username;
    private String dob;
    private String gender;
    private String phoneNumber;
}
