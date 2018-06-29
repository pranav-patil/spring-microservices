package com.emprovise.service.mapper;

import com.emprovise.service.dto.StockDetailDTO;
import com.emprovise.service.dto.StockPrice;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@Component
public class StockDetailDTOMapper {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String OPEN   = "open";
    private static final String HIGH   = "high";
    private static final String LOW    = "low";
    private static final String CLOSE  = "close";
    private static final String VOLUME = "volume";

    public StockDetailDTO map(String interval, String stockResponse) throws ParseException {

        StockDetailDTO stockDetailDTO = new StockDetailDTO();

        if (stockResponse != null) {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(stockResponse).getAsJsonObject();

            JsonElement jsonElement = jsonObject.get(String.format("Time Series (%s)", interval));

            if(jsonElement.isJsonObject()) {

                for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                    StockPrice stockPrice = new StockPrice();

                    if(StringUtils.isNotBlank(entry.getKey())) {
                        stockPrice.setTimeStamp(DATE_FORMATTER.parse(entry.getKey()));
                    }

                    JsonObject entryObject = entry.getValue().getAsJsonObject();

                    for (Map.Entry<String, JsonElement> priceEntry : entryObject.entrySet()) {

                        String key = priceEntry.getKey();

                        if(StringUtils.isNotBlank(key)) {
                            if(key.endsWith(OPEN)) {
                                stockPrice.setOpen(parseDouble(priceEntry.getValue().getAsString()));
                            }
                            else if(key.endsWith(HIGH)) {
                                stockPrice.setHigh(parseDouble(priceEntry.getValue().getAsString()));
                            }
                            else if(key.endsWith(LOW)) {
                                stockPrice.setLow(parseDouble(priceEntry.getValue().getAsString()));
                            }
                            else if(key.endsWith(CLOSE)) {
                                stockPrice.setClose(parseDouble(priceEntry.getValue().getAsString()));
                            }
                            else if(key.endsWith(VOLUME)) {
                                stockPrice.setVolume(parseLong(priceEntry.getValue().getAsString()));
                            }
                        }
                    }

                    stockDetailDTO.addStockPrice(stockPrice);
                }
            }
        }

        return stockDetailDTO;
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        }catch (Exception ex) {
            return 0;
        }
    }

    private long parseLong(String value) {
        try {
            return Long.parseLong(value);
        }catch (Exception ex) {
            return 0;
        }
    }
}
