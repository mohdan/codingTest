package com.mytaxi.datatransferobject;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverCarDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "Username can not be null!")
    private String username;

    @NotNull(message = "Password can not be null!")
    private String password;

    private GeoCoordinate coordinate;
    
    private OnlineStatus onlineStatus;

    private CarManufacturerDTO car;


    private DriverCarDTO()
    {}


    private DriverCarDTO(Long id, String username, String password, GeoCoordinate coordinate, OnlineStatus onlineStatus, CarManufacturerDTO car)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.coordinate = coordinate;
        this.onlineStatus = onlineStatus;
        this.car = car;
    }


    public static DriverDTOBuilder newBuilder()
    {
        return new DriverDTOBuilder();
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public String getUsername()
    {
        return username;
    }


    public String getPassword()
    {
        return password;
    }


    public GeoCoordinate getCoordinate()
    {
        return coordinate;
    }
    
    public OnlineStatus getOnlineStatus()
    {
        return onlineStatus;
    }


    public CarManufacturerDTO getCar()
    {
        return car;
    }

    public static class DriverDTOBuilder
    {
        private Long id;
        private String username;
        private String password;
        private GeoCoordinate coordinate;
        private OnlineStatus onlineStatus;
        private CarManufacturerDTO car;


        public DriverDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public DriverDTOBuilder setUsername(String username)
        {
            this.username = username;
            return this;
        }


        public DriverDTOBuilder setPassword(String password)
        {
            this.password = password;
            return this;
        }


        public DriverDTOBuilder setCoordinate(GeoCoordinate coordinate)
        {
            this.coordinate = coordinate;
            return this;
        }
        
        public DriverDTOBuilder setOnlineStatus(OnlineStatus onlineStatus)
        {
            this.onlineStatus = onlineStatus;
            return this;
        }


        public DriverDTOBuilder setCar(CarManufacturerDTO car)
        {
            this.car = car;
            return this;
        }


        public DriverCarDTO createDriverDTO()
        {
            return new DriverCarDTO(id, username, password, coordinate,onlineStatus, car);
        }

    }
}
