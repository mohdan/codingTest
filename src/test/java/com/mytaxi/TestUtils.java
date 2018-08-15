package com.mytaxi;

import java.time.ZonedDateTime;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.datatransferobject.CarManufacturerDTO;
import com.mytaxi.datatransferobject.DriverCarDTO;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.datatransferobject.ManufacturerDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.domainvalue.car.CarRating;
import com.mytaxi.domainvalue.car.CarStatus;
import com.mytaxi.domainvalue.car.EngineType;
import com.mytaxi.filter.SearchDriverFilter;


public abstract class TestUtils
{

    public CarDO getCar()
    {

        String licensePlate = "MH11 1111";
        Integer seatCount = 2;
        EngineType engineType = EngineType.GAS;
        CarDO carDO = new CarDO(licensePlate, seatCount, engineType, Boolean.TRUE, getManufacturer());

        carDO.setId(1L);
        return carDO;
    }


    public ManufacturerDO getManufacturer()
    {
        String name = "Audi";
        String model = "A01";
        ManufacturerDO manufacturer = new ManufacturerDO(name, model);

        manufacturer.setId(1L);
        manufacturer.setDateCreated(ZonedDateTime.now());
        return manufacturer;
    }
    
    
    public ManufacturerDTO getManufacturerDTO()
    {
        String name = "Audi";
        String model = "A01";
        return ManufacturerDTO.newBuilder().setId(1L).setName(name).setModel(model).createManufacturerDTO();
    }


    public CarDTO getCarDTO()
    {
        return CarDTO.newBuilder().setConvertable(true).setEngineType(EngineType.GAS).setLicensePlate("MH11 1111")
            .setSeatCount(2).setManufacturerId(1L).setRating(CarRating.FIVE_STAR).createCarDTO();
    }
    
    
    public CarManufacturerDTO getCarManufacturerDTO()
    {
        return CarManufacturerDTO.newBuilder().setConvertable(true).setEngineType(EngineType.GAS).setLicensePlate("MH11 1111")
            .setSeatCount(2).setManufacturer(getManufacturerDTO()).setRating(CarRating.FIVE_STAR).createCarManufacturerDTO();
    }


    public DriverDO getDriver()
    {

        String username = "test";
        String password = "test";
        DriverDO driverDO = new DriverDO(username, password);
        driverDO.setId(1L);
        driverDO.setDeleted(false);

        driverDO.setOnlineStatus(OnlineStatus.ONLINE);
        GeoCoordinate geoCoordinate = new GeoCoordinate(90, 90);
        driverDO.setCoordinate(geoCoordinate);
        return driverDO;
    }


    public DriverDO getDriverWithFreeCar()
    {

        DriverDO driverDO = getDriver();
        CarDO carDO = getCar();
        carDO.setCarStatus(CarStatus.FREE);
        driverDO.setCar(carDO);
        return driverDO;
    }


    public DriverDO getDriverWithBusyCar()
    {

        DriverDO driverDO = getDriver();
        CarDO carDO = getCar();
        carDO.setCarStatus(CarStatus.BUSY);
        driverDO.setCar(carDO);
        return driverDO;
    }


    public DriverDO getDriverWithDeletedCar()
    {

        DriverDO driverDO = getDriver();
        CarDO carDO = getCar();
        carDO.setCarStatus(CarStatus.OFFDUTY);
        carDO.setDeleted(Boolean.TRUE);
        driverDO.setCar(carDO);
        return driverDO;
    }


    public DriverDO getOfflineDriver()
    {

        DriverDO driverDO = getDriver();
        CarDO carDO = getCar();
        carDO.setCarStatus(CarStatus.FREE);
        driverDO.setCar(carDO);
        driverDO.setOnlineStatus(OnlineStatus.OFFLINE);
        return driverDO;
    }


    public DriverDTO getDriverDTO()
    {
        GeoCoordinate geoCoordinate = new GeoCoordinate(90, 90);
        String username = "test";
        String password = "test";

        return DriverDTO.newBuilder().setId(1L).setPassword(password)
            .setUsername(username).setCoordinate(geoCoordinate).createDriverDTO();
    }


    public DriverCarDTO getDriverCarDTO()
    {
        GeoCoordinate geoCoordinate = new GeoCoordinate(90, 90);
        String username = "test";
        String password = "test";

        return DriverCarDTO.newBuilder().setId(1L).setPassword(password)
            .setUsername(username).setCoordinate(geoCoordinate)
            .setCar(getCarManufacturerDTO()).createDriverDTO();
    }


    public SearchDriverFilter getSearchDriverFilter()
    {
        String username = "driver01";
        OnlineStatus onlineStatus = null;
        String licensePlate = null;
        CarRating rating = null;
        String manufacturerName = null;

        SearchDriverFilter searchDriverFilter = new SearchDriverFilter();
        searchDriverFilter.setUsername(username);
        searchDriverFilter.setOnlineStatus(onlineStatus);
        searchDriverFilter.setLicensePlate(licensePlate);
        searchDriverFilter.setRating(rating);
        searchDriverFilter.setManufacturerName(manufacturerName);
        return searchDriverFilter;

    }
}
