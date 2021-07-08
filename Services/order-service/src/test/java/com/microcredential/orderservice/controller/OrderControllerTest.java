package com.microcredential.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microcredential.orderservice.feignclients.PackageServiceProxy;
import com.microcredential.orderservice.model.Address;
import com.microcredential.orderservice.model.ItemAddress;
import org.assertj.core.api.Assertions;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderControllerTest {


    MockMvc mockMvc;

    @MockBean
    PackageServiceProxy packageServiceProxy;

    @Autowired
    OrderController orderController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void contextLoads() {
        Assertions.assertThat(this.orderController).isNotNull();
    }

    @org.junit.Test
    public void placeOrderTest() throws Exception {
        ItemAddress itemAddress=new ItemAddress();
        Address address=new Address();
        address.setLine1("temp");
        address.setLine2("temp2");
        address.setCity("chennai");
        address.setState("TN");
        itemAddress.setId(1);
        itemAddress.setName("phone");
        itemAddress.setPrice(12000);
        itemAddress.setQuantity(2);
        itemAddress.setTotal(24000);
        itemAddress.setAddress(address);
        ObjectMapper mapper=new ObjectMapper();
        List<ItemAddress> itemAddressList=new ArrayList<>();
        itemAddressList.add(itemAddress);
        String json=mapper.writeValueAsString(itemAddressList);

        when(packageServiceProxy.sortPackage(any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/order/1")
                .contentType(MediaType.APPLICATION_JSON).content(json);
        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
