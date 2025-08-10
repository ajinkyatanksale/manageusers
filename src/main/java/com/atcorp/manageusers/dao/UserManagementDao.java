package com.atcorp.manageusers.dao;

import com.atcorp.manageusers.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserManagementDao extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserName (String userName);
}
