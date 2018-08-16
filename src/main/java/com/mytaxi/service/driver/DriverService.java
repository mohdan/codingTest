package com.mytaxi.service.driver;

import java.util.List;

import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.domainvalue.car.CarRating;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.filter.SearchDriverFilter;

public interface DriverService
{

    DriverDO find(Long driverId) throws EntityNotFoundException;


    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    DriverDO update(Long driverId, OnlineStatus onlineStatus) throws EntityNotFoundException;
   

    void delete(Long driverId) throws EntityNotFoundException;


    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;


    List<DriverDO> find(OnlineStatus onlineStatus);


    DriverDO selectCar(Long driverId, Long carId) throws EntityNotFoundException, ConstraintsViolationException,
        CarAlreadyInUseException;


    DriverDO deselectCar(Long driverId)
        throws EntityNotFoundException, ConstraintsViolationException;


    SearchDriverFilter checkSearchDriverFilter(String uuid, String userName, OnlineStatus onlineStatus,
        String licensePlate, CarRating rating, String manufacturerName);


    List<DriverDO> searchDriver(SearchDriverFilter filter);

}
