package com.microcredential.orderservice.controller;

import com.microcredential.orderservice.feignclients.PackageServiceProxy;
import com.microcredential.orderservice.model.ItemAddress;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class OrderController {

    @Autowired
    PackageServiceProxy packageServiceProxy;

    @PostMapping("/order/{customerId}")
    @CircuitBreaker(name="default",fallbackMethod = "fallbackMethod")
    public ResponseEntity<String> placeOrder(@PathVariable int customerId, @RequestBody  List<ItemAddress> itemAddressList){
        System.out.println("in order service");
       return packageServiceProxy.sortPackage(itemAddressList);
    }
    public ResponseEntity<String> fallbackMethod(Exception e){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
