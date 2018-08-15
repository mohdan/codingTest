package com.mytaxi;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MytaxiServerApplicantTestApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class MytaxiConfigurationTest
{

    @Autowired
    private MytaxiConfiguration configuration;


    @Test
    public void getMytaxiServiceEncryptionKeyTest()
    {
        Assert.assertNotNull(configuration.getMytaxiServiceEncryptionKey());
    }
}
