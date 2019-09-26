Finance Service
=============

Finance service provides services to access various financial data, although currently supporting only stock data.
It mainly showcases [OpenFeign Clients](https://github.com/OpenFeign/feign) and [Netflix Hystrix](https://github.com/Netflix/Hystrix) circuit breakers.
Finance service uses [Alphavantage API](https://www.alphavantage.co/documentation/) to fetch real time hourly stock details by company codes. The **alphavantage.apikey** is an encrypted Alphavantage API key which can be created by [free registration](https://www.alphavantage.co/support/#api-key).
   
### Running the Finance Service

The **CONFIG_SERVICE_PASSWORD** is a required parameter to run finance-service as it enables to access finance-service.yml configuration file from the [config-service](../config-service/README.md).
The **FINANCE_SERVICE_PASSWORD** is the client secret required to access oauth2 token for client id finance-service. Use the same **FINANCE_SERVICE_PASSWORD** configured in [authorization-service](../authorization-service/README.md).
Optionally **spring.profiles.active** can be passed with value **production** which enables logback to send all logs to [Elastic Stack](../elastic-stack/README.md) instead of logging in the console by default.

    $ java -jar finance-service/build/libs/finance-service-0.0.1-SNAPSHOT.jar
           -DCONFIG_SERVICE_PASSWORD=xxxx
		   -DFINANCE_SERVICE_PASSWORD=yyyy
		   -Dspring.profiles.active=production
