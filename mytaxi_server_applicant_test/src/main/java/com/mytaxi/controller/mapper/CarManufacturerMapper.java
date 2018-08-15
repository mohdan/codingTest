package com.mytaxi.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.mytaxi.datatransferobject.CarManufacturerDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.car.CarRating;

public class CarManufacturerMapper
{
    public static CarDO makeCarDO(CarManufacturerDTO carManufacturerDTO)
    {
        return new CarDO(carManufacturerDTO.getLicensePlate(), carManufacturerDTO.getSeatCount(), carManufacturerDTO.getEngineType(), carManufacturerDTO.getConvertable(),
            ManufacturerMapper.makeManufacturerDO(carManufacturerDTO.getManufacturer()));
    }


    public static CarManufacturerDTO makeCarManufacturerDTO(CarDO carDO)
    {
        CarManufacturerDTO.CarManufacturerDTOBuilder carManufacturerDTOBuilder = CarManufacturerDTO.newBuilder().setId(carDO.getId()).setSeatCount(carDO.getSeatCount())
            .setLicensePlate(carDO.getLicensePlate()).setEngineType(carDO.getEngineType())
            .setManufacturer(ManufacturerMapper.makeManufacturerDTO(carDO.getManufacturer()));

        CarRating rating = carDO.getRating();
        if (rating != null)
        {
            carManufacturerDTOBuilder.setRating(rating);
        }

        Boolean convertable = carDO.getConvertible();
        if (convertable != null)
        {
            carManufacturerDTOBuilder.setConvertable(convertable);
        }

        return carManufacturerDTOBuilder.createCarManufacturerDTO();
    }


    public static List<CarManufacturerDTO> makeCarManufacturerDTOList(Collection<CarDO> cars)
    {
        return cars.stream().map(CarManufacturerMapper::makeCarManufacturerDTO).collect(Collectors.toList());
    }
}
