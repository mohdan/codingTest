package com.mytaxi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{

    //	@Autowired
    //    @Qualifier("driverUserService")
    //    private UserDetailsService userDetailsService;
    //    
    //    @Autowired
    //    private PasswordEncoder passwordEncoder;    
    //        
    //    @Autowired
    //    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
    //        auth
    //            .userDetailsService(userDetailsService)
    //            .passwordEncoder(passwordEncoder);    
    //    }

    //    @Override
    //    public void configure(HttpSecurity http) throws Exception {
    //        http
    //            .authorizeRequests()
    //                .antMatchers("/oauth/token").permitAll()
    //                .anyRequest().authenticated()
    //                .and()
    //            .httpBasic()
    //                .and()
    //            .csrf().disable();
    //    }

    /**
         * See: https://github.com/spring-projects/spring-boot/issues/11136
         *
         * @return
         * @throws Exception
         */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

}
