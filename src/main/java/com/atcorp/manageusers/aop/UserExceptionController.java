package com.atcorp.manageusers.aop;

import com.atcorp.manageusers.dto.base.FailureResponse;
import com.atcorp.manageusers.dto.base.Response;
import com.atcorp.manageusers.utils.enums.FailureEnum;
import com.atcorp.manageusers.utils.exception.AuthenticationFailureException;
import com.atcorp.manageusers.utils.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.ParseException;

@ControllerAdvice
public class UserExceptionController {

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<Response> exception(UserAlreadyExistsException exception) {
        Response response = new Response();
        FailureResponse failureResponse = new FailureResponse("User already exists", FailureEnum.USER_ALREADY_EXISTS);
        response.setFailureResponse(failureResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(value = ParseException.class)
    public ResponseEntity<Response> exception(ParseException exception) {
        Response response = new Response();
        FailureResponse failureResponse = new FailureResponse("Internal Server Error", FailureEnum.SERVER_ERROR);
        response.setFailureResponse(failureResponse);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<Response> exception(UsernameNotFoundException exception) {
        Response response = new Response();
        FailureResponse failureResponse = new FailureResponse("User does not enrolled with the system", FailureEnum.USER_NOT_FOUND);
        response.setFailureResponse(failureResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(value = AuthenticationFailureException.class)
    public ResponseEntity<Response> exception(AuthenticationFailureException exception) {
        Response response = new Response();
        FailureResponse failureResponse = new FailureResponse("Username or password is incorrect", FailureEnum.AUTHENTICATION_FAILED);
        response.setFailureResponse(failureResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
