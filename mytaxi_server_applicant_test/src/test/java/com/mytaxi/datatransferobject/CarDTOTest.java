package com.mytaxi.datatransferobject;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.mytaxi.junitutils.PojoTestUtil;

public class CarDTOTest
{

    private static final String[] FIELDS = {"id", "licensePlate", "seatCount", "rating", "engineType", "convertable", "manufacturerId"};
    /*
    @Test
    public void gettersTest()
    		throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	PojoTestUtil.testGetters(CarDTO.class, FIELDS);
    }
    
    @Test
    public void settersTest()
    		throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	PojoTestUtil.testSetters(SearchDriverFilter.class, FIELDS);
    }*/
}
