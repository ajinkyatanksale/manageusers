package com.atcorp.manageusers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    private long userId;
    private String username;
    private String password;
    private String name;
    private String dob;
    private String gender;
    private String phoneNumber;
    @Setter
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public User (String username, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    private User (UserBuilder userBuilder) {
        this.username = userBuilder.username;
        this.password = userBuilder.password;
        this.name = userBuilder.name;
        this.dob = userBuilder.dob;
        this.phoneNumber = userBuilder.phoneNumber;
        this.gender = userBuilder.gender;
    }

    public static final class UserBuilder {
        private long userId;
        private String username;
        private String password;
        private String name;
        private String dob;
        private String gender;
        private String phoneNumber;
        private Collection<? extends GrantedAuthority> authorities;

        public UserBuilder() {
        }

        public UserBuilder(User other) {
            this.userId = other.userId;
            this.username = other.username;
            this.password = other.password;
            this.name = other.name;
            this.dob = other.dob;
            this.gender = other.gender;
            this.phoneNumber = other.phoneNumber;
            this.authorities = other.authorities;
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder setUserId(long userId) {
            this.userId = userId;
            return this;
        }

        public UserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setDob(String dob) {
            this.dob = dob;
            return this;
        }

        public UserBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public UserBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder setAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public User build() {
            User user = new User();
            user.setUserId(userId);
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
            user.setDob(dob);
            user.setGender(gender);
            user.setPhoneNumber(phoneNumber);
            user.setAuthorities(authorities);
            return user;
        }
    }
}
