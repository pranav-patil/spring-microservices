package com.emprovise.service.client.fallback;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinanceServiceFallbackFactory implements FallbackFactory<FinanceServiceClientFallback> {

    @Autowired
    private FinanceServiceClientFallback financeServiceClientFallback;
    private static Logger logger = LoggerFactory.getLogger(FinanceServiceFallbackFactory.class);

    @Override
    public FinanceServiceClientFallback create(Throwable cause) {

        if(!(cause instanceof RuntimeException && cause.getMessage() == null)) {
            logger.error("FinanceServiceClient failed, switching to FinanceServiceClientFallback", cause);
        }
        return financeServiceClientFallback;
    }

    @Override
    public String toString() {
        return financeServiceClientFallback.toString();
    }
}
