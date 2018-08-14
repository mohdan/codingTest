package com.mytaxi.junitutils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;

@SuppressWarnings("PMD")
public class PojoTestUtil
{

    private PojoTestUtil()
    {
        throw new IllegalStateException("Util class cannot be initialised.");
    }

    private static final Random RAND = new Random();

    private enum Mode
    {
        GET, SET
    }


    /**
     * Tests the getters of a class via reflection. All non-primitive objects
     * must have a parameter-less constructor and objects are not tested
     * recursively. All standard naming conventions must be obeyed as there are
     * assumptions on the names taking place.
     *
     * An assert failures happens when a field with a public getter does not
     * return the field value.
     *
     * @param clazz The POJO class to test
     * @param propertyNames The list of property names for which there are
     * getters.
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    public static <T> void testGetters(final Class<T> clazz,
        final String... propertyNames) throws InstantiationException,
        IllegalAccessException, IllegalArgumentException,
        InvocationTargetException
    {
        doTest(clazz, Mode.GET, propertyNames);
    }


    /**
     * Tests the getters of a class via reflection. All non-primitive objects
     * must have a parameter-less constructor and objects are not tested
     * recursively. All standard naming conventions must be obeyed as there are
     * assumptions on the names taking place.
     *
     * An assert failures happens when a field with a public getter does not
     * return the field value.
     *
     * @param clazz The POJO class to test
     * @param propertyNames The list of property names for which there are
     * getters.
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    public static <T> void testSetters(final Class<T> clazz,
        final String... propertyNames) throws InstantiationException,
        IllegalAccessException, IllegalArgumentException,
        InvocationTargetException
    {
        doTest(clazz, Mode.SET, propertyNames);
    }


    private static <T> void doTest(final Class<T> clazz, final Mode mode,
        final String... propertyNames) throws InstantiationException,
        IllegalAccessException, IllegalArgumentException,
        InvocationTargetException
    {
        final Set<String> propNames = new HashSet<>();
        propNames.addAll(Arrays.asList(propertyNames));
        final Set<String> procNames = new HashSet<>();
        final T o = clazz.newInstance();
        final Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields)
        {
            if (Modifier.isStatic(field.getModifiers()))
            {
                continue;
            }
            final String methodName;
            switch (mode)
            {
                case GET:
                    methodName = getMethodName(field.getName(),
                        field.getType() == boolean.class ? "is" : "get");
                    break;
                case SET:
                    methodName = getMethodName(field.getName(), "set");
                    break;
                default:
                    throw new UnsupportedOperationException(mode.toString());
            }
            final Method method;
            try
            {
                switch (mode)
                {
                    case GET:
                        method = clazz.getMethod(methodName);
                        break;
                    case SET:
                        method = clazz.getMethod(methodName, field.getType());
                        break;
                    default:
                        throw new UnsupportedOperationException(mode.toString());
                }
            }
            catch (NoSuchMethodException ex)
            {
                continue; //ignore
            }
            if (!Modifier.isPublic(method.getModifiers()))
            {
                continue;
            }
            if (!propNames.contains(field.getName()))
            {
                Assert.fail(String.format("Field [%s] does not exist.",
                    field.getName()));
                continue; //redundant
            }
            final boolean isAccessible = field.isAccessible();
            try
            {
                if (!isAccessible)
                {
                    field.setAccessible(true);
                }
                Class fieldClass = field.getType();
                final Object fieldObj = initialiseType(fieldClass);
                switch (mode)
                {
                    case GET:
                        field.set(o, fieldObj);
                        final Object testObj = method.invoke(o);
                        Assert.assertTrue(fieldObj.equals(testObj));
                        break;
                    case SET:
                        final Object ret = method.invoke(o, fieldObj);
                        Assert.assertNull(ret);
                        final Object actVal = field.get(o);
                        Assert.assertTrue(fieldObj.equals(actVal));
                        break;
                    default:
                        throw new UnsupportedOperationException(mode.toString());
                }
                procNames.add(field.getName());
            }
            finally
            {
                field.setAccessible(isAccessible);
            }
        }
        if (procNames.size() != propNames.size())
        {
            propNames.forEach(name -> {
                if (!procNames.contains(name))
                {
                    System.out.println(
                        String.format("Field [%s] not found.", name));
                }
            });
            Assert.fail("See std.out.");
        }
    }


    private static String getMethodName(String name, final String prefix)
    {
        String firstLetter = name.substring(0, 1);
        return String.format("%s%s%s", prefix, firstLetter.toUpperCase(),
            name.substring(1));
    }


    private static Object initialiseType(Class fieldClass) throws InstantiationException, IllegalAccessException
    {
        if (fieldClass == Long.class || fieldClass == long.class)
        {
            return RAND.nextLong();
        }
        if (fieldClass == Integer.class || fieldClass == int.class)
        {
            return RAND.nextInt();
        }
        if (fieldClass == Byte.class || fieldClass == byte.class)
        {
            return (byte) (RAND.nextInt(255) - 128);
        }
        if (fieldClass == Short.class || fieldClass == short.class)
        {
            return (short) (RAND.nextInt(65535) - 32768);
        }
        if (fieldClass == Character.class || fieldClass == char.class)
        {
            return (char) (RAND.nextInt(100) + 25);
        }
        if (fieldClass == Float.class || fieldClass == float.class)
        {
            return RAND.nextFloat();
        }
        if (fieldClass == Double.class || fieldClass == double.class)
        {
            return RAND.nextDouble();
        }
        if (fieldClass == Boolean.class || fieldClass == boolean.class)
        {
            return RAND.nextBoolean();
        }
        if (fieldClass == String.class)
        {
            return UUID.randomUUID().toString();
        }
        if (fieldClass.isEnum())
        {
            Object[] enumConsts = fieldClass.getEnumConstants();
            return enumConsts[RAND.nextInt(enumConsts.length)];
        }
        if (fieldClass == List.class)
        {
            return new ArrayList<>();
        }
        if (fieldClass == Map.class)
        {
            return new HashMap<>();
        }
        if (fieldClass == Page.class)
        {
            return Mockito.mock(Page.class);
        }
        if (fieldClass.isArray())
        {
            return Array.newInstance(fieldClass.getComponentType(), 0);
        }
        return fieldClass.newInstance();
    }


    /**
     * Tests that a class has a private empty constructor. It is expected that
     * if somehow this constructor is still called, some exception is thrown.
     *
     * It also tests that there are no non-private constructors available.
     *
     * @param clazz The class to test.
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    public static void privateEmptyConstructorTest(final Class clazz)
        throws InstantiationException, IllegalAccessException,
        IllegalArgumentException
    {
        final Constructor[] constructors = clazz.getDeclaredConstructors();
        for (final Constructor constructor : constructors)
        {
            if (!Modifier.isPrivate(constructor.getModifiers()))
            {
                Assert.fail(String.format("Found a non-private constructor for "
                    + "class [%s].", clazz.getName()));
                continue;
            }
            final boolean isAccessible = constructor.isAccessible();
            try
            {
                constructor.setAccessible(true);
                Assert.assertEquals(0, constructor.getParameterCount());
                try
                {
                    constructor.newInstance();
                    Assert.fail();
                }
                catch (InvocationTargetException ex)
                {
                    //good
                }
            }
            finally
            {
                constructor.setAccessible(isAccessible);
            }
        }
    }
}
