package com.mytaxi.datatransferobject;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManufacturerDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "Name can not be null!")
    private String name;

    @NotNull(message = "Model can not be null!")
    private String model;


    private ManufacturerDTO()
    {}


    private ManufacturerDTO(Long id, String name, String model)
    {
        this.id = id;
        this.name = name;
        this.model = model;

    }


    public static ManufacturerDTOBuilder newBuilder()
    {
        return new ManufacturerDTOBuilder();
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public String getName()
    {
        return name;
    }


    public String getModel()
    {
        return model;
    }

    public static class ManufacturerDTOBuilder
    {
        private Long id;
        private String name;
        private String model;


        public ManufacturerDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public ManufacturerDTOBuilder setName(String name)
        {
            this.name = name;
            return this;
        }


        public ManufacturerDTOBuilder setModel(String model)
        {
            this.model = model;
            return this;
        }


        public ManufacturerDTO createManufacturerDTO()
        {
            return new ManufacturerDTO(id, name, model);
        }
    }
}
