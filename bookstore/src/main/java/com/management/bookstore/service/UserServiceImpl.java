package com.management.bookstore.service;

import com.management.bookstore.enums.UserRoleEnum;
import com.management.bookstore.exception.jwt.InvalidCredentialsException;
import com.management.bookstore.exception.jwt.UserRoleNotMatchedException;
import com.management.bookstore.mapper.jwt.JwtRequestMapper;
import com.management.bookstore.mapper.jwt.JwtResponseMapper;
import com.management.bookstore.model.Users;
import com.management.bookstore.repository.UserRepository;
import com.management.bookstore.utils.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService jwtUserDetailsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Override
    public Users signUp(Users user) {
      String role=  user.getRole().toLowerCase();
      String password;
      /*if(role!=null && (role.equalsIgnoreCase(UserRole.ADMIN.getText())
              || role.equalsIgnoreCase(UserRole.MANAGER.getText())
              || role.equalsIgnoreCase(UserRole.CUSTOMER.getText())))*/
        if(role!=null && role.equals(UserRoleEnum.CUSTOMER.getText()) ){
          password= user.getPassword();
         user.setPassword(passwordEncoder.encode(user.getPassword()));
         userRepository.save(user);
            user.setPassword(password);
         return user;
      }else{
          throw new UserRoleNotMatchedException();
      }

    }

    @Override
    public JwtResponseMapper login(JwtRequestMapper jwtRequest) throws Exception {
        authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(jwtRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        return new JwtResponseMapper(token);
    }
    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("INVALID_CREDENTIALS");
        }
    }
}
