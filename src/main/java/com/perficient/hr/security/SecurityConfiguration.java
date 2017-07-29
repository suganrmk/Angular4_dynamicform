package com.perficient.hr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
	     auth.jdbcAuthentication();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		  http.csrf().disable();	
		  http.authorizeRequests().antMatchers("/init**").access("hasRole('ROLE_ADMIN')");
	}
}