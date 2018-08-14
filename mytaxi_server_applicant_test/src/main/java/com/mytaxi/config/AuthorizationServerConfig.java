package com.mytaxi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import com.mytaxi.MytaxiConfiguration;

@Configuration
@EnableAuthorizationServer
@ComponentScan(basePackages = "com.mytaxi")
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter
{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MytaxiConfiguration mytaxiConfiguration;

    @Autowired
    @Qualifier("driverUserService")
    private UserDetailsService userDetailsService;


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
    {
        endpoints
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService);
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception
    {
        security.checkTokenAccess("isAuthenticated()");
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception
    {
        clients
            .inMemory()
            .withClient("my-trusted-client")
            .authorizedGrantTypes("client_credentials", "password")
            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
            .scopes("read", "write", "trust")
            .resourceIds("oauth2-resource")
            .accessTokenValiditySeconds(5000)
            .secret(passwordEncoder.encode("secret"));
    }

}
