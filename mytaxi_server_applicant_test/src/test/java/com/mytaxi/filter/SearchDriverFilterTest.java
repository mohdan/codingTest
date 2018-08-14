package com.mytaxi.filter;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.mytaxi.junitutils.PojoTestUtil;

public class SearchDriverFilterTest
{

    private static final String[] FIELDS = {"username", "onlineStatus", "licensePlate", "rating", "manufacturerName"};


    @Test
    public void gettersTest()
        throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        PojoTestUtil.testGetters(SearchDriverFilter.class, FIELDS);
    }


    @Test
    public void settersTest()
        throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        PojoTestUtil.testSetters(SearchDriverFilter.class, FIELDS);
    }

}
