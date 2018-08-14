package com.mytaxi.filter;

import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.domainvalue.car.CarRating;

public class SearchDriverFilter
{

    private String username;
    private OnlineStatus onlineStatus;
    private String licensePlate;
    private CarRating rating;
    private String manufacturerName;


    /**
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }


    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username)
    {
        this.username = username;
    }


    /**
     * @return the onlineStatus
     */
    public OnlineStatus getOnlineStatus()
    {
        return onlineStatus;
    }


    /**
     * @param onlineStatus
     *            the onlineStatus to set
     */
    public void setOnlineStatus(OnlineStatus onlineStatus)
    {
        this.onlineStatus = onlineStatus;
    }


    /**
     * @return the licensePlate
     */
    public String getLicensePlate()
    {
        return licensePlate;
    }


    /**
     * @param licensePlate
     *            the licensePlate to set
     */
    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }


    /**
     * @return the rating
     */
    public CarRating getRating()
    {
        return rating;
    }


    /**
     * @param rating
     *            the rating to set
     */
    public void setRating(CarRating rating)
    {
        this.rating = rating;
    }


    /**
     * @return the manufacturerName
     */
    public String getManufacturerName()
    {
        return manufacturerName;
    }


    /**
     * @param manufacturerName
     *            the manufacturerName to set
     */
    public void setManufacturerName(String manufacturerName)
    {
        this.manufacturerName = manufacturerName;
    }

}
