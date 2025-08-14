package com.atcorp.manageusers.controller;

import com.atcorp.manageusers.configurations.JwtAuthenticationFilter;
import com.atcorp.manageusers.configurations.JwtHelper;
import com.atcorp.manageusers.model.User;
import com.atcorp.manageusers.service.UserAuthManager;
import com.atcorp.manageusers.service.UserManagementServiceImpl;
import com.atcorp.manageusers.utils.exception.AuthenticationFailureException;
import com.atcorp.manageusers.utils.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserManagementController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserManagementControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserManagementServiceImpl userManagementServiceImpl;

    @MockitoBean
    private JwtHelper jwtHelper;
    @MockitoBean
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockitoBean
    UserAuthManager userAuthManager;

    @Test
    void testCreateUserSuccess() throws Exception {
        when(userManagementServiceImpl.createCustomer(any(User.class))).thenReturn("User Created Successfully");

        ResultActions resultActions = mockMvc.perform(post("/users/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "tanksale.ajinkya3@gmail.com",
                                    "password": "Ajinkya@1012",
                                    "dob": "10/12/1998",
                                    "gender": "Male",
                                    "phoneNumber": "9762289985"
                                }"""));
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.successResponse.successMessage").value("User Created Successfully"));
    }

    @Test
    void testCreateUserValidationFailure() throws Exception {

        ResultActions resultActions = mockMvc.perform(post("/users/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {
                                    "username": "tanksale.ajinkya3@gmail.com",
                                    "password": "Ajinkya@1012",
                                    "dob": "10/12/19988989",
                                    "gender": "Male",
                                    "phoneNumber": "9762289985"
                                }"""));
        resultActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.failureResponse.failureMessage").value("Validation Failed"));
    }

    @Test
    void testCreateUserUserAlreadyExistsFailure() throws Exception {
        when(userManagementServiceImpl.createCustomer(any(User.class))).thenThrow(new UserAlreadyExistsException("User already exists"));
        ResultActions resultActions = mockMvc.perform(post("/users/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {
                                    "username": "tanksale.ajinkya3@gmail.com",
                                    "password": "Ajinkya@1012",
                                    "dob": "10/12/1998",
                                    "gender": "Male",
                                    "phoneNumber": "9762289985"
                                }"""));
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.failureResponse.failureMessage").value("User already exists"));
    }

    @Test
    void testLoginUserSuccess() throws Exception {
        when(userManagementServiceImpl.loginCustomer(any(User.class))).thenReturn("jwtToken");

        ResultActions resultActions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {
                                    "username": "tanksale.ajinkya3@gmail.com",
                                    "password": "Ajinkya@1012"
                                }"""));
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.successResponse.successMessage").value("User Logged in Successfully"));
    }

    @Test
    void testLoginUserValidationFailure() throws Exception {

        ResultActions resultActions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {
                                    "username": "tanksale.ajinkya3@gmail.com",
                                    "password": "Aj"
                                }"""));
        resultActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.failureResponse.failureMessage").value("Validation Failed"));
    }

    @Test
    void testLoginUserFailure() throws Exception {
        when(userManagementServiceImpl.loginCustomer(any(User.class))).thenThrow(new AuthenticationFailureException("Authentication failed"));
        ResultActions resultActions = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {
                                    "username": "tanksale.ajinkya3@gmail.com",
                                    "password": "Ajinkya@1012"
                                }"""));
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.failureResponse.failureMessage").value("Username or password is incorrect"));
    }
}