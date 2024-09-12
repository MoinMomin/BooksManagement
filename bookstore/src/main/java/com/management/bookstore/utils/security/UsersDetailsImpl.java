package com.management.bookstore.utils.security;

import com.management.bookstore.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collection;

public class UsersDetailsImpl implements UserDetails {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private Users user;

    public UsersDetailsImpl(Users user) {
        this.user=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return Arrays.asList(authority);
    }
    @Override
    public String getPassword() {

        System.out.print("*******************"+user.getPassword());
        return user.getPassword();
    }

    @Override
    public String getUsername() {

        System.out.print("*******************"+user.getUserName());
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
