package com.mytaxi.domainobject;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.mytaxi.domainvalue.car.CarRating;
import com.mytaxi.domainvalue.car.CarStatus;
import com.mytaxi.domainvalue.car.EngineType;

@Entity
@Table(name = "car", uniqueConstraints = @UniqueConstraint(name = "uc_license_plate", columnNames = {
    "license_plate"}))
public class CarDO
{

    public static final class ColumnNames
    {
        private ColumnNames()
        {}

        public static final String DATE_CREATED = "date_created";
        public static final String LICENSE_PLATE = "license_plate";
        public static final String SEAT_COUNT = "seat_count";
        public static final String ENGINE_TYPE = "engine_type";
        public static final String CAR_STATUS = "car_status";
        public static final String MANUFACTURERER_ID = "manufacturer_id";

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = ColumnNames.DATE_CREATED, nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(name = ColumnNames.LICENSE_PLATE, nullable = false)
    @NotNull(message = "LicensePlate can not be null!")
    private String licensePlate;

    @Column(name = ColumnNames.SEAT_COUNT, nullable = false)
    @NotNull(message = "SeatCount can not be null!")
    private Integer seatCount;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Column
    private Boolean convertible;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarRating rating;

    @Enumerated(EnumType.STRING)
    @Column(name = ColumnNames.ENGINE_TYPE, nullable = false)
    private EngineType engineType;

    @Enumerated(EnumType.STRING)
    @Column(name = ColumnNames.CAR_STATUS, nullable = false)
    private CarStatus carStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ColumnNames.MANUFACTURERER_ID)
    private ManufacturerDO manufacturer;


    @SuppressWarnings("unused")
    private CarDO()
    {}


    public CarDO(String licensePlate, Integer seatCount, EngineType engineType, Boolean convertible, ManufacturerDO manufacturer)
    {
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.engineType = engineType;
        this.convertible = convertible;
        this.manufacturer = manufacturer;
        this.deleted = false;
        this.rating = CarRating.NO_RATING;
        this.carStatus = CarStatus.OFFDUTY;
    }


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public Integer getSeatCount()
    {
        return seatCount;
    }


    public Boolean getDeleted()
    {
        return deleted;
    }


    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }


    public Boolean getConvertible()
    {
        return convertible;
    }


    public void setConvertible(Boolean convertible)
    {
        this.convertible = convertible;
    }


    public CarRating getRating()
    {
        return rating;
    }


    public void setRating(CarRating rating)
    {
        this.rating = rating;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public CarStatus getCarStatus()
    {
        return carStatus;
    }


    public void setCarStatus(CarStatus carStatus)
    {
        this.carStatus = carStatus;
    }


    public ManufacturerDO getManufacturer()
    {
        return manufacturer;
    }


    public void setManufacturer(ManufacturerDO manufacturer)
    {
        this.manufacturer = manufacturer;
    }

}
