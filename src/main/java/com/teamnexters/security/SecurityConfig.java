package com.teamnexters.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private customuserDetailsService customuserDetailsService = new customuserDetailsService();
	private SuccessHandler successhandler = new SuccessHandler();
	private FailureHandler failurehandler = new FailureHandler();
	private AccessdeniedHandler accessdeniedhandler = new AccessdeniedHandler();
	private LogoutHandler logouthandler = new LogoutHandler();
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(customuserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/api/main/**").permitAll()
				.antMatchers("/api/user/**").access("hasRole('ROLE_USER')")
				.antMatchers("/api/admin/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/user/**").access("hasRole('ROLE_USER')")
				.antMatchers("/admin/**").access("hasRole('ROLE_SUPERADMIN')")
		.and()
			.logout()
				.logoutSuccessHandler(logouthandler)
				.logoutUrl("/logout")
		.and()
			.exceptionHandling()
				.accessDeniedHandler(accessdeniedhandler)
		.and()
			.formLogin()
				.loginPage("/needlogin.do")
				.loginProcessingUrl("/login")
				.successHandler(successhandler)
				.failureHandler(failurehandler)
		.and()
			.csrf()
				.disable();
	}
}