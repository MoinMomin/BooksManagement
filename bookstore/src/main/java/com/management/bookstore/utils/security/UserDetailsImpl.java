package com.management.bookstore.utils.security;

import com.management.bookstore.model.Users;
import com.management.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=userRepository.findUserByUserName(username);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+user);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new UsersDetailsImpl(user);
    }

}
