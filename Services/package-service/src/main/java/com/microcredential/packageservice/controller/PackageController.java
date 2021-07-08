package com.microcredential.packageservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microcredential.packageservice.messaging.MessageProducer;
import com.microcredential.packageservice.model.Address;
import com.microcredential.packageservice.model.Item;
import com.microcredential.packageservice.model.ItemAddress;
import com.microcredential.packageservice.model.Package;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class PackageController {

    public static int id=1;

    @Autowired
    MessageProducer   messageProducer;

    @Value("${east-zone}")
    private List<String> eastZoneList;

    @Value("${west-zone}")
    private List<String> westZoneList;

    @Value("${north-zone}")
    private List<String> northZoneList;

    @Value("${south-zone}")
    private List<String> southZoneList;


    public  ResponseEntity<String> fallbackMethod(Exception e){
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/package")
    @CircuitBreaker(name = "default",fallbackMethod = "fallbackMethod")
    public ResponseEntity<String> sortPackage(@RequestBody List<ItemAddress> itemAddressList) throws JsonProcessingException {

        Map<Address,List<ItemAddress>> map=itemAddressList.stream().collect(Collectors.groupingBy(itemAddress->itemAddress.getAddress()));
        for(Map.Entry entry:map.entrySet()){
            List<ItemAddress> itemAddressList1=(List<ItemAddress>)entry.getValue();
            Package newPackage=new Package();
            newPackage.setPackageId(id++);
            List<Item> listOfItems=new ArrayList<>();
            newPackage.setAddress(itemAddressList1.get(0).getAddress());
            for(ItemAddress itemAddress:itemAddressList1){
                Item item=new Item();
                item.setItemName(itemAddress.getName());
                item.setQuantity(itemAddress.getQuantity());
                item.setPrice(itemAddress.getPrice());
                item.setTotal(itemAddress.getTotal());
                listOfItems.add(item);
            }
            newPackage.setListOfItems(listOfItems);
            System.out.println(newPackage);
            String state=newPackage.getAddress().getState();
            if(eastZoneList.contains(state)){
                messageProducer.sendMessageEastZone(newPackage);
            }
            else if(westZoneList.contains(state)){
                messageProducer.sendMessageWestZone(newPackage);
            }
            else if(northZoneList.contains(state)){
                messageProducer.sendMessageNorthZone(newPackage);
            }
            else if(southZoneList.contains(state)){
                messageProducer.sendMessageSouthZone(newPackage);
            }
            else
                return null;
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
