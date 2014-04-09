package com.digitalrpg.web.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.digitalrpg.web.service.CustomPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	UserDetailsService userDetailsService;

   @Bean(name="authenticationManager")
   @Override
   public AuthenticationManager authenticationManagerBean() throws Exception {
       return super.authenticationManagerBean();
   }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider
				.setPasswordEncoder(new CustomPasswordEncoder());
		auth.authenticationProvider(daoAuthenticationProvider);
	}
	
	@Bean
	protected RememberMeServices rememberMeServices() {
		TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("d-rpg-rm", userDetailsService);
		rememberMeServices.setParameter("remember-me");
		return rememberMeServices;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/img/**", "/css/**", "/js/**", "/register",
						"/register/**", "/reset-password", "/activate", "/",
						"/home", "/campaigns/public", "/campaigns/public/**",
						"/messages", "/favicon.ico").permitAll().anyRequest()
				.authenticated()
				.and().formLogin()
					.loginPage("/login")
					.defaultSuccessUrl("/home", false)
					.permitAll()
				.and().rememberMe()
					.key("d-rpg-rm")
					.rememberMeServices(rememberMeServices())
				.and().logout()
					.logoutSuccessUrl("/home").permitAll();
	}
}
