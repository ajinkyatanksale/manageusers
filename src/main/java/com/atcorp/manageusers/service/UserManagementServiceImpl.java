package com.atcorp.manageusers.service;

import com.atcorp.manageusers.configurations.JwtHelper;
import com.atcorp.manageusers.dao.UserManagementDao;
import com.atcorp.manageusers.model.User;
import com.atcorp.manageusers.entity.UserEntity;
import com.atcorp.manageusers.utils.exception.AuthenticationFailureException;
import com.atcorp.manageusers.utils.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Component
public class UserManagementServiceImpl implements UserManagementService {

    @Autowired
    UserManagementDao userManagementDao;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    JwtHelper jwtHelper;

    @Override
    public String createCustomer(User user) throws ParseException, UserAlreadyExistsException {
        UserEntity userEntity1 = userManagementDao.findByUserName(user.getUsername());

        if (Objects.nonNull(userEntity1)) {
            throw new UserAlreadyExistsException("User already exists");
        }

        String formatPattern = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);
        Date parsedDate = dateFormat.parse(user.getDob() + " 00:00:00");
        Timestamp timestamp = new Timestamp(parsedDate.getTime());
        UserEntity userEntity = new UserEntity.UserEntityBuilder()
                .setUserName(user.getUsername())
                .setPassword(passwordEncoder.encode(user.getPassword()))
                .setPhoneNumber(user.getPhoneNumber())
                .setGender(user.getGender())
                .setDob(timestamp)
                .build();

        userManagementDao.save(userEntity);
        return "User Created Successfully";
    }

    @Override
    public String loginCustomer(User user) throws UsernameNotFoundException {
        getUserByUsername(user.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtHelper.generateToken(user);
        } else {
            throw new AuthenticationFailureException("Authentication failed");
        }
    }

    @Override
    public User getUserByUsername(String username) throws UsernameNotFoundException{
        UserEntity userEntity = userManagementDao.findByUserName(username);
        if (Objects.nonNull(userEntity)) {
            return new User.UserBuilder().setUserName(userEntity.getUserName())
                    .setPhoneNumber(userEntity.getPhoneNumber())
                    .setGender(userEntity.getGender())
                    .setDob(userEntity.getDob().toString()).build();
        } else {
            throw new UsernameNotFoundException("User does not exists");
        }
    }
}
