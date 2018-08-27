package com.emprovise.service.dataservice.repository;

import com.emprovise.service.dataservice.model.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface StockRepository extends ReactiveMongoRepository<Stock, String> {

    Flux<Stock> findByStockName(final String stockName);
}
