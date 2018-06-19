package com.emprovise.service.client;

import com.emprovise.service.client.fallback.FinanceServiceClientFallback;
import com.google.gson.JsonObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "finance-service", fallback = FinanceServiceClientFallback.class)
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
