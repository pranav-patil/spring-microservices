package com.emprovise.service.client.fallback;

import com.emprovise.service.client.FinanceServiceClient;
import com.google.gson.JsonObject;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FinanceServiceClientFallback implements FinanceServiceClient {

    @Override
    public ResponseEntity<Resource<JsonObject>> getFinanceService(String symbol, String interval) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(symbol, "Finance Service Not Available");
        Resource<JsonObject> jsonResource = new Resource<>(jsonObject, new Link(""));
        return ResponseEntity.ok(jsonResource);
    }

    @Override
    public String greeting() {
        return null;
    }
}
