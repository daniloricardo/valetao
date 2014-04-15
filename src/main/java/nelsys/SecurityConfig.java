package nelsys;


import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;


import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.configuration.ObjectPostProcessorConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 15)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	
	@Autowired
	DataSource dataSource;
	
	
	 protected void configure(HttpSecurity http) throws Exception {
		 http
         .authorizeRequests()
             .antMatchers("/criasenha").permitAll()
             
             .anyRequest().authenticated();
     http
         .formLogin()
             .loginPage("/login")
             .permitAll()
             .and()
         .logout()
             .permitAll();
		    http.csrf().disable();
		    
		  }
	
	

	 @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		 PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
		
		
		 System.out.println(dataSource);
		 auth
	     //    .inMemoryAuthentication()
	     //        .withUser("sa").password("danilo").roles("USER");
	      .jdbcAuthentication().usersByUsernameQuery(
	        	" SELECT username, password, 'true' as enable FROM users WHERE username=?")
	        		.authoritiesByUsernameQuery(
	       		"SELECT username, authority FROM users WHERE username=?")
	       		.passwordEncoder(passwordEncoder)
	       		.dataSource(dataSource);
	        		
	        		
	    }
	 
}
