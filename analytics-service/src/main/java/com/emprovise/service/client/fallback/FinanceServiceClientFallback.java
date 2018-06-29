package com.emprovise.service.client.fallback;

import com.emprovise.service.client.FinanceServiceClient;
import com.emprovise.service.dto.StockDetailDTO;
import org.springframework.stereotype.Component;

@Component
public class FinanceServiceClientFallback implements FinanceServiceClient {

    @Override
    public StockDetailDTO getFinanceService(String symbol, String interval) {
        return new StockDetailDTO();
    }

    @Override
    public String greeting() {
        return "Hystrix Fallback Greeting";
    }
}
