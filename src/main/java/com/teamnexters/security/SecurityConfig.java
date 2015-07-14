package com.teamnexters.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private customuserDetailsService customuserDetailsService = new customuserDetailsService();
	private SuccessHandler successhandler = new SuccessHandler();
	private FailureHandler failurehandler = new FailureHandler();
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(customuserDetailsService);
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/api/**").permitAll()
		.antMatchers("/api/user/**").access("hasRole('ROLE_USER')")
		.antMatchers("/api/admin/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/user/**").access("hasRole('ROLE_USER')")
		.antMatchers("/admin/**").access("hasRole('ROLE_SUPERADMIN')")
		.and()
			.formLogin().loginProcessingUrl("/login").successHandler(successhandler).failureHandler(failurehandler)
			.and().csrf().disable();
	}
}