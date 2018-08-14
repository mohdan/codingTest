package com.mytaxi.service.car;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import com.mytaxi.TestUtils;
import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.ManufacturerRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainvalue.car.CarRating;
import com.mytaxi.domainvalue.car.CarStatus;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

public class DefaultCarServiceTest extends TestUtils
{

    @Mock
    private CarRepository carRepository;

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private DefaultCarService carService;


    @BeforeClass
    public static void setUp()
    {
        MockitoAnnotations.initMocks(DefaultCarService.class);
    }


    @Test
    public void findTest()
    {
        CarDO car = getCar();
        when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
        try
        {
            carService.find(any(Long.class));
        }
        catch (EntityNotFoundException e)
        {
            Assert.fail();
        }
        verify(carRepository, times(1)).findById(any(Long.class));
    }


    @Test(expected = EntityNotFoundException.class)
    public void findTest_EntityNotFoundException() throws EntityNotFoundException
    {
        when(carRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        carService.find(any(Long.class));
        verify(carRepository, times(1)).findById(any(Long.class));
    }


    @Test
    public void createTest()
    {
        CarDO car = getCar();
        ManufacturerDO manufacturer = getManufacturer();
        when(manufacturerRepository.findById(any(Long.class))).thenReturn(Optional.of(manufacturer));
        when(carRepository.save(any())).thenReturn(car);
        try
        {
            carService.create(car);
        }
        catch (ConstraintsViolationException | EntityNotFoundException e)
        {
            Assert.fail();
        }
        verify(manufacturerRepository, times(1)).findById(any(Long.class));
        verify(carRepository, times(1)).save(any());
    }


    @Test(expected = EntityNotFoundException.class)
    public void createTest_EntityNotFoundException() throws EntityNotFoundException
    {
        when(manufacturerRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        try
        {
            carService.create(getCar());
        }
        catch (ConstraintsViolationException e)
        {
            Assert.fail();
        }
        verify(manufacturerRepository, times(1)).findById(any(Long.class));
        verify(carRepository, times(1)).save(any());
    }


    @Test(expected = ConstraintsViolationException.class)
    public void createTest_ConstraintsViolationException() throws ConstraintsViolationException
    {
        CarDO car = getCar();
        ManufacturerDO manufacturer = getManufacturer();
        when(manufacturerRepository.findById(any(Long.class))).thenReturn(Optional.of(manufacturer));
        when(carRepository.save(any()))
            .thenThrow(new DataIntegrityViolationException("Some constraints are violated ..."));
        try
        {
            carService.create(car);
        }
        catch (EntityNotFoundException e)
        {
            Assert.fail();
        }
        verify(manufacturerRepository, times(1)).findById(any(Long.class));
        verify(carRepository, times(1)).save(any());
    }


    @Test
    public void updateTest()
    {
        CarDO car = getCar();
        when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
        try
        {
            carService.update(1L, CarStatus.FREE, CarRating.NO_RATING);
        }
        catch (EntityNotFoundException e)
        {
            Assert.fail();
        }
        verify(carRepository, times(1)).findById(any(Long.class));
    }


    @Test(expected = EntityNotFoundException.class)
    public void updateTest_EntityNotFoundException() throws EntityNotFoundException
    {
        when(carRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        carService.update(1L, CarStatus.FREE, CarRating.NO_RATING);
        verify(carRepository, times(1)).findById(any(Long.class));
    }


    @Test
    public void updateStatusTest()
    {
        CarDO car = getCar();
        when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
        try
        {
            carService.update(1L, CarStatus.FREE);
        }
        catch (EntityNotFoundException e)
        {
            Assert.fail();
        }
        verify(carRepository, times(1)).findById(any(Long.class));
    }


    @Test
    public void updateRatingTest()
    {
        CarDO car = getCar();
        when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
        try
        {
            carService.update(1L, CarRating.FIVE_STAR);
        }
        catch (EntityNotFoundException e)
        {
            Assert.fail();
        }
        verify(carRepository, times(1)).findById(any(Long.class));
    }


    @Test
    public void deleteTest()
    {
        CarDO car = getCar();
        when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(car));
        try
        {
            carService.delete(1L);
        }
        catch (EntityNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        verify(carRepository, times(1)).findById(any(Long.class));
    }


    @Test(expected = EntityNotFoundException.class)
    public void deleteTest_EntityNotFoundException() throws EntityNotFoundException
    {
        when(carRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        carService.delete(1L);
        verify(carRepository, times(1)).findById(any(Long.class));
    }


    @Test
    public void findCarsByStatus_Test()
    {
        List<CarDO> cars = Collections.singletonList(getCar());
        when(carRepository.findByCarStatus(any(CarStatus.class))).thenReturn(cars);
        carService.find(CarStatus.BUSY);
        verify(carRepository, times(1)).findByCarStatus(any(CarStatus.class));
    }
}
