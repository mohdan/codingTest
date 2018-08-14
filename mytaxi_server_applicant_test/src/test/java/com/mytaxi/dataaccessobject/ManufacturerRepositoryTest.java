package com.mytaxi.dataaccessobject;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.mytaxi.MytaxiServerApplicantTestApplication;
import com.mytaxi.domainobject.ManufacturerDO;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MytaxiServerApplicantTestApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class ManufacturerRepositoryTest
{

    @Autowired
    private ManufacturerRepository manufacturerRepository;


    @Test
    public void finById_FoundTest()
    {
        Long manufacturerId = 1L;
        Optional<ManufacturerDO> manufacturer = manufacturerRepository.findById(manufacturerId);
        Assert.assertTrue(manufacturer.isPresent());
        Assert.assertEquals(manufacturer.get().getId(), manufacturerId);
    }


    @Test
    public void finById_NotFoundTest()
    {
        Long manufacturerId = 10L;
        Optional<ManufacturerDO> manufacturer = manufacturerRepository.findById(manufacturerId);
        Assert.assertFalse(manufacturer.isPresent());
    }

}
