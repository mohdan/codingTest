package com.mytaxi.service.car;

import java.util.List;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.car.CarRating;
import com.mytaxi.domainvalue.car.CarStatus;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

public interface CarService
{

    CarDO find(Long carId) throws EntityNotFoundException;


    CarDO create(CarDO carDO) throws ConstraintsViolationException, EntityNotFoundException;


    void delete(Long carId) throws EntityNotFoundException;


    void update(Long carId, CarStatus status) throws EntityNotFoundException;


    void update(Long carId, CarRating rating) throws EntityNotFoundException;


    void update(Long carId, CarStatus status, CarRating rating) throws EntityNotFoundException;


    List<CarDO> find(CarStatus status);
}
