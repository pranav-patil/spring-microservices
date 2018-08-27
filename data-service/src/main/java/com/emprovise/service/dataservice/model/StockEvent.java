package com.emprovise.service.dataservice.model;

import java.util.Date;

public class StockEvent {

    private String stockId;
    private Date date;
    private Double current;

    public StockEvent() {
    }

    public StockEvent(String stockId, Date date, Double current) {
        this.stockId = stockId;
        this.date = date;
        this.current = current;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }
}
