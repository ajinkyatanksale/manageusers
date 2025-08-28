package com.atcorp.manageusers.controller;

import com.atcorp.manageusers.dto.*;
import com.atcorp.manageusers.dto.base.FailureResponse;
import com.atcorp.manageusers.dto.base.Response;
import com.atcorp.manageusers.model.User;
import com.atcorp.manageusers.service.UserManagementService;
import com.atcorp.manageusers.utils.enums.FailureEnum;
import com.atcorp.manageusers.utils.exception.AuthenticationFailureException;
import com.atcorp.manageusers.utils.exception.UserAlreadyExistsException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Objects;

@RestController
@RequestMapping("/users")
@Slf4j
@Tag(name = "User Management", description = "Operations related to user accounts")
public class UserManagementController {
    @Autowired
    UserManagementService userManagementService;

    @PostMapping("/enroll")
    public ResponseEntity<Response> createCustomer(@Valid @RequestBody EnrollUserRequest enrollUserRequest) throws ParseException, UserAlreadyExistsException{
        Response response = new Response();

        String res = userManagementService.createCustomer(new User.UserBuilder()
                .setUsername(enrollUserRequest.getUsername())
                .setName(enrollUserRequest.getName())
                .setPassword(enrollUserRequest.getPassword())
                .setDob(enrollUserRequest.getDob())
                .setGender(enrollUserRequest.getGender())
                .setPhoneNumber(enrollUserRequest.getPhoneNumber())
                .build());
        if (StringUtils.isNoneBlank(res)) {
            EnrollUserSuccessResponse successResponse = new EnrollUserSuccessResponse();
            successResponse.setSuccessMessage(res);
            response.setSuccessResponse(successResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            FailureResponse failureResponse = new FailureResponse("Internal Server Error", FailureEnum.SERVER_ERROR);
            response.setFailureResponse(failureResponse);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginCustomer(@Valid @RequestBody LoginUserRequest loginUserRequest) throws AuthenticationFailureException {
        User user = new User.UserBuilder().setUsername(loginUserRequest.getUsername()).setPassword(loginUserRequest.getPassword()).build();
        Response response = new Response();
        String jwtToken = userManagementService.loginCustomer(user);
        if (StringUtils.isNoneBlank(jwtToken)) {
            LoginUserSuccessResponse loginSuccessResponse = new LoginUserSuccessResponse();
            loginSuccessResponse.setSuccessMessage("User Logged in Successfully");
            loginSuccessResponse.setJwtToken(jwtToken);
            response.setSuccessResponse(loginSuccessResponse);
        } else {
            FailureResponse failureResponse = new FailureResponse("User is not registered in the system", FailureEnum.USER_NOT_FOUND);
            response.setFailureResponse(failureResponse);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Response> getUserByUsername(@Valid @RequestParam @NotNull String username) {
        User user = userManagementService.getUserByUsername(username);
        Response response = new Response();
        if (Objects.nonNull(user)) {
            FindUserByUsernameSuccessReponse findUserByUsernameSuccessReponse = new FindUserByUsernameSuccessReponse();
            findUserByUsernameSuccessReponse.setUsername(user.getUsername());
            findUserByUsernameSuccessReponse.setName(user.getName());
            findUserByUsernameSuccessReponse.setDob(user.getDob());
            findUserByUsernameSuccessReponse.setGender(user.getGender());
            findUserByUsernameSuccessReponse.setPhoneNumber(user.getPhoneNumber());
            response.setSuccessResponse(findUserByUsernameSuccessReponse);
        } else {
            FailureResponse failureResponse = new FailureResponse("User does not enrolled with the system", FailureEnum.USER_NOT_FOUND);
            response.setFailureResponse(failureResponse);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
