package com.atcorp.manageusers.dto;

import com.atcorp.manageusers.dto.base.SuccessResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollUserSuccessResponse implements SuccessResponse {
    private String successMessage;
}
