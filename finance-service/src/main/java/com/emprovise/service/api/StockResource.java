package com.emprovise.service.api;

import brave.Tracer;
import com.emprovise.service.config.SSLUtil;
import com.emprovise.service.dto.StockDetailDTO;
import com.emprovise.service.mapper.StockDetailDTOMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/rest/stock")
public class StockResource {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StockDetailDTOMapper stockDTOMapper;
    @Value("${alphavantage.apikey}")
    private String apiKey;
    @Autowired
    private Tracer tracer;

    private static final String ALPHA_VANTAGE_URL = "https://www.alphavantage.co/query";
    private static final String TIME_SERIES_INTRADAY = "TIME_SERIES_INTRADAY";
    private static Logger logger = LoggerFactory.getLogger(StockResource.class);

//    @PreAuthorize("#oauth2.hasScope('server')")
    @HystrixCommand(commandKey = "getStockDetails", fallbackMethod = "getStockDetailsFallback")
    @GetMapping("/symbol/{symbol}/interval/{interval}")
    public StockDetailDTO getStockDetails(@PathVariable("symbol") String symbol,
                                          @PathVariable("interval") String interval) throws Exception {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(ALPHA_VANTAGE_URL)
                .queryParam("function", TIME_SERIES_INTRADAY)
                .queryParam("symbol", symbol)
                .queryParam("interval", interval)
                .queryParam("apikey", apiKey);

        SSLUtil.turnOffSslChecking();
        String response = restTemplate.getForObject(builder.toUriString(), String.class);
        return stockDTOMapper.map(interval, response);
    }

    public StockDetailDTO getStockDetailsFallback(String symbol, String interval, Throwable cause) throws Exception {
        logger.error("Error in fetching stock details", cause);
        return new StockDetailDTO();
    }

    @GetMapping("/greeting")
    public String greeting() {
        tracer.currentSpan().tag("service.greeting", "hello_world");
        return "Hello World";
    }
}
