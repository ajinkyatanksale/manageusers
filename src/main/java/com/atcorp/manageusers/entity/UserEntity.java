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

    public static class UserEntityBuilder {
        private String userName;
        private String password;
        private Timestamp dob;
        private String gender;
        private String phoneNumber;


        public UserEntityBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserEntityBuilder setPassword(String password) {
            this.password = password;
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
            return new UserEntity(this);
        }
    }
}
