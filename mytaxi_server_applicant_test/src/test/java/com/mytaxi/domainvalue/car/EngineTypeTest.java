package com.mytaxi.domainvalue.car;

import org.junit.Assert;
import org.junit.Test;

public class EngineTypeTest
{

    @Test
    public void GAS_Test()
    {
        String expected = "GAS";
        String result = (EngineType.GAS).toString();
        Assert.assertEquals(expected, result);
    }


    @Test
    public void ELECTRIC_Test()
    {
        String expected = "ELECTRIC";
        String result = (EngineType.ELECTRIC).toString();
        Assert.assertEquals(expected, result);
    }

}
