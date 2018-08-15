package com.mytaxi.dataaccessobject;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mytaxi.domainobject.ManufacturerDO;

/**
 * Database Access Object for manufacturer table.
 * <p/>
 */
public interface ManufacturerRepository extends CrudRepository<ManufacturerDO, Long>
{

    Optional<ManufacturerDO> findById(Long id);

}
