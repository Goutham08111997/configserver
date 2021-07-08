package com.microcredential.packageservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microcredential.packageservice.controller.PackageController;
import com.microcredential.packageservice.messaging.MessageProducer;
import com.microcredential.packageservice.model.Address;
import com.microcredential.packageservice.model.ItemAddress;
import com.microcredential.packageservice.model.Package;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class PackageControllerTest {

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Autowired
    PackageController packageController;

    @MockBean
    MessageProducer messageProducer;

    @Before
    public void setUp() throws JsonProcessingException {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

//        Mockito.doNothing().when(messageProducer.sendMessageEastZone(any(Class<Package.class>)));
    }

    @Test
    public void contextLoads() {
        Assertions.assertThat(this.packageController).isNotNull();
    }

    @Test
    public void sortPackageTest() throws Exception {
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


        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/package")
                .contentType(MediaType.APPLICATION_JSON).content(json);
        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
