package com.wipro.profile.risk.assessment.config;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.wipro.profile.risk.assessment.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	
	 @Autowired
	 private UserService userService;
	 
	 @Autowired
	 private DataSource dataSource;
	 
	 private final String USER_QUERY = "select email, password from user where email = ?";
	 
	 private final String ROLE_QUERY = "select u.email, r.name from user user u inner join user_role ur on (u.id = ur.user_id) inner join role r on (ur.role_id = r.role_id)"
	 		+ "   where u.email=?";

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	        .csrf().disable()
	                .authorizeRequests()
	                    .antMatchers(
	                            "/registration"
	                            ).permitAll()
	                    .anyRequest().authenticated()
	                .and()
	                    .formLogin()
	                        .loginPage("/login")
	                            .permitAll()
	                .and()
	                    .logout()
	                        .invalidateHttpSession(true)
	                        .clearAuthentication(true)
	                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	                        .logoutSuccessUrl("/login?logout")
	                .permitAll();
	    }

	    @Bean
	    public BCryptPasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }	    

	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {	    	
	    	auth.jdbcAuthentication().usersByUsernameQuery(USER_QUERY)
	    	.authoritiesByUsernameQuery(ROLE_QUERY)
	    	.dataSource(dataSource)
	    	.passwordEncoder(passwordEncoder());
	    }

}
