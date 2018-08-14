package com.mytaxi.domainobject;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "manufacturer", uniqueConstraints = @UniqueConstraint(name = "uc_name_model", columnNames = {"name",
    "model"}))
public class ManufacturerDO
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_created", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    @NotNull(message = "Name can not be null!")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Model can not be null!")
    private String model;

    @Column(nullable = false)
    private Boolean deleted = false;


    private ManufacturerDO()
    {}


    public ManufacturerDO(Long manufacturerId)
    {
        this.id = manufacturerId;
    }


    public ManufacturerDO(String name, String model)
    {
        this.name = name;
        this.model = model;
        this.deleted = false;
    }


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    public ZonedDateTime getDateCreated()
    {
        return dateCreated;
    }


    public void setDateCreated(ZonedDateTime dateCreated)
    {
        this.dateCreated = dateCreated;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public String getModel()
    {
        return model;
    }


    public void setModel(String model)
    {
        this.model = model;
    }


    public Boolean getDeleted()
    {
        return deleted;
    }


    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

}
