package com.emprovise.service.api;

import com.emprovise.service.client.FinanceServiceClient;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@RestController
public class FinanceController {

    @Autowired
    private FinanceServiceClient financeServiceClient;

    @RequestMapping("/finance")
    public ResponseEntity<Resource<JsonObject>> finance() {
        return financeServiceClient.getFinanceService("MSFT", "weekly");
    }

    @RequestMapping("/greeting/view")
    public String greeting() {
        return financeServiceClient.greeting();
    }
}
