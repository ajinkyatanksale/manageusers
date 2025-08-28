package com.atcorp.manageusers.service;

import com.atcorp.manageusers.dao.UserManagementDao;
import com.atcorp.manageusers.entity.UserEntity;
import com.atcorp.manageusers.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthManager implements UserDetailsService {

    @Autowired
    UserManagementDao userManagementDao;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userManagementDao.findByUserName(username);
        if (userEntity != null) {
            return new User(userEntity.getUserName(), userEntity.getPassword(), userEntity.getName(),
                    userEntity.getDob().toString(), userEntity.getGender(),
                    userEntity.getPhoneNumber());
        } else {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }
}
