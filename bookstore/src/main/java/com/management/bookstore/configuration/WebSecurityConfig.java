package com.management.bookstore.configuration;

import com.management.bookstore.enums.UserRoleEnum;
import com.management.bookstore.filter.JwtRequestFilter;
import com.management.bookstore.utils.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsImpl userDetailsImpl;
    @Autowired
    JwtRequestFilter jwtReqfilter;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println(userDetailsImpl);
        auth.userDetailsService(userDetailsImpl).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/signup","/user/signin","/book/browsebooks","/swagger-ui/*","/swagger-ui.html").permitAll()

                .antMatchers("/order/placeorder","/order/vieworderhistory").hasAuthority(UserRoleEnum.CUSTOMER.getText())
                 .antMatchers("/order/viewallorders","/book/getallbooks","/book/updatebooksstock").hasAnyAuthority(UserRoleEnum.MANAGER.getText())
                .antMatchers("/book/addbooks","/book/updatebooks","/book/deletebooks","/author/*","/order/viewallorders").hasAnyAuthority(UserRoleEnum.ADMIN.getText())
                .anyRequest().authenticated()

                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
               // .and().exceptionHandling().accessDeniedPage("/accessDenied");
        http.addFilterBefore(jwtReqfilter, UsernamePasswordAuthenticationFilter.class);

    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
