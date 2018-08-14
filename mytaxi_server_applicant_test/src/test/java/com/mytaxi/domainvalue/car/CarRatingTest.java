package com.mytaxi.domainvalue.car;

import org.junit.Assert;
import org.junit.Test;

public class CarRatingTest
{

    @Test
    public void NO_RATING_Test()
    {
        String expected = "NO_RATING";
        String result = (CarRating.NO_RATING).toString();
        Assert.assertEquals(expected, result);
    }


    @Test
    public void ONE_STAR_Test()
    {
        String expected = "ONE_STAR";
        String result = (CarRating.ONE_STAR).toString();
        Assert.assertEquals(expected, result);
    }


    @Test
    public void TWO_STAR_Test()
    {
        String expected = "TWO_STAR";
        String result = (CarRating.TWO_STAR).toString();
        Assert.assertEquals(expected, result);
    }


    @Test
    public void THREE_STAR_Test()
    {
        String expected = "THREE_STAR";
        String result = (CarRating.THREE_STAR).toString();
        Assert.assertEquals(expected, result);
    }


    @Test
    public void FOUR_STAR_Test()
    {
        String expected = "FOUR_STAR";
        String result = (CarRating.FOUR_STAR).toString();
        Assert.assertEquals(expected, result);
    }


    @Test
    public void FIVE_STAR_Test()
    {
        String expected = "FIVE_STAR";
        String result = (CarRating.FIVE_STAR).toString();
        Assert.assertEquals(expected, result);
    }

}
