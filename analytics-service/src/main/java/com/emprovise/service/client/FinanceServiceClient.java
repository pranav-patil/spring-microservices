package com.emprovise.service.client;

import com.emprovise.service.client.fallback.FinanceServiceFallbackFactory;
import com.emprovise.service.dto.StockDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "finance-service", fallbackFactory = FinanceServiceFallbackFactory.class)
public interface FinanceServiceClient {

    @RequestMapping(value = "/rest/stock/symbol/{symbol}/interval/{interval}",
            method = RequestMethod.GET,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            })
    StockDetailDTO getFinanceService(@PathVariable("symbol") String symbol,
                                     @PathVariable("interval") String interval);

    @RequestMapping("/rest/stock/greeting")
    String greeting();
}
