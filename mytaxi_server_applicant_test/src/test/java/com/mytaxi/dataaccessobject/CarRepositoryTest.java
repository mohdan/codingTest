package com.mytaxi.dataaccessobject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.mytaxi.MytaxiServerApplicantTestApplication;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.car.CarStatus;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MytaxiServerApplicantTestApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class CarRepositoryTest
{

    @Autowired
    private CarRepository carRepository;


    @Test
    public void finById_FoundTest()
    {
        Long carId = 1L;
        Optional<CarDO> car = carRepository.findById(carId);
        Assert.assertTrue(car.isPresent());
        Assert.assertEquals(car.get().getId(), carId);
    }


    @Test
    public void finById_NotFoundTest()
    {
        Long carId = 10L;
        Optional<CarDO> car = carRepository.findById(carId);
        Assert.assertFalse(car.isPresent());
    }


    @Test
    public void findByCarStatus_BusyTest()
    {
        List<CarDO> busyCars = carRepository.findByCarStatus(com.mytaxi.domainvalue.car.CarStatus.BUSY);
        Assert.assertThat(busyCars, hasSize(2));
        busyCars.forEach(c -> Assert.assertTrue(CarStatus.BUSY.equals(c.getCarStatus())));
    }


    @Test
    public void findByCarStatus_FreeTest()
    {
        List<CarDO> freeCars = carRepository.findByCarStatus(com.mytaxi.domainvalue.car.CarStatus.FREE);
        Assert.assertThat(freeCars, hasSize(2));
        freeCars.forEach(c -> Assert.assertTrue(CarStatus.FREE.equals(c.getCarStatus())));
    }


    @Test
    public void findByCarStatus_OffDutyTest()
    {
        List<CarDO> offDutyCars = carRepository.findByCarStatus(com.mytaxi.domainvalue.car.CarStatus.OFFDUTY);
        Assert.assertThat(offDutyCars, hasSize(2));
        offDutyCars.forEach(c -> Assert.assertTrue(CarStatus.OFFDUTY.equals(c.getCarStatus())));
    }

}
