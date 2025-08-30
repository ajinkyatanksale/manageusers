package com.atcorp.manageusers.service;

import com.atcorp.manageusers.configurations.JwtHelper;
import com.atcorp.manageusers.dao.UserManagementDao;
import com.atcorp.manageusers.entity.UserEntity;
import com.atcorp.manageusers.model.User;
import com.atcorp.manageusers.service.UserManagementServiceImpl;
import com.atcorp.manageusers.utils.exception.AuthenticationFailureException;
import com.atcorp.manageusers.utils.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserManagementServiceTest {

    @Mock
    UserManagementDao userManagementDao;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    UserManagementServiceImpl userManagementServiceImpl;

    @Mock
    JwtHelper jwtHelper;

    @Mock
    AuthenticationManager authenticationManager;

    @Test
    public void testEnrollmentSuccessful () throws ParseException {
        User user = new User(10001, "tanksale.ajnkya@gmail.com", "Ajinkya@1012", "Ajinkya Tanksale", "10/12/1998", "Male", "9762289985", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        String formatPattern = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);
        Date parsedDate = dateFormat.parse(user.getDob() + " 00:00:00");
        Timestamp timestamp = new Timestamp(parsedDate.getTime());
        UserEntity savedUser = new UserEntity.UserEntityBuilder().setUserName("tanksale.ajnkya@gmail.com")
                .setPassword("Ajinkya@1012")
                .setDob(timestamp)
                .setGender("Male")
                .setPhoneNumber("9762289985").build();


        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPass");
        when(userManagementDao.save(any(UserEntity.class))).thenReturn(savedUser);
        when(userManagementDao.findByUserName(any(String.class))).thenReturn(null);

        String result = userManagementServiceImpl.createCustomer(user);

        assertNotNull(result);
        assertEquals("User Created Successfully", result);
        verify(userManagementDao).save(any(UserEntity.class));
    }

    @Test
    public void testEnrollmentFailure1 () throws ParseException {
        User user = new User(10001,"tanksale.ajnkya@gmail.com", "Ajinkya@1012", "Ajinkya Tanksale", "10/12/1998", "Male", "9762289985", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        String formatPattern = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);
        Date parsedDate = dateFormat.parse(user.getDob() + " 00:00:00");
        Timestamp timestamp = new Timestamp(parsedDate.getTime());
        UserEntity savedUser = new UserEntity.UserEntityBuilder().setUserName("tanksale.ajnkya@gmail.com")
                .setPassword("Ajinkya@1012")
                .setDob(timestamp)
                .setGender("Male")
                .setPhoneNumber("9762289985").build();

        when(userManagementDao.findByUserName(any(String.class))).thenReturn(savedUser);

        assertThrows(UserAlreadyExistsException.class, () -> {
            userManagementServiceImpl.createCustomer(user);
        });
    }

    @Test
    public void testEnrollmentFailure2 () {
        User user = new User(10001, "tanksale.ajnkya@gmail.com", "Ajinkya@1012", "Ajinkya Tanksale", "10-12-1998", "Male", "9762289985", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        when(userManagementDao.findByUserName(any(String.class))).thenReturn(null);

        assertThrows(ParseException.class, () -> {
            userManagementServiceImpl.createCustomer(user);
        });
    }

    @Test
    public void testLoginSuccessful () throws ParseException {
        User user = new User(100001, "tanksale.ajnkya@gmail.com", "Ajinkya@1012", "Ajinkya Tanksale", "10/12/1998", "Male", "9762289985", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        String formatPattern = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);
        Date parsedDate = dateFormat.parse(user.getDob() + " 00:00:00");
        Timestamp timestamp = new Timestamp(parsedDate.getTime());
        UserEntity existingUser = new UserEntity.UserEntityBuilder().setUserName("tanksale.ajnkya@gmail.com")
                .setPassword("Ajinkya@1012")
                .setName("Ajinkya Tanksale")
                .setDob(timestamp)
                .setGender("Male")
                .setPhoneNumber("9762289985")
                .setRole("ROLE_USER").build();

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        "tanksale.ajinkya@gmail.com",        // principal
                        "Ajinkya@1012",        // credentials
                        List.of(new SimpleGrantedAuthority("USER")) // authorities
                );

        when(jwtHelper.generateToken(any(User.class))).thenReturn("jwtToken");
        when(userManagementDao.findByUserName(any(String.class))).thenReturn(existingUser);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authToken);

        String result = userManagementServiceImpl.loginCustomer(user);

        assertEquals("jwtToken", result);
    }

    @Test
    public void testLoginFailureUsernameNotFound () {
        User user = new User(10001, "tanksale.ajnkya@gmail.com", "Ajinkya@1012", "Ajinkya Tanksale", "10/12/1998", "Male", "9762289985", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        when(userManagementDao.findByUserName(any(String.class))).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userManagementServiceImpl.loginCustomer(user);
        });
    }

    @Test
    public void testLoginFailureAuthenticationFailure () throws ParseException {
        User user = new User(10001, "tanksale.ajnkya@gmail.com", "Ajinkya@1012", "Ajinkya Tanksale" , "10/12/1998", "Male", "9762289985", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        String formatPattern = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);
        Date parsedDate = dateFormat.parse(user.getDob() + " 00:00:00");
        Timestamp timestamp = new Timestamp(parsedDate.getTime());
        UserEntity existingUser = new UserEntity.UserEntityBuilder().setUserName("tanksale.ajnkya@gmail.com")
                .setPassword("Ajinkya@1012")
                .setName("Ajinkya Tanksale")
                .setDob(timestamp)
                .setGender("Male")
                .setPhoneNumber("9762289985")
                .setRole("ROLE_USER").build();

        when(userManagementDao.findByUserName(any(String.class))).thenReturn(existingUser);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(new UsernamePasswordAuthenticationToken(user, null));

        assertThrows(AuthenticationFailureException.class, () -> {
            userManagementServiceImpl.loginCustomer(user);
        });
    }

}
