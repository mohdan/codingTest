package com.mytaxi.controller.mapper;

import com.mytaxi.datatransferobject.ManufacturerDTO;
import com.mytaxi.domainobject.ManufacturerDO;

public class ManufacturerMapper
{

    public static ManufacturerDO makeManufacturerDO(ManufacturerDTO manufacturerDTO)
    {
        return new ManufacturerDO(manufacturerDTO.getName(), manufacturerDTO.getModel());
    }


    public static ManufacturerDTO makeManufacturerDTO(ManufacturerDO manufacturerDO)
    {
        ManufacturerDTO.ManufacturerDTOBuilder manufacturerDTOBuilder = ManufacturerDTO.newBuilder().setId(manufacturerDO.getId()).setModel(manufacturerDO.getModel())
            .setName(manufacturerDO.getName());

        return manufacturerDTOBuilder.createManufacturerDTO();
    }
}
