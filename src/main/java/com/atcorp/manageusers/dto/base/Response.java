package com.atcorp.manageusers.dto.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private SuccessResponse successResponse;
    private FailureResponse failureResponse;
}
