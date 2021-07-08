package com.microcredential.orderservice.feignclients;

import com.microcredential.orderservice.model.ItemAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "package-service")
public interface PackageServiceProxy {
    @PostMapping("/package")
    public ResponseEntity<String> sortPackage(@RequestBody List<ItemAddress> itemAddressList);
}
