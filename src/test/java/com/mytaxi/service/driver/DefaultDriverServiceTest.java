package com.mytaxi.service.driver;

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
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.domainvalue.car.CarRating;
import com.mytaxi.domainvalue.car.CarStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.filter.SearchDriverFilter;
import com.mytaxi.service.car.DefaultCarService;
import com.mytaxi.specification.SearchDriverSpecification;

public class DefaultDriverServiceTest extends TestUtils
{

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DefaultDriverService driverService;

    @Mock
    private DefaultCarService carService;


    @BeforeClass
    public static void setUp()
    {
        MockitoAnnotations.initMocks(DefaultDriverService.class);
    }


    @Test
    public void findTest()
    {
        DriverDO driver = getDriver();
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.of(driver));
        try
        {
            driverService.find(any(Long.class));
        }
        catch (EntityNotFoundException e)
        {
            Assert.fail();
        }
        verify(driverRepository, times(1)).findById(any(Long.class));
    }


    @Test(expected = EntityNotFoundException.class)
    public void findTest_EntityNotFoundException() throws EntityNotFoundException
    {
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        driverService.find(any(Long.class));
        verify(driverRepository, times(1)).findById(any(Long.class));
    }


    @Test
    public void createTest()
    {
        DriverDO driver = getDriver();
        when(driverRepository.save(any())).thenReturn(driver);
        try
        {
            driverService.create(any(DriverDO.class));
        }
        catch (ConstraintsViolationException e)
        {
            Assert.fail();
        }
        verify(driverRepository, times(1)).save(any());
    }


    @Test(expected = ConstraintsViolationException.class)
    public void createTest_ConstraintsViolationException() throws ConstraintsViolationException
    {
        when(driverRepository.save(any()))
            .thenThrow(new DataIntegrityViolationException("Some constraints are violated ..."));
        driverService.create(any(DriverDO.class));
        verify(driverRepository, times(1)).save(any());
    }


    @Test
    public void deleteTest()
    {
        DriverDO driver = getDriver();
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.of(driver));
        try
        {
            driverService.delete(any(Long.class));
        }
        catch (EntityNotFoundException e)
        {
            Assert.fail();
        }
        verify(driverRepository, times(1)).findById(any(Long.class));
    }


    @Test(expected = EntityNotFoundException.class)
    public void deleteTest_EntityNotFoundException() throws EntityNotFoundException
    {
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        driverService.delete(any(Long.class));
        verify(driverRepository, times(1)).findById(any(Long.class));
    }


    @Test
    public void updateLocationTest()
    {
        DriverDO driver = getDriver();
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.of(driver));
        try
        {
            driverService.updateLocation(1L, 90.0, 90.0);
        }
        catch (EntityNotFoundException e)
        {
            Assert.fail();
        }
        verify(driverRepository, times(1)).findById(any(Long.class));
    }


    @Test(expected = EntityNotFoundException.class)
    public void updateLocationTest_EntityNotFoundException() throws EntityNotFoundException
    {
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        driverService.updateLocation(1L, 90.0, 90.0);
        verify(driverRepository, times(1)).findById(any(Long.class));
    }


    @Test
    public void findByOnlineStatusTest()
    {
        List<DriverDO> drivers = Collections.singletonList(getDriver());
        when(driverRepository.findByOnlineStatus(any(OnlineStatus.class))).thenReturn(drivers);
        driverService.find(OnlineStatus.ONLINE);
        verify(driverRepository, times(1)).findByOnlineStatus(any(OnlineStatus.class));
    }


    @Test
    public void findDriverCheckedTest()
    {
        DriverDO driver = getDriver();
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.of(driver));
        try
        {
            driverService.find(any(Long.class));
        }
        catch (EntityNotFoundException e)
        {
            Assert.fail();
        }
        verify(driverRepository, times(1)).findById(any(Long.class));
    }


    @Test(expected = EntityNotFoundException.class)
    public void findDriverCheckedTest_EntityNotFoundException() throws EntityNotFoundException
    {
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        driverService.find(any(Long.class));
        verify(driverRepository, times(1)).findById(any(Long.class));
    }


    @Test
    public void deselectCarTest()
    {
        DriverDO driver = getDriverWithFreeCar();
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.of(driver));
        try
        {
            driverService.deselectCar(any(Long.class));
        }
        catch (EntityNotFoundException | ConstraintsViolationException e)
        {
            Assert.fail();
        }
        verify(driverRepository, times(1)).findById(any(Long.class));

    }


    @Test(expected = ConstraintsViolationException.class)
    public void deselectCarTest_CarStatusUpdateException() throws ConstraintsViolationException
    {
        DriverDO driver = getDriverWithBusyCar();
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.of(driver));
        try
        {
            driverService.deselectCar(any(Long.class));
        }
        catch (EntityNotFoundException e)
        {
            Assert.fail();
        }
        verify(driverRepository, times(1)).findById(any(Long.class));

    }


    @Test(expected = EntityNotFoundException.class)
    public void deselectCarTest_DriverNotFound() throws EntityNotFoundException
    {
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        try
        {
            driverService.deselectCar(any(Long.class));
        }
        catch (ConstraintsViolationException e)
        {
            Assert.fail();
        }
        verify(driverRepository, times(1)).findById(any(Long.class));

    }


    @Test(expected = EntityNotFoundException.class)
    public void deselectCarTest_DriverDeleted() throws EntityNotFoundException
    {
        DriverDO driver = getDriver();
        driver.setDeleted(Boolean.TRUE);
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.of(driver));
        try
        {
            driverService.deselectCar(any(Long.class));
        }
        catch (ConstraintsViolationException e)
        {
            Assert.fail();
        }
        verify(driverRepository, times(1)).findById(any(Long.class));

    }


    @Test(expected = ConstraintsViolationException.class)
    public void deselectCarTest_DriverNotOnline() throws ConstraintsViolationException
    {
        DriverDO driver = getOfflineDriver();
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.of(driver));
        try
        {
            driverService.deselectCar(any(Long.class));
        }
        catch (EntityNotFoundException e)
        {
            Assert.fail();
        }
        verify(driverRepository, times(1)).findById(any(Long.class));

    }


    @Test(expected = EntityNotFoundException.class)
    public void deselectCarTest_CarNotFound() throws EntityNotFoundException
    {
        DriverDO driver = getDriver();
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.of(driver));
        try
        {
            driverService.deselectCar(any(Long.class));
        }
        catch (ConstraintsViolationException e)
        {
            Assert.fail();
        }
        verify(driverRepository, times(1)).findById(any(Long.class));

    }


    @Test
    public void checkSearchDriverFilterTest()
    {

        String uuid = "uuid";
        String username = "username";
        OnlineStatus onlineStatus = OnlineStatus.ONLINE;
        String licensePlate = "licensePlate";
        CarRating rating = CarRating.NO_RATING;
        String manufacturerName = "manufacturerName";

        SearchDriverFilter searchDriverFilter = driverService.checkSearchDriverFilter(uuid, username, onlineStatus,
            licensePlate, rating, manufacturerName);
        Assert.assertEquals(username, searchDriverFilter.getUsername());
        Assert.assertEquals(onlineStatus, searchDriverFilter.getOnlineStatus());
        Assert.assertEquals(licensePlate, searchDriverFilter.getLicensePlate());
        Assert.assertEquals(rating, searchDriverFilter.getRating());
        Assert.assertEquals(manufacturerName, searchDriverFilter.getManufacturerName());

    }


    @Test
    public void searchDriverTest()
    {
        List<DriverDO> drivers = Collections.singletonList(getDriver());
        when(driverRepository.findAll(any(SearchDriverSpecification.class))).thenReturn(drivers);
        driverService.searchDriver(any(SearchDriverFilter.class));
        verify(driverRepository, times(1)).findAll(any(SearchDriverSpecification.class));

    }


    @Test
    public void selectCarTest()
    {
        DriverDO driver = getDriver();
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.of(driver));
        CarDO car = getCar();
        driverService.setCarService(carService);

        try
        {
            when(carService.find(any(Long.class))).thenReturn(car);
        }
        catch (EntityNotFoundException e1)
        {
            Assert.fail();
        }

        try
        {
            driverService.selectCar(1L, 1L);
            verify(driverRepository, times(1)).findById(any(Long.class));
            verify(carService, times(1)).find(any(Long.class));
        }
        catch (EntityNotFoundException | ConstraintsViolationException | CarAlreadyInUseException e)
        {
            Assert.fail();
        }

    }


    @Test(expected = ConstraintsViolationException.class)
    public void selectCarTest_ConstraintsViolationException() throws ConstraintsViolationException
    {
        DriverDO driver = getDriverWithBusyCar();
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.of(driver));
        driverService.setCarService(carService);

        try
        {
            driverService.selectCar(1L, 1L);
            verify(driverRepository, times(1)).findById(any(Long.class));
            verify(carService, times(1)).find(any(Long.class));
        }
        catch (EntityNotFoundException | CarAlreadyInUseException e)
        {
            Assert.fail();
        }

    }


    @Test(expected = EntityNotFoundException.class)
    public void selectCarTest_EntityNotFoundException() throws EntityNotFoundException
    {
        DriverDO driver = getDriverWithDeletedCar();
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.of(driver));
        CarDO car = getCar();
        car.setDeleted(Boolean.TRUE);
        driverService.setCarService(carService);

        try
        {
            when(carService.find(any(Long.class))).thenReturn(car);
        }
        catch (EntityNotFoundException e1)
        {
            Assert.fail();
        }

        try
        {
            driverService.selectCar(1L, 1L);
            verify(driverRepository, times(1)).findById(any(Long.class));
            verify(carService, times(1)).find(any(Long.class));
        }
        catch (ConstraintsViolationException | CarAlreadyInUseException e)
        {
            Assert.fail();
        }

    }


    @Test(expected = CarAlreadyInUseException.class)
    public void selectCarTest_CarAlreadyInUseException() throws CarAlreadyInUseException
    {
        DriverDO driver = getDriver();
        when(driverRepository.findById(any(Long.class))).thenReturn(Optional.of(driver));
        CarDO car = getCar();
        car.setCarStatus(CarStatus.BUSY);
        driverService.setCarService(carService);

        try
        {
            when(carService.find(any(Long.class))).thenReturn(car);
        }
        catch (EntityNotFoundException e1)
        {
            Assert.fail();
        }

        try
        {
            driverService.selectCar(1L, 1L);
            verify(driverRepository, times(1)).findById(any(Long.class));
            verify(carService, times(1)).find(any(Long.class));
        }
        catch (EntityNotFoundException | ConstraintsViolationException e)
        {
            Assert.fail();
        }

    }

}
