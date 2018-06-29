package com.emprovise.service.api;

import com.emprovise.service.client.FinanceServiceClient;
import com.emprovise.service.dto.StockDetailDTO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@RestController
public class FinanceController {

    @Autowired
    private FinanceServiceClient financeServiceClient;

    @RequestMapping("/finance")
    public String finance() {
        StockDetailDTO stockDetailDTO = financeServiceClient.getFinanceService("MSFT", "60min");
        Gson gson = new Gson();
        return gson.toJson(stockDetailDTO);
    }

    @RequestMapping("/greeting/view")
    public String greeting() {
        return financeServiceClient.greeting();
    }
}
