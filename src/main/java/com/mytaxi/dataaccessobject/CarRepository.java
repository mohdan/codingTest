package com.mytaxi.dataaccessobject;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.car.CarStatus;

/**
 * Database Access Object for car table.
 * <p/>
 */
public interface CarRepository extends CrudRepository<CarDO, Long>
{

    Optional<CarDO> findById(Long id);


    List<CarDO> findByCarStatus(CarStatus carStatus);
}
