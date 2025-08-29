package com.atcorp.manageusers.controller;

import com.atcorp.manageusers.dto.FindUserByUsernameSuccessResponse;
import com.atcorp.manageusers.dto.base.FailureResponse;
import com.atcorp.manageusers.dto.base.Response;
import com.atcorp.manageusers.model.User;
import com.atcorp.manageusers.service.UserManagementService;
import com.atcorp.manageusers.utils.enums.FailureEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/internal")
@Slf4j
@Tag(name = "User Management Internal APIs", description = "Operations related to user accounts")
@PreAuthorize("hasRole('SERVICE')")
public class InternalCommController {

    @Autowired
    UserManagementService userManagementService;

    @GetMapping("/getUserInfo/{userId}")
    public ResponseEntity<Response> getUserByUserId(@Valid @PathVariable @NotNull long userId) {
        User user = userManagementService.getUserByUserId(userId);
        Response response = new Response();
        if (Objects.nonNull(user)) {
            FindUserByUsernameSuccessResponse findUserByUsernameSuccessResponse = new FindUserByUsernameSuccessResponse();
            findUserByUsernameSuccessResponse.setUsername(user.getUsername());
            findUserByUsernameSuccessResponse.setName(user.getName());
            findUserByUsernameSuccessResponse.setDob(user.getDob());
            findUserByUsernameSuccessResponse.setGender(user.getGender());
            findUserByUsernameSuccessResponse.setPhoneNumber(user.getPhoneNumber());
            response.setSuccessResponse(findUserByUsernameSuccessResponse);
        } else {
            FailureResponse failureResponse = new FailureResponse("User does not enrolled with the system", FailureEnum.USER_NOT_FOUND);
            response.setFailureResponse(failureResponse);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
