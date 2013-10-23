package com.digitalrpg.web.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.digitalrpg.web.security.CustomAuthenticationProvider;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public CustomAuthenticationProvider getCustomAuthenticationProvider() {
		return new CustomAuthenticationProvider();
	}
	
	@Override
    protected void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .authenticationProvider(getCustomAuthenticationProvider());
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/img/**", "/css/**", "/js/**", "/register", 
						"/register/**", "/reset-password", "/activate", "/", 
						"/home", "/campaigns/public", "/campaigns/public/**", 
						"/messages", "/favicon.ico").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/home", false)
				.permitAll()
				.and()
			.logout()
				.logoutSuccessUrl("/home")
				.permitAll();
	}
}
