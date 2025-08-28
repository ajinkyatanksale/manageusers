package com.atcorp.manageusers.service;

import com.atcorp.manageusers.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public interface UserManagementService {
    String createCustomer(User user) throws ParseException;
    String loginCustomer(User user);
    User getUserByUsername(String username);
    User getUserByUserId(long userId);
}
