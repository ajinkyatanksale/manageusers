package controller;

import com.atcorp.manageusers.controller.UserManagementController;
import com.atcorp.manageusers.model.User;
import com.atcorp.manageusers.service.UserManagementServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserManagementController.class)
class UserManagementControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserManagementServiceImpl userManagementServiceImpl;



    @Test
    void testCreateUser() throws Exception {
        when(userManagementServiceImpl.createCustomer(any(User.class))).thenReturn("User Created Successfully");

        mockMvc.perform(post("/users/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"username\": \"tanksale.ajinkya3@gmail.com\",\n" +
                                "    \"password\": \"Ajinkya@1012\",\n" +
                                "    \"dob\": \"10/12/1998\",\n" +
                                "    \"gender\": \"Male\",\n" +
                                "    \"phoneNumber\": \"9762289985\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.successMessage").value("User Created Successfully"));
    }
}
