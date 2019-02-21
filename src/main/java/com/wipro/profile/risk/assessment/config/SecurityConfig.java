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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.wipro.profile.risk.assessment.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	
	 @Autowired
	 private UserService userService;
	 
	 @Autowired
	 private DataSource dataSource;
	 
	 private final String USER_QUERY = "select email, password from user where email=?";
	 
	 private final String ROLE_QUERY = "select u.email, r.name from user u inner join users_roles ur on (u.id = ur.user_id) inner join role r on (ur.role_id = r.id) where u.email=sudhansu.singh@wipro.com";

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	                .authorizeRequests()
	                .antMatchers("/").permitAll()
	                .antMatchers("/login").permitAll()
	                .antMatchers("/registration").permitAll()
	                .antMatchers("/home/**").hasAuthority("ROLE_ADMIN")
	                .anyRequest()
	                .authenticated()
	                .and()
	                    .formLogin()
	                        .loginPage("/login").failureUrl("/login?error=true")
	                        .defaultSuccessUrl("/home")
	                        .usernameParameter("email")
	                        .passwordParameter("password")	                            
	                .and()
	                    .logout()
	                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	                        .logoutSuccessUrl("/")
	                        .and()
	                        .rememberMe()
	                        .tokenRepository(tokenRepository())
	                        .tokenValiditySeconds(60*60)
	                        .and().exceptionHandling().accessDeniedPage("/access_denied");
//	                        .invalidateHttpSession(true)
//	                        .clearAuthentication(true)	                        


	    }
	    
	    public PersistentTokenRepository tokenRepository() {
	    	JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
	    	db.setDataSource(dataSource);
	    	return db;
	    	
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
