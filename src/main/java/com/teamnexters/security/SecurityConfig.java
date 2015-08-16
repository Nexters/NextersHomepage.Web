package com.teamnexters.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.teamnexters.security.handler.AccessdeniedHandler;
import com.teamnexters.security.handler.AuthenticationEntryPointHandler;
import com.teamnexters.security.handler.FailureHandler;
import com.teamnexters.security.handler.LogoutHandler;
import com.teamnexters.security.handler.SuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private customuserDetailsService customuserDetailsService = new customuserDetailsService();
	private SuccessHandler successhandler = new SuccessHandler();
	private FailureHandler failurehandler = new FailureHandler();
	private AccessdeniedHandler accessdeniedhandler = new AccessdeniedHandler();
	private LogoutHandler logouthandler = new LogoutHandler();
	private AuthenticationEntryPoint authenticationEntryPoint=new AuthenticationEntryPointHandler();
	
	
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(customuserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
				.antMatchers("/api/main/**").permitAll()
				.antMatchers("/admin/index.html").permitAll()
				.antMatchers("/admin/img/product/**").permitAll()
				.antMatchers("/admin/board/**").access("hasRole('ROLE_USER')")
				.antMatchers("/admin/img/upload/**").access("hasRole('ROLE_USER')")
				.antMatchers("/api/user/**").access("hasRole('ROLE_USER')")
				.antMatchers("/api/admin/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/user/**").access("hasRole('ROLE_USER')")
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				
		.and()
			.logout()
				.logoutSuccessHandler(logouthandler)
				.logoutUrl("/logout")
		.and()
			.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)
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
		http
			.sessionManagement()
				.sessionFixation()
					.migrateSession()
				.maximumSessions(1)
				.maxSessionsPreventsLogin(false)
				.expiredUrl("/expiredSession.do");
		
		http.headers().frameOptions().sameOrigin();
	}
}