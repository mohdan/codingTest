package com.mytaxi.dataaccessobject;

import org.springframework.data.repository.CrudRepository;

import com.mytaxi.domainobject.User;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findByUsername(String username);
}
