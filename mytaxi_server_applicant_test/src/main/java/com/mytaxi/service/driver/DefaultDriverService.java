package com.mytaxi.service.driver;

import java.util.List;

import javax.persistence.LockModeType;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.domainvalue.car.CarRating;
import com.mytaxi.domainvalue.car.CarStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.filter.SearchDriverFilter;
import com.mytaxi.service.car.CarService;
import com.mytaxi.specification.SearchDriverSpecification;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService
{

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    @Autowired
    private CarService carService;

    private final DriverRepository driverRepository;


    public DefaultDriverService(final DriverRepository driverRepository)
    {
        this.driverRepository = driverRepository;
    }


    public void setCarService(CarService carService)
    {
        this.carService = carService;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException
     *             if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException
     *             if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("Some constraints are thrown due to driver creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException
     *             if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException
    {
        return driverRepository.findById(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find driver with id: " + driverId));
    }


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    private DriverDO findOnlineDriver(Long driverId) throws EntityNotFoundException, ConstraintsViolationException
    {
        DriverDO driverDO = findDriverChecked(driverId);

        if (driverDO.getDeleted())
        {
            throw new EntityNotFoundException("Could not find active driver with id: " + driverId);
        }

        if (!OnlineStatus.ONLINE.equals(driverDO.getOnlineStatus()))
        {
            throw new ConstraintsViolationException(String.format("Driver %s should be ONLINE", driverId));
        }

        return driverDO;
    }


    @Transactional
    @Override
    public DriverDO selectCar(Long driverId, Long carId) throws EntityNotFoundException, ConstraintsViolationException,
        CarAlreadyInUseException
    {

        DriverDO driverDO = findOnlineDriver(driverId);
        CarDO driverCarDO = driverDO.getCar();

        if (null != driverCarDO)
        {

            if (CarStatus.BUSY == driverCarDO.getCarStatus())
            {
                throw new ConstraintsViolationException("CarStatus BUSY to OFFDUTY not allowed");
            }
            driverCarDO.setCarStatus(CarStatus.OFFDUTY);
            carService.update(driverCarDO.getId(), CarStatus.OFFDUTY);
        }

        CarDO carDO = carService.find(carId);

        if (carDO.getDeleted() == Boolean.TRUE)
        {
            throw new EntityNotFoundException("Could not find active car with id: " + carId);
        }

        if (carDO.getCarStatus() == CarStatus.OFFDUTY)
        {
            carService.update(carId, CarStatus.FREE);
            driverDO.setCar(carDO);
        }
        else
        {
            throw new CarAlreadyInUseException(String.format("Requested car %s is already Booked", carId));
        }

        return driverDO;
    }


    @Transactional
    @Override
    public DriverDO deselectCar(Long driverId)
        throws EntityNotFoundException, ConstraintsViolationException
    {

        DriverDO driverDO = findOnlineDriver(driverId);
        CarDO carDO = driverDO.getCar();

        if (null == carDO)
        {
            throw new EntityNotFoundException("No car selected by driver " + driverId);
        }

        if (CarStatus.BUSY == carDO.getCarStatus())
        {
            throw new ConstraintsViolationException("CarStatus BUSY to OFFDUTY not allowed");
        }

        carDO.setCarStatus(CarStatus.OFFDUTY);
        driverDO.setCar(null);

        return driverDO;
    }


    @Override
    public SearchDriverFilter checkSearchDriverFilter(String uuid, String username, OnlineStatus onlineStatus,
        String licensePlate, CarRating rating, String manufacturerName)
    {
        SearchDriverFilter searchDriverFilter = new SearchDriverFilter();
        searchDriverFilter.setUsername(username);
        searchDriverFilter.setOnlineStatus(onlineStatus);
        searchDriverFilter.setLicensePlate(licensePlate);
        searchDriverFilter.setRating(rating);
        searchDriverFilter.setManufacturerName(manufacturerName);
        return searchDriverFilter;
    }


    @Override
    public List<DriverDO> searchDriver(SearchDriverFilter searchDriverFilter)
    {
        SearchDriverSpecification searchDriverSpecification = new SearchDriverSpecification(searchDriverFilter);
        return driverRepository.findAll(searchDriverSpecification);
    }

}
