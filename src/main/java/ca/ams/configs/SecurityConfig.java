package ca.ams.configs;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http
            .formLogin()
             	.loginPage("/login")
             	.loginProcessingUrl("login")
             	.permitAll()
             	.and()
            .authorizeRequests()
         		.antMatchers(HttpMethod.GET, "/js/login.js").permitAll()
         		.antMatchers(HttpMethod.GET, "/css/**").permitAll() 
         		.antMatchers(HttpMethod.GET, "/bower_components/**").permitAll()
         		.antMatchers(HttpMethod.GET, "/init").permitAll()
         		.antMatchers(HttpMethod.GET, "/login").permitAll()
         		.anyRequest().authenticated()
         		.and()
            .logout()                                    
             	.permitAll();
    }

//    @Configuration
//    protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
//        @Override
//        public void init(AuthenticationManagerBuilder auth) throws Exception {
//            auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
//        }
//    }    
}
