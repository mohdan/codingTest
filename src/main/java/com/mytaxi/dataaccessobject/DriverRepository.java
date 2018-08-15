package com.mytaxi.dataaccessobject;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<DriverDO, Long>, JpaSpecificationExecutor<DriverDO>
{

    Optional<DriverDO> findById(Long id);


    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);


    DriverDO findByUsername(final String username);
}
