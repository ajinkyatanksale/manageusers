package com.atcorp.manageusers.dto;

import com.atcorp.manageusers.dto.base.SuccessResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserSuccessResponse implements SuccessResponse {
    private String jwtToken;
    private String successMessage;
}
