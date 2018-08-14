package com.mytaxi.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.mytaxi.datatransferobject.DriverCarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.exception.EncryptDecryptException;
import com.mytaxi.util.EncryptionUtils;

@Component
public class DriverCarMapper
{

    @Autowired
    EncryptionUtils encryptionUtils;


    public DriverDO makeDriverDO(DriverCarDTO driverCarDTO) throws EncryptDecryptException
    {
        String password = BCrypt.hashpw(driverCarDTO.getPassword(), BCrypt.gensalt());

        return new DriverDO(driverCarDTO.getUsername(), password);
    }


    public DriverCarDTO makeDriverCarDTO(DriverDO driverDO) throws EncryptDecryptException
    {
        DriverCarDTO.DriverDTOBuilder driverCarDTOBuilder;

        driverCarDTOBuilder = DriverCarDTO.newBuilder().setId(driverDO.getId())
            .setPassword(driverDO.getPassword()).setUsername(driverDO.getUsername());

        GeoCoordinate coordinate = driverDO.getCoordinate();
        if (coordinate != null)
        {
            driverCarDTOBuilder.setCoordinate(coordinate);
        }

        CarDO car = driverDO.getCar();
        if (car != null)
        {
            driverCarDTOBuilder.setCar(CarMapper.makeCarDTO(car));

        }

        return driverCarDTOBuilder.createDriverDTO();
    }


    public List<DriverCarDTO> makeDriverCarDTOList(Collection<DriverDO> drivers)
    {
        return drivers.stream().map(this::makeDriverCarDTO).collect(Collectors.toList());
    }
}
