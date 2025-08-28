package com.atcorp.manageusers.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Entity
@Table(name = "Users", schema = "user_management")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column(name="user_id")
    @SequenceGenerator(name="user_id_seq", sequenceName="user_management.user_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "user_id_seq")
    private long userId;

    @Column(name="user_name")
    private String userName;
    @Column(name="password")
    private String password;
    @Column(name="name")
    private String name;
    @Column(name="dob")
    private Timestamp dob;
    @Column(name="gender")
    private String gender;
    @Column(name="phone_number")
    private String phoneNumber;

    private UserEntity (UserEntityBuilder userEntityBuilder) {
        this.userName = userEntityBuilder.userName;
        this.password = userEntityBuilder.password;
        this.dob = userEntityBuilder.dob;
        this.phoneNumber = userEntityBuilder.phoneNumber;
        this.gender = userEntityBuilder.gender;
    }

    public static final class UserEntityBuilder {
        private long userId;
        private String userName;
        private String password;
        private String name;
        private Timestamp dob;
        private String gender;
        private String phoneNumber;

        public UserEntityBuilder() {
        }

        public UserEntityBuilder(UserEntity other) {
            this.userId = other.userId;
            this.userName = other.userName;
            this.password = other.password;
            this.name = other.name;
            this.dob = other.dob;
            this.gender = other.gender;
            this.phoneNumber = other.phoneNumber;
        }

        public static UserEntityBuilder anUserEntity() {
            return new UserEntityBuilder();
        }

        public UserEntityBuilder setUserId(long userId) {
            this.userId = userId;
            return this;
        }

        public UserEntityBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserEntityBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserEntityBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserEntityBuilder setDob(Timestamp dob) {
            this.dob = dob;
            return this;
        }

        public UserEntityBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public UserEntityBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserEntity build() {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(userId);
            userEntity.setUserName(userName);
            userEntity.setPassword(password);
            userEntity.setName(name);
            userEntity.setDob(dob);
            userEntity.setGender(gender);
            userEntity.setPhoneNumber(phoneNumber);
            return userEntity;
        }
    }
}
