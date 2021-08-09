package com.example.SpringSecurityDemo.config;

import com.example.SpringSecurityDemo.service.AuthUserDetailService;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SpringSecurityDemoConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthUserDetailService authUserDetailService;
    @Autowired
    AppAuthenticationEntryPoint appAuthenticationEntryPoint;
    @Autowired
    AppSecurityFilter appSecurityFilter;

    @Override
    public void configure(HttpSecurity security) throws Exception{
        security.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().withUser("userOne").password(passwordEncoder().encode("password")).roles("USER");
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

