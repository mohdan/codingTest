package com.mytaxi.domainobject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Role
{
    @Id
    @GeneratedValue
    private Long id;
    private String name;


    Role()
    {}


    public Role(String name)
    {
        super();
        this.name = name;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }

}
