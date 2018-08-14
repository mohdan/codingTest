package com.mytaxi.datatransferobject;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mytaxi.domainvalue.car.CarRating;
import com.mytaxi.domainvalue.car.EngineType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "License Plate can not be null!")
    private String licensePlate;

    @NotNull(message = "SeatCount can not be null!")
    private Integer seatCount;

    private CarRating rating;

    private EngineType engineType;

    private Boolean convertable;

    private Long manufacturerId;


    private CarDTO()
    {}


    private CarDTO(Long id, String licensePlate, Integer seatCount, CarRating rating, EngineType engineType,
        Boolean convertable, Long manufacturerId)
    {
        this.id = id;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.rating = rating;
        this.engineType = engineType;
        this.convertable = convertable;
        this.manufacturerId = manufacturerId;

    }


    public static CarDTOBuilder newBuilder()
    {
        return new CarDTOBuilder();
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public Integer getSeatCount()
    {
        return seatCount;
    }


    public CarRating getRating()
    {
        return rating;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public Boolean getConvertable()
    {
        return convertable;
    }


    public Long getManufacturerId()
    {
        return manufacturerId;
    }

    public static class CarDTOBuilder
    {
        private Long id;
        private String licensePlate;
        private Integer seatCount;
        private CarRating rating;
        private EngineType engineType;
        private Boolean convertable;
        private Long manufacturerId;


        public CarDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public CarDTOBuilder setLicensePlate(String licensePlate)
        {
            this.licensePlate = licensePlate;
            return this;
        }


        public CarDTOBuilder setSeatCount(Integer seatCount)
        {
            this.seatCount = seatCount;
            return this;
        }


        public CarDTOBuilder setRating(CarRating rating)
        {
            this.rating = rating;
            return this;
        }


        public CarDTOBuilder setEngineType(EngineType engineType)
        {
            this.engineType = engineType;
            return this;
        }


        public CarDTOBuilder setConvertable(Boolean convertable)
        {
            this.convertable = convertable;
            return this;
        }


        public CarDTOBuilder setManufacturerId(Long manufacturerId)
        {
            this.manufacturerId = manufacturerId;
            return this;
        }


        public CarDTO createCarDTO()
        {
            return new CarDTO(id, licensePlate, seatCount, rating, engineType, convertable, manufacturerId);
        }
    }
}
