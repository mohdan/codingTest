package com.mytaxi.domainobject;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.util.EncryptionUtils;

@Entity
@Table(
    name = "driver",
    uniqueConstraints = @UniqueConstraint(name = "uc_username", columnNames = {"username"}))
public class DriverDO implements UserDetails
{
    public static final class ColumnNames
    {
        private ColumnNames()
        {}

        public static final String DATE_CREATED = "date_created";
        public static final String DATE_COORDINATE_UPDATED = "date_coordinate_updated";
        public static final String ONLINE_STATUS = "online_status";
        public static final String CAR_ID = "car_id";

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_created", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    @NotNull(message = "Username can not be null!")
    private String username;

    @Column(nullable = false)
    @NotNull(message = "Password can not be null!")
    private String password;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Embedded
    private GeoCoordinate coordinate;

    @Column(name = "date_coordinate_updated")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCoordinateUpdated = ZonedDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OnlineStatus onlineStatus;

    @OneToOne()
    @JoinColumn(name = "car_id", nullable = true)
    private CarDO car;


    private DriverDO()
    {}


    public DriverDO(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.deleted = false;
        this.coordinate = null;
        this.dateCoordinateUpdated = null;
        this.onlineStatus = OnlineStatus.OFFLINE;
    }


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    @Override
    public String getUsername()
    {
        return username;
    }


    @Override
    public String getPassword()
    {
        return password;
    }


    public Boolean getDeleted()
    {
        return deleted;
    }


    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }


    public OnlineStatus getOnlineStatus()
    {
        return onlineStatus;
    }


    public void setOnlineStatus(OnlineStatus onlineStatus)
    {
        this.onlineStatus = onlineStatus;
    }


    public GeoCoordinate getCoordinate()
    {
        return coordinate;
    }


    public void setCoordinate(GeoCoordinate coordinate)
    {
        this.coordinate = coordinate;
        this.dateCoordinateUpdated = ZonedDateTime.now();
    }


    public CarDO getCar()
    {
        return car;
    }


    public void setCar(CarDO car)
    {
        this.car = car;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        // TODO Auto-generated method stub
        return new ArrayList<>();
    }


    @Override
    public boolean isAccountNonExpired()
    {
        // TODO Auto-generated method stub
        return true;
    }


    @Override
    public boolean isAccountNonLocked()
    {
        // TODO Auto-generated method stub
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired()
    {
        // TODO Auto-generated method stub
        return true;
    }


    @Override
    public boolean isEnabled()
    {
        // TODO Auto-generated method stub
        return true;
    }

}
