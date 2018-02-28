package com.emprovise.service.sqldbservice.resource;

import com.emprovise.service.sqldbservice.dto.StockDetails;
import com.emprovise.service.sqldbservice.model.Stock;
import com.emprovise.service.sqldbservice.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/db")
public class PostgreDBServiceResource {

    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/stock/{stockName}")
    public List<String> getStockDetails(@PathVariable final String stockName) {
        return stockRepository.findByStockName(stockName)
                        .stream()
                        .map(Stock::getStockName)
                        .collect(Collectors.toList());
    }

    @PostMapping("/stock/add")
    public void addStock(@RequestBody final StockDetails stockDetails) {
        Stock stock = new Stock();
        stock.setStockName(stockDetails.getStockName());
        stock.setLow(stockDetails.getLow());
        stock.setHigh(stockDetails.getHigh());
        stock.setOpen(stockDetails.getOpen());
        stock.setClose(stockDetails.getClose());
        stock.setDate(stockDetails.getDate());
        stockRepository.save(stock);
    }
}
