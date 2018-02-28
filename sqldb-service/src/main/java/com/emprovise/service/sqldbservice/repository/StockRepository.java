package com.emprovise.service.sqldbservice.repository;

import com.emprovise.service.sqldbservice.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Integer> {

    List<Stock> findByStockName(String stockName);
}
