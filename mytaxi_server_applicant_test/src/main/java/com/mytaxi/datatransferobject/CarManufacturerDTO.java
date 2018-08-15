package com.mytaxi.datatransferobject;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mytaxi.domainvalue.car.CarRating;
import com.mytaxi.domainvalue.car.EngineType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarManufacturerDTO
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

    private ManufacturerDTO manufacturer;


    private CarManufacturerDTO()
    {}


    private CarManufacturerDTO(Long id, String licensePlate, Integer seatCount, CarRating rating, EngineType engineType,
        Boolean convertable, ManufacturerDTO manufacturer)
    {
        this.id = id;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.rating = rating;
        this.engineType = engineType;
        this.convertable = convertable;
        this.manufacturer = manufacturer;

    }


    public static CarManufacturerDTOBuilder newBuilder()
    {
        return new CarManufacturerDTOBuilder();
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


    public ManufacturerDTO getManufacturer()
    {
        return manufacturer;
    }

    public static class CarManufacturerDTOBuilder
    {
        private Long id;
        private String licensePlate;
        private Integer seatCount;
        private CarRating rating;
        private EngineType engineType;
        private Boolean convertable;
        private ManufacturerDTO manufacturer;


        public CarManufacturerDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public CarManufacturerDTOBuilder setLicensePlate(String licensePlate)
        {
            this.licensePlate = licensePlate;
            return this;
        }


        public CarManufacturerDTOBuilder setSeatCount(Integer seatCount)
        {
            this.seatCount = seatCount;
            return this;
        }


        public CarManufacturerDTOBuilder setRating(CarRating rating)
        {
            this.rating = rating;
            return this;
        }


        public CarManufacturerDTOBuilder setEngineType(EngineType engineType)
        {
            this.engineType = engineType;
            return this;
        }


        public CarManufacturerDTOBuilder setConvertable(Boolean convertable)
        {
            this.convertable = convertable;
            return this;
        }


        public CarManufacturerDTOBuilder setManufacturer(ManufacturerDTO manufacturer)
        {
            this.manufacturer = manufacturer;
            return this;
        }


        public CarManufacturerDTO createCarManufacturerDTO()
        {
            return new CarManufacturerDTO(id, licensePlate, seatCount, rating, engineType, convertable, manufacturer);
        }
    }
}
