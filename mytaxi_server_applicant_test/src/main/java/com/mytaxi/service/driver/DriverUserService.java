package com.mytaxi.service.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.dataaccessobject.DriverUserDetailsService;

@Service("driverUserService")
public class DriverUserService implements DriverUserDetailsService
{

    @Autowired
    private DriverRepository driverRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return driverRepository.findByUsername(username);
    }

}
