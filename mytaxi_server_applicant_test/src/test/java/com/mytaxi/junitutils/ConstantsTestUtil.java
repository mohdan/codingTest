package com.mytaxi.junitutils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;

@SuppressWarnings("PMD")
public class ConstantsTestUtil
{

    private ConstantsTestUtil()
    {
        throw new IllegalStateException("Util class cannot be initialised.");
    }


    /**
     * Validates the String constants mapping of a class.
     *
     * @param clazz The class to test.
     * @param map The expected constants mapping.
     * @throws java.lang.IllegalAccessException
     */
    public static void testStringConstants(final Class clazz,
        final Map<String, String> map) throws IllegalArgumentException, IllegalAccessException
    {
        final Set<String> foundConstants = new HashSet<>();
        final Set<String> notFoundConstants = new HashSet<>();
        final Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields)
        {
            if (!Modifier.isStatic(field.getModifiers()))
            {
                continue;
            }
            if (!Modifier.isFinal(field.getModifiers()))
            {
                continue;
            }
            if (field.getType() != String.class)
            {
                continue;
            }
            if (!map.containsKey(field.getName()))
            {
                notFoundConstants.add(field.getName());
                continue;
            }
            foundConstants.add(field.getName());
            final boolean isAccessable = field.isAccessible();
            try
            {
                field.setAccessible(true);
                String actValue = (String) field.get(null);
                String expValue = map.get(field.getName());
                if (expValue == null)
                {
                    Assert.assertNull(String.format("Expected null for field "
                        + "[%s], but found [%s].", field.getName(),
                        actValue), actValue);
                }
                else
                {
                    Assert.assertTrue(String.format("Expected [%s] but found "
                        + "[%s].", expValue, actValue),
                        expValue.equals(actValue));
                }
            }
            finally
            {
                field.setAccessible(isAccessable);
            }
        }
        if (!notFoundConstants.isEmpty())
        {
            notFoundConstants.forEach(str -> System.out.println(
                String.format("Found constant [%s] that was not expected.",
                    str)));
            Assert.fail("Found missing not expected constants. See std.out.");
        }
        if (foundConstants.size() != map.size())
        {
            map.forEach((String key, String val) -> {
                if (!foundConstants.contains(key))
                {
                    System.out.println(String.format("Expected constant [%s] "
                        + "but not found in class.", key));
                }
            });
            Assert.fail("Some expected constants not found. See std.out.");
        }
    }
}
