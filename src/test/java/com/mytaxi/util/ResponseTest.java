package com.mytaxi.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mytaxi.junitutils.ConstantsTestUtil;
import com.mytaxi.junitutils.PojoTestUtil;

public class ResponseTest
{

    public ResponseTest()
    {}


    @BeforeClass
    public static void setUpClass()
    {}


    @AfterClass
    public static void tearDownClass()
    {}


    @Before
    public void setUp()
    {}


    @After
    public void tearDown()
    {}

    private static final String[] FIELDS = {"uuid", "data", "errors"};

    private static final Map<String, String> CONSTANTS = buildConstants();


    private static Map<String, String> buildConstants()
    {
        final Map<String, String> map = new HashMap<>();
        map.put("UUID", "uuid");
        map.put("DATA", "data");
        map.put("ERRORS", "errors");
        return map;
    }


    @Test
    public void gettersTest()
        throws InstantiationException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException
    {
        PojoTestUtil.testGetters(Response.class, FIELDS);
    }


    @Test
    public void settersTest()
        throws InstantiationException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException
    {
        PojoTestUtil.testSetters(Response.class, FIELDS);
    }


    @Test
    public void constantsTest() throws IllegalArgumentException,
        IllegalAccessException
    {
        ConstantsTestUtil.testStringConstants(Response.JsonKeys.class, CONSTANTS);
    }


    @Test
    public void privateConstantsConstructorTest()
        throws InstantiationException, IllegalAccessException,
        IllegalArgumentException
    {
        PojoTestUtil.privateEmptyConstructorTest(Response.JsonKeys.class);
    }

}
