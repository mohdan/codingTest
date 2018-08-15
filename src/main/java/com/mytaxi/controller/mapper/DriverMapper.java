package com.mytaxi.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.util.EncryptionUtils;

@Component
public class DriverMapper
{

    @Autowired
    EncryptionUtils encryptionUtils;


    public DriverDO makeDriverDO(DriverDTO driverDTO)
    {
        String password = BCrypt.hashpw(driverDTO.getPassword(), BCrypt.gensalt());
        return new DriverDO(driverDTO.getUsername(), password);
    }


    public DriverDTO makeDriverDTO(DriverDO driverDO)
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder;

        driverDTOBuilder = DriverDTO.newBuilder().setId(driverDO.getId())
            .setPassword(driverDO.getPassword()).setUsername(driverDO.getUsername());

        GeoCoordinate coordinate = driverDO.getCoordinate();
        if (coordinate != null)
        {
            driverDTOBuilder.setCoordinate(coordinate);
        }

        return driverDTOBuilder.createDriverDTO();
    }


    public List<DriverDTO> makeDriverDTOList(Collection<DriverDO> drivers)
    {
        return drivers.stream().map(this::makeDriverDTO).collect(Collectors.toList());
    }
}
