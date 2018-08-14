package com.mytaxi.util;

import org.junit.Assert;
import org.junit.Test;

public class UtilityConstantsTest
{

    @Test
    public void UUID_CONSTANT_Test()
    {
        String expected = "UUID";
        Assert.assertNotNull(UtilityConstants.UUID_CONSTANT);
        Assert.assertEquals(expected, UtilityConstants.UUID_CONSTANT);
    }
}
