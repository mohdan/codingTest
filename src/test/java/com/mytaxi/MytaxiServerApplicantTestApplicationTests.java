package com.mytaxi;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MytaxiServerApplicantTestApplication.class)
public class MytaxiServerApplicantTestApplicationTests
{

    @Test
    public void contextLoads()
    {}


    @Test(expected = IllegalArgumentException.class) // (timeout = 5000)
    public void testNullArgument() throws Throwable
    {

        MytaxiServerApplicantTestApplication.main((String[]) null);
        fail("Expecting exception: IllegalArgumentException");

    }

}
