package com.mytaxi.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.controller.mapper.DriverCarMapper;
import com.mytaxi.controller.mapper.DriverMapper;
import com.mytaxi.datatransferobject.DriverCarDTO;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.domainvalue.car.CarRating;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.filter.SearchDriverFilter;
import com.mytaxi.service.driver.DriverService;
import com.mytaxi.util.Response;
import com.mytaxi.util.UtilityConstants;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController("driverController")
@RequestMapping("v1/drivers")
public class DriverController
{

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverController.class);

    private final DriverService driverService;

    @Autowired
    private DriverMapper driverMapper;

    @Autowired
    private DriverCarMapper driverCarMapper;


    @Autowired
    public DriverController(final DriverService driverService)
    {
        this.driverService = driverService;
    }


    @GetMapping("/{driverId}")
    public DriverCarDTO getDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException
    {
        return driverCarMapper.makeDriverCarDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = driverMapper.makeDriverDO(driverDTO);
        return driverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    public void updateLocation(@Valid @PathVariable long driverId, @RequestParam double longitude,
        @RequestParam double latitude) throws ConstraintsViolationException, EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);

    }


    @GetMapping
    public List<DriverCarDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        return driverCarMapper.makeDriverCarDTOList(driverService.find(onlineStatus));
    }


    @PostMapping("/{driverId}/{carId}")
    public ResponseEntity<Response> selectCarByDriver(@Valid @PathVariable Long driverId, @Valid @PathVariable Long carId,
        HttpServletRequest request) throws EntityNotFoundException, ConstraintsViolationException, CarAlreadyInUseException
    {
        final String uuid = (String) request.getAttribute(UtilityConstants.UUID_CONSTANT);
        final Response response = new Response();
        response.setUuid(uuid);
        DriverCarDTO driverCarDTO = driverCarMapper.makeDriverCarDTO(driverService.selectCar(driverId, carId));
        response.setData(driverCarDTO);
        return buildHttpResp(uuid, response, HttpStatus.OK);
    }


    @DeleteMapping("/{driverId}/car")
    public ResponseEntity<Response> deselectCarByDriver(@Valid @PathVariable Long driverId, HttpServletRequest request)
        throws EntityNotFoundException, ConstraintsViolationException
    {
        final String uuid = (String) request.getAttribute(UtilityConstants.UUID_CONSTANT);
        final Response response = new Response();
        response.setUuid(uuid);
        DriverDTO driverDTO = driverMapper.makeDriverDTO(driverService.deselectCar(driverId));
        response.setData(driverDTO);
        return buildHttpResp(uuid, response, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<Response> searchDriver(@RequestParam Optional<String> username,
        @RequestParam Optional<OnlineStatus> onlineStatus, @RequestParam Optional<String> licensePlate,
        @RequestParam Optional<CarRating> rating, @RequestParam Optional<String> manufacturerName,
        HttpServletRequest request) throws ConstraintsViolationException, EntityNotFoundException
    {
        //This we use for the all the request to track each request.
        final String uuid = (String) request.getAttribute(UtilityConstants.UUID_CONSTANT);
        final Response response = new Response();
        response.setUuid(uuid);
        LOGGER.info("UUID: [{}] - Received GET request on /searchDriver. userName: {}. onlineStatus: {}", uuid,
            username, onlineStatus);
        SearchDriverFilter filter = driverService.checkSearchDriverFilter(uuid,
            username.isPresent() ? username.get() : null, onlineStatus.isPresent() ? onlineStatus.get() : null,
            licensePlate.isPresent() ? licensePlate.get() : null, rating.isPresent() ? rating.get() : null,
            manufacturerName.isPresent() ? manufacturerName.get() : null);
        response.setData(driverService.searchDriver(filter));
        return buildHttpResp(uuid, response, HttpStatus.OK);

    }


    private ResponseEntity buildHttpResp(final String uuid, final Response response, final HttpStatus httpStatus)
    {
        LOGGER.info("UUID: [{}] - Replying with HTTP Status {} - {}", uuid, httpStatus.value(),
            httpStatus.getReasonPhrase());
        return new ResponseEntity(response, httpStatus);
    }


    public void setDriverMapper(DriverMapper driverMapper)
    {
        this.driverMapper = driverMapper;
    }


    public void setDriverCarMapper(DriverCarMapper driverCarMapper)
    {
        this.driverCarMapper = driverCarMapper;
    }

}
