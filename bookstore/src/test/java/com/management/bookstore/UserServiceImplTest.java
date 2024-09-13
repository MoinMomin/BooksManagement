package com.management.bookstore;

import com.management.bookstore.enums.UserRoleEnum;
import com.management.bookstore.exception.jwt.InvalidCredentialsException;
import com.management.bookstore.exception.jwt.UserRoleNotMatchedException;
import com.management.bookstore.mapper.jwt.JwtRequestMapper;
import com.management.bookstore.mapper.jwt.JwtResponseMapper;
import com.management.bookstore.model.Users;
import com.management.bookstore.repository.UserRepository;
import com.management.bookstore.service.UserServiceImpl;
import com.management.bookstore.utils.security.jwt.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService jwtUserDetailsService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUp_Success() {
        Users user = new Users(null, "user1", "password123", UserRoleEnum.CUSTOMER.getText());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        Users result = userService.signUp(user);

        assertNotNull(result);
        assertEquals(user.getUserName(), result.getUserName());
        assertEquals(UserRoleEnum.CUSTOMER.getText(), result.getRole());
        assertEquals(user.getPassword(), result.getPassword()); // Password should be returned in plain form
        verify(userRepository).save(user);
    }

    @Test
    public void testSignUp_InvalidRole() {
        Users user = new Users(null, "user1", "password123", "INVALID_ROLE");

        assertThrows(UserRoleNotMatchedException.class, () -> userService.signUp(user));
        verify(userRepository, never()).save(any());
    }

    @Test
    public void testLogin_Success() throws Exception {
        JwtRequestMapper jwtRequest = new JwtRequestMapper("user1", "password123");
        UserDetails userDetails = mock(UserDetails.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsername())).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn("jwtToken");

        JwtResponseMapper result = userService.login(jwtRequest);

        assertNotNull(result);
        assertEquals("jwtToken", result.getJwtToken());
    }

    @Test
    public void testLogin_InvalidCredentials() throws Exception {
        JwtRequestMapper jwtRequest = new JwtRequestMapper("user1", "password123");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(InvalidCredentialsException.class, () -> userService.login(jwtRequest));
    }

    @Test
    public void testLogin_UserDisabled() throws Exception {
        JwtRequestMapper jwtRequest = new JwtRequestMapper("user1", "password123");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new DisabledException("User is disabled"));

        assertThrows(Exception.class, () -> userService.login(jwtRequest));
}
}