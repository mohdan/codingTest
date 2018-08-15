package com.mytaxi.controller;

import static org.mockito.BDDMockito.given;

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
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.car.CarRating;
import com.mytaxi.domainvalue.car.CarStatus;
import com.mytaxi.service.car.DefaultCarService;

@RunWith(SpringRunner.class)
public class CarControllerTest extends TestUtils
{
    private MockMvc mockMvc;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private DefaultCarService carService;

    @InjectMocks
    private CarController carController;


    @BeforeClass
    public static void setUp()
    {
        MockitoAnnotations.initMocks(CarController.class);
    }


    @Before
    public void init()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(carController).dispatchOptions(true).build();
    }


    @Test
    public void getCarTest() throws Exception
    {
        CarDO carDo = getCar();
        given(carService.find(Mockito.anyLong())).willReturn(carDo);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/cars/{carId}", "2");
        // .header("Authorization", tokenOk).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        final String responseBody = result.getResponse()
            .getContentAsString();
        Assert.assertTrue(responseBody.contains("MH11 1111"));
    }


    @Test
    public void getCarTest_Exception() throws Exception
    {
        CarDO carDo = getCar();
        given(carService.find(Mockito.anyLong())).willReturn(carDo);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/cars/{carId}", " ");

        mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void createCarTest() throws Exception
    {
        CarDO carData = getCar();
        String jsonInString = mapper.writeValueAsString(carData);
        Mockito.doReturn(carData).when(carService).create(Mockito.any(CarDO.class));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/cars")
            .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonInString);

        MvcResult result = mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn();

        final String responseBody = result.getResponse().getContentAsString();
        Assert.assertTrue(responseBody.contains("MH11 1111"));

    }


    @Test
    public void createCarTest_BadRequest() throws Exception
    {
        CarDO carData = getCar();
        Mockito.doReturn(carData).when(carService).create(Mockito.any(CarDO.class));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/cars");
        // .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonInString);

        mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void deleteCarTest() throws Exception
    {

        Mockito.doNothing().when(carService).delete(Mockito.anyLong());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/cars/{carId}", "1");

        MvcResult result = mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }


    @Test
    public void updateCarTest() throws Exception
    {
        CarDO carData = getCar();
        String jsonInString = mapper.writeValueAsString(carData);
        Mockito.doNothing().when(carService).update(Mockito.any(Long.class), Mockito.any(), Mockito.any());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/cars/{carId}", "1")
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(jsonInString);

        MvcResult result = mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }


    @Test
    public void updateCarTest_BadRequest() throws Exception
    {
        Mockito.doNothing().when(carService).update(Mockito.any(Long.class), Mockito.any(), Mockito.any());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/cars/{carId}", "sb");

        mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status()
                .isBadRequest())
            .andReturn();
    }


    @Test
    public void findAllCarsByStatusTest() throws Exception
    {
        List<CarDO> cars = Collections.singletonList(getCar());
        Mockito.doReturn(cars).when(carService).find(Mockito.any(CarStatus.class));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/cars").param("status",
            CarStatus.FREE.toString());
        MvcResult result = mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        final String responseBody = result.getResponse().getContentAsString();
        Assert.assertTrue(responseBody.contains("MH11 1111"));
    }


    @Test
    public void findAllCarsByStatusTest_BadRequest() throws Exception
    {
        List<CarDO> cars = Collections.singletonList(getCar());
        Mockito.doReturn(cars).when(carService).find(Mockito.any(CarStatus.class));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/cars");
        mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andReturn();

    }


    @Test
    public void updateCarWithParamTest() throws Exception
    {
        CarDO carData = getCar();
        String jsonInString = mapper.writeValueAsString(carData);
        Mockito.doNothing().when(carService).update(Mockito.any(Long.class), Mockito.any(), Mockito.any());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/cars/{carId}", "1").param("status", CarStatus.FREE.toString())
            .param("rating", CarRating.FIVE_STAR.toString())
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(jsonInString);

        MvcResult result = mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

}
