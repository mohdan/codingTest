package com.mytaxi.service.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mytaxi.dataaccessobject.UserRepository;
import com.mytaxi.domainobject.User;

@Service
public class UserService
{

    @Autowired

    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * User for Oauth2 authentication for generation of bearer token
     * 
     * @param user      
     */
    public void save(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
    }

}
