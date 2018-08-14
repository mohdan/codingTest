package com.mytaxi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer

public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{

    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
            .antMatchers("/").permitAll().antMatchers("/v1/**")
            .authenticated();

        http.httpBasic().and().csrf().disable();
    }

    //	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    //	   @Override
    //	   public AuthenticationManager authenticationManagerBean() throws Exception {
    //	       return super.authenticationManagerBean();
    //	   }
    //	 
    //	 

}
