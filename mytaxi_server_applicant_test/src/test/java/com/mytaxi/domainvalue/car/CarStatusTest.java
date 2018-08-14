package com.mytaxi.domainvalue.car;

import org.junit.Assert;
import org.junit.Test;

public class CarStatusTest
{

    @Test
    public void BUSY_Test()
    {
        String expected = "BUSY";
        String result = (CarStatus.BUSY).toString();
        Assert.assertEquals(expected, result);
    }


    @Test
    public void FREE_Test()
    {
        String expected = "FREE";
        String result = (CarStatus.FREE).toString();
        Assert.assertEquals(expected, result);
    }


    @Test
    public void OFFDUTY_Test()
    {
        String expected = "OFFDUTY";
        String result = (CarStatus.OFFDUTY).toString();
        Assert.assertEquals(expected, result);
    }

}
