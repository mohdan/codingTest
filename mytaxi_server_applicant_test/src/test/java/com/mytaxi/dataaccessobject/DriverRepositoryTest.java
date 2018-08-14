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
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.domainvalue.car.CarRating;
import com.mytaxi.domainvalue.car.CarStatus;
import com.mytaxi.filter.SearchDriverFilter;
import com.mytaxi.specification.SearchDriverSpecification;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MytaxiServerApplicantTestApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class DriverRepositoryTest
{

    @Autowired
    private DriverRepository driverRepository;


    @Test
    public void finById_FoundTest()
    {
        Long driverId = 1L;
        Optional<DriverDO> driver = driverRepository.findById(driverId);
        Assert.assertTrue(driver.isPresent());
        Assert.assertEquals(driver.get().getId(), driverId);
    }


    @Test
    public void finById_NotFoundTest()
    {
        Long driverId = 10L;
        Optional<DriverDO> driver = driverRepository.findById(driverId);
        Assert.assertFalse(driver.isPresent());
    }


    @Test
    public void findByOnlineStatus_OnlineTest()
    {
        List<DriverDO> onlineDrivers = driverRepository.findByOnlineStatus(OnlineStatus.ONLINE);
        Assert.assertThat(onlineDrivers, hasSize(4));
        onlineDrivers.forEach(d -> Assert.assertTrue(OnlineStatus.ONLINE.equals(d.getOnlineStatus())));
    }


    @Test
    public void findByOnlineStatus_OfflineTest()
    {
        List<DriverDO> offlineDrivers = driverRepository.findByOnlineStatus(OnlineStatus.OFFLINE);
        Assert.assertThat(offlineDrivers, hasSize(4));
        offlineDrivers.forEach(d -> Assert.assertTrue(OnlineStatus.OFFLINE.equals(d.getOnlineStatus())));
    }


    @Test
    public void search_username_Test()
    {
        String username = "driver01";
        SearchDriverFilter searchDriverFilter = new SearchDriverFilter();
        searchDriverFilter.setUsername(username);

        List<DriverDO> firstDriver = driverRepository.findAll(new SearchDriverSpecification(searchDriverFilter));
        Assert.assertThat(firstDriver, hasSize(1));
        firstDriver.forEach(d -> Assert.assertTrue(username.equals(d.getUsername())));

    }


    @Test
    public void search_online_Test()
    {
        OnlineStatus onlineStatus = OnlineStatus.ONLINE;
        SearchDriverFilter searchDriverFilter = new SearchDriverFilter();
        searchDriverFilter.setOnlineStatus(onlineStatus);;

        List<DriverDO> onlineDrivers = driverRepository.findAll(new SearchDriverSpecification(searchDriverFilter));
        Assert.assertThat(onlineDrivers, hasSize(4));
        //  Assert.assertTrue(onlineDrivers.stream().allMatch(d ->onlineStatus.equals(d.getUsername())));

    }

}
