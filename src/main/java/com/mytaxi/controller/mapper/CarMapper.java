package com.mytaxi.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainvalue.car.CarRating;

public class CarMapper
{

    public static CarDO makeCarDO(CarDTO carDTO)
    {
        return new CarDO(carDTO.getLicensePlate(), carDTO.getSeatCount(), carDTO.getEngineType(), carDTO.getConvertable(),
            new ManufacturerDO(carDTO.getManufacturerId()));
    }


    public static CarDTO makeCarDTO(CarDO carDO)
    {
        CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder().setId(carDO.getId()).setSeatCount(carDO.getSeatCount())
            .setLicensePlate(carDO.getLicensePlate()).setEngineType(carDO.getEngineType())
            .setManufacturerId(carDO.getManufacturer().getId());

        CarRating rating = carDO.getRating();
        if (rating != null)
        {
            carDTOBuilder.setRating(rating);
        }

        Boolean convertable = carDO.getConvertible();
        if (convertable != null)
        {
            carDTOBuilder.setConvertable(convertable);
        }

        return carDTOBuilder.createCarDTO();
    }


    public static List<CarDTO> makeCarDTOList(Collection<CarDO> cars)
    {
        return cars.stream().map(CarMapper::makeCarDTO).collect(Collectors.toList());
    }
}
