package com.vikingbank.configs;


import com.vikingbank.custom.CustomAuthenticationDetailsSource;

import com.vikingbank.custom.CustomAuthenticationProvider;

import com.vikingbank.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration

@EnableWebSecurity

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired

    private CustomAuthenticationDetailsSource customAuthenticationDetailsSource;

    @Autowired

    private UserService userService;


    @Bean

    public PasswordEncoder passwordEncoder() {

        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    }


    @Bean

    public CustomAuthenticationProvider authProvider() {

        return new CustomAuthenticationProvider();

    }


    @Override

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authProvider());

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().maximumSessions(1);
        http.authorizeRequests().antMatchers(
                        "/registration**",
                        "/registration/**",
                        "/js/**",
                        "/css/**",
                        "/img/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .authenticationDetailsSource(customAuthenticationDetailsSource)
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }

}
