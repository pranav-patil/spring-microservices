package com.emprovise.service.client;

import com.google.gson.JsonObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("finance-service")
public interface FinanceServiceClient {

    @RequestMapping(value = "/rest/stock/symbol/{symbol}/interval/{interval}",
            method = RequestMethod.GET,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            })
    ResponseEntity<Resource<JsonObject>> getFinanceService(@PathVariable("symbol") String symbol,
                                                           @PathVariable("interval") String interval);

    @RequestMapping("/rest/stock/greeting")
    String greeting();
}
