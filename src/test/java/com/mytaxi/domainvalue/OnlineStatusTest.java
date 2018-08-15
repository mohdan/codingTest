package com.mytaxi.domainvalue;

import org.junit.Assert;
import org.junit.Test;

public class OnlineStatusTest
{

    @Test
    public void ONLINE_Test()
    {
        String expected = "ONLINE";
        String result = (OnlineStatus.ONLINE).toString();
        Assert.assertEquals(expected, result);
    }


    @Test
    public void OFFLINE_Test()
    {
        String expected = "OFFLINE";
        String result = (OnlineStatus.OFFLINE).toString();
        Assert.assertEquals(expected, result);
    }

}
