package com.Mudamu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends  WebSecurityConfigurerAdapter{

	String[] resources = new String[]{
            "/include/**","/css/**","/icons/**","/img/**","/js/**","/layer/**"
    };
	/**
	*
	* configuracion de springSeguriti donde configuramos a los permisos necesarios para acceder a las paginas, cual es la pagina login, a donde nos lleva despues del login...
	* 
	*/
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
        .antMatchers(resources).permitAll()  
        .antMatchers("/","/index", "/signup").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/pacPage")
            .failureUrl("/login?error=true")
            .usernameParameter("username")
            .passwordParameter("password")
            .and()
            .csrf().disable()
        .logout()
            .permitAll()
            .logoutSuccessUrl("/login?logout");
    }
	
	BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	*
	* encriptador de password.
	* @return 
	* 	  possible object is
    *     {@link BCryptPasswordEncoder}
	*/
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        return bCryptPasswordEncoder;
    }
    
    @Autowired
    UserDetailsService userDetailsService;
    
	/**
	*
	* Donde se especifica donde se ara la authentficacion.
	* @param auth
    *     allowed object is
    *     {@link AuthenticationManagerBuilder }
    *@throws Exception     
    *
	*/
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
    	//Especificar el encargado del login y encriptacion del password
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
}
