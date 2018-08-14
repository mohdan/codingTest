package com.mytaxi.service.car;

import java.util.List;

import javax.persistence.LockModeType;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.ManufacturerRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainvalue.car.CarRating;
import com.mytaxi.domainvalue.car.CarStatus;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some car specific things.
 * <p/>
 */
@Service
public class DefaultCarService implements CarService
{

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;


    /**
     * Selects a car by id.
     *
     * @param carId
     * @return found car
     * @throws EntityNotFoundException
     *             if no car with the given id was found.
     */
    @Override
    public CarDO find(Long carId) throws EntityNotFoundException
    {
        return findCarChecked(carId);
    }


    /**
     * Creates a new car.
     *
     * @param carDO
     * @return
     * @throws ConstraintsViolationException
     *             if a car already exists with the given username, ... .
     * @throws EntityNotFoundException
     */
    @Override
    public CarDO create(CarDO carDO) throws ConstraintsViolationException, EntityNotFoundException
    {

        CarDO car;
        try
        {
            carDO.setManufacturer(manufacturerCheck(carDO.getManufacturer().getId()));
            car = carRepository.save(carDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("Some constraints are thrown due to car creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return car;
    }


    /**
     * Deletes an existing car by id.
     *
     * @param carId
     * @throws EntityNotFoundException
     *             if no car with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long carId) throws EntityNotFoundException
    {

        CarDO carDO = findCarChecked(carId);
        carDO.setDeleted(true);
    }


    /**
     * Update the car details.
     *
     * @param carId
     * @param carDO
     * @throws EntityNotFoundException
     * @throws ConstraintsViolationException
     */

    /**
     * Find all cars by car status state.
     *
     * @param onlineStatus
     */
    @Override
    public List<CarDO> find(CarStatus carStatus)
    {
        return carRepository.findByCarStatus(carStatus);
    }


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    private CarDO findCarChecked(Long carId) throws EntityNotFoundException
    {
        return carRepository.findById(carId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find Car with id: " + carId));
    }


    @Override
    @Transactional
    public void update(Long carId, CarStatus status, CarRating rating) throws EntityNotFoundException
    {
        CarDO car = findCarChecked(carId);
        if (null != status)
        {
            car.setCarStatus(status);
        }
        if (null != rating)
        {
            car.setRating(rating);
        }
    }


    @Override
    public void update(Long carId, CarStatus status) throws EntityNotFoundException
    {
        this.update(carId, status, null);

    }


    @Override
    public void update(Long carId, CarRating rating) throws EntityNotFoundException
    {
        this.update(carId, null, rating);

    }


    private ManufacturerDO manufacturerCheck(final Long manufacturerId) throws EntityNotFoundException
    {
        return manufacturerRepository.findById(manufacturerId).orElseThrow(
            () -> new EntityNotFoundException("Could not find Manufacturer with id: " + manufacturerId));
    }

}
