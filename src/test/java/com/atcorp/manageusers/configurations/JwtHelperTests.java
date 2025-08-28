package com.atcorp.manageusers.configurations;


import com.atcorp.manageusers.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtHelperTests {

    @InjectMocks
    JwtHelper jwtHelper;

    @Test
    public void testGenerateJwtSuccess () {
        User user = new User("tanksale.ajnkya@gmail.com", "Ajinkya@1012", "Ajinkya Tanksale", "10/12/1998", "Male", "9762289985");

        String result = jwtHelper.generateToken(user);

        assertNotNull(result);
    }
}
