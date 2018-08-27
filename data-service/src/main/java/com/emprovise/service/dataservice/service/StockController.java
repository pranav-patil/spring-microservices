package com.emprovise.service.dataservice.service;

import com.emprovise.service.dataservice.dto.StockDetail;
import com.emprovise.service.dataservice.model.StockEvent;
import com.emprovise.service.dataservice.resource.StockResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockResource stockResource;

    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<StockEvent> stockEventStream(@PathVariable String id) {
        return stockResource.events(id);
    }

    @GetMapping("/{id}")
    private Mono<StockDetail> getStockById(@PathVariable String id) {
        return stockResource.findById(id);
    }

//    @GetMapping("/{name}")
//    private Flux<StockDetail> getStockByName(@PathVariable String name) {
//        return stockResource.findByName(name);
//    }

    @GetMapping
    private Flux<StockDetail> getAllStocks() {
        return stockResource.all();
    }

    @PostMapping("/add")
    private Mono<StockDetail> addStock(@RequestBody StockDetail stock) {
        return stockResource.add(stock);
    }
}
