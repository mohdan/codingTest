package com.mytaxi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.controller.mapper.CarManufacturerMapper;
import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.datatransferobject.CarManufacturerDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.car.CarRating;
import com.mytaxi.domainvalue.car.CarStatus;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.car.CarService;

/**
 * All operations with a car will be routed by this controller.
 * <p/>
 */
@RestController("carController")
@RequestMapping("v1/cars")
public class CarController
{

    private final CarService carService;


    @Autowired
    public CarController(final CarService carService)
    {
        this.carService = carService;
    }


    @GetMapping("/{carId}")
    public CarManufacturerDTO getCar(@Valid @PathVariable long carId) throws EntityNotFoundException
    {
        return CarManufacturerMapper.makeCarManufacturerDTO(carService.find(carId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarManufacturerDTO createCar(@Valid @RequestBody CarDTO carDTO)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        return CarManufacturerMapper.makeCarManufacturerDTO(carService.create(carDO));
    }


    @DeleteMapping("/{carId}")
    public void deleteCar(@Valid @PathVariable long carId) throws EntityNotFoundException
    {
        carService.delete(carId);
    }


    @PutMapping("/{carId}")
    public void update(@Valid @PathVariable long carId, @RequestParam Optional<CarStatus> status,
        @RequestParam Optional<CarRating> rating) throws ConstraintsViolationException, EntityNotFoundException
    {
        carService.update(carId, status.isPresent() ? status.get() : null, rating.isPresent() ? rating.get() : null);

    }


    @GetMapping
    public List<CarManufacturerDTO> findCars(@RequestParam CarStatus status)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        return CarManufacturerMapper.makeCarManufacturerDTOList(carService.find(status));
    }
}
