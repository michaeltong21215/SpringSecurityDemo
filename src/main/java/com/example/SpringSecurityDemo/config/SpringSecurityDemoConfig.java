package com.example.SpringSecurityDemo.config;

import com.example.SpringSecurityDemo.service.AuthUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityDemoConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthUserDetailService authUserDetailService;
    @Autowired
    AppAuthenticationEntryPoint appAuthenticationEntryPoint;
    @Autowired
    AppSecurityFilter appSecurityFilter;

/*    @Override
    public void configure(HttpSecurity security) throws Exception{
        security.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }*/

    @Override
    public void configure(HttpSecurity security) throws Exception{
        security.csrf().disable().authorizeRequests().antMatchers("/authenticate", "/register")
                .permitAll().anyRequest().authenticated().and().exceptionHandling()
                .authenticationEntryPoint(appAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        security.addFilterBefore(appSecurityFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authManager) throws Exception{
        //auth.inMemoryAuthentication().withUser("userOne").password(passwordEncoder().encode("password")).roles("USER");
        authManager.userDetailsService(authUserDetailService).passwordEncoder(passwordEncoder());
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

}
