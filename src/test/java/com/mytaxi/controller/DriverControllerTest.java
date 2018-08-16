package com.mytaxi.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaxi.TestUtils;
import com.mytaxi.controller.mapper.DriverCarMapper;
import com.mytaxi.controller.mapper.DriverMapper;
import com.mytaxi.datatransferobject.DriverCarDTO;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.filter.SearchDriverFilter;
import com.mytaxi.service.driver.DefaultDriverService;

@RunWith(SpringRunner.class)
public class DriverControllerTest extends TestUtils
{

    private MockMvc mockMvc;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private DriverMapper driverMapper;

    @Mock
    private DriverCarMapper driverCarMapper;

    @Mock
    private DefaultDriverService driverService;

    @InjectMocks
    private DriverController driverController;


    @BeforeClass
    public static void setUp()
    {
        MockitoAnnotations.initMocks(DriverController.class);
    }


    @Before
    public void init()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(driverController).dispatchOptions(true).build();
        driverController.setDriverMapper(driverMapper);
        driverController.setDriverCarMapper(driverCarMapper);
    }


    @Test
    public void getDriverTest() throws Exception
    {
        DriverCarDTO driverdata = getDriverCarDTO();
        given(driverCarMapper.makeDriverCarDTO(Mockito.any())).willReturn(driverdata);
        given(driverService.find(Mockito.anyLong())).willReturn(new DriverDO("test", "test"));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/drivers/{driverId}", "2");
        // .header("Authorization", tokenOk).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("username").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("password").value("test"));
    }


    @Test
    public void getDriverTest_Exception() throws Exception
    {
        given(driverService.find(Mockito.anyLong())).willReturn(new DriverDO("driver 2", "driver 2"));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/drivers/{driverId}", " ");

        mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void createDriverTest() throws Exception
    {
        DriverDO driverData = getDriver();
        String jsonInString = mapper.writeValueAsString(driverData);
        Mockito.doReturn(getDriver()).when(driverMapper).makeDriverDO(Mockito.any(DriverDTO.class));
        Mockito.doReturn(getDriverDTO()).when(driverMapper).makeDriverDTO(Mockito.any(DriverDO.class));
        Mockito.doReturn(driverData).when(driverService).create(Mockito.any(DriverDO.class));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/drivers")
            .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonInString);

        MvcResult result = mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn();

        final String responseBody = result.getResponse().getContentAsString();
        Assert.assertTrue(responseBody.contains("test"));

    }


    @Test
    public void createDriverTest_BadRequest() throws Exception
    {
        given(driverService.create(Mockito.any())).willReturn(new DriverDO("driver 2", "driver 2"));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/drivers");

        mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void deleteDriverTest() throws Exception
    {
        Mockito.doNothing().when(driverService).delete(Mockito.anyLong());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/drivers/{driverId}", "2");

        MvcResult result = mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }


    @Test
    public void updateLocationTest() throws Exception
    {
        Mockito.doNothing().when(driverService).updateLocation(Mockito.any(Long.class), Mockito.any(Double.class),
            Mockito.any(Double.class));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/drivers/{driverId}", "2")
            .param("longitude", "99")
            .param("latitude", "99");

        MvcResult result = mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }


    @Test
    public void findDriverByOnlineStatusTest() throws Exception
    {
        List<DriverDO> driverData = Collections.singletonList(getDriver());
        List<DriverCarDTO> driverDataDTOs = Collections.singletonList(getDriverCarDTO());

        Mockito.doReturn(driverData).when(driverService).find(Mockito.any(OnlineStatus.class));
        Mockito.doReturn(driverDataDTOs).when(driverCarMapper).makeDriverCarDTOList(Mockito.any());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/drivers").param("onlineStatus",
            OnlineStatus.ONLINE.toString());

        MvcResult result = mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        final String responseBody = result.getResponse().getContentAsString();
        Assert.assertTrue(responseBody.contains("test"));
    }


    @Test
    public void deSelectCarByDriverIdTest() throws Exception
    {
        // Mockito.doNothing().when(driverService).deselectCar(Mockito.any(Long.class));
        given(driverService.deselectCar(Mockito.any())).willReturn(new DriverDO("driver 2", "driver 2"));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/drivers/{driverId}/car", "2");
        MvcResult result = mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }


    @Test
    public void selectCarByDriverIdAndCarIdTest() throws Exception
    {
        DriverDO driverData = getDriver();
        Mockito.doReturn(driverData).when(driverService).selectCar(Mockito.any(Long.class), Mockito.any(Long.class));
        Mockito.doReturn(getDriverCarDTO()).when(driverCarMapper).makeDriverCarDTO(Mockito.any(DriverDO.class));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/drivers/{driverId}/car/{carId}", "2", "2");
        MvcResult result = mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        final String responseBody = result.getResponse().getContentAsString();
        Assert.assertTrue(responseBody.contains("test"));

    }


    @Test
    public void searchDriverTest() throws Exception
    {
        List<DriverDO> driverData = Collections.singletonList(getDriver());
        List<DriverCarDTO> driverDataDTOs = Collections.singletonList(getDriverCarDTO());

        when(driverService.searchDriver(Mockito.any(SearchDriverFilter.class))).then(((invocation) -> {
            return driverData;
        }));
        Mockito.doReturn(driverDataDTOs).when(driverCarMapper).makeDriverCarDTOList(Mockito.any());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/drivers/search");

        MvcResult result = mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        final String responseBody = result.getResponse().getContentAsString();
        Assert.assertTrue(responseBody.contains("test"));
    }

}
