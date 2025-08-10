package com.atcorp.manageusers.dto.base;

import com.atcorp.manageusers.utils.enums.FailureEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FailureResponse {
    private String failureMessage;
    private FailureEnum failureEnum;
}
