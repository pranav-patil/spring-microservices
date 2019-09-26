Analytics Service
=============

Analytics service provides various services to access analytical data from diverse resources. Currently analytics services for only financial or stock data are available.
It mainly showcases [OpenFeign Clients](https://github.com/OpenFeign/feign), [Netflix Hystrix](https://github.com/Netflix/Hystrix) circuit breakers, [Spring Cloud Sleuth](https://cloud.spring.io/spring-cloud-sleuth/) with Zipkin and [Spring Webflux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html) clients.
   
### Running the Analytics Service

The **CONFIG_SERVICE_PASSWORD** is a required parameter to run analytics-service as it enables to access analytics-service.yml configuration file from the [config-service](../config-service/README.md).
The **ANALYTICS_SERVICE_PASSWORD** is the client secret required to access oauth2 token for client id analytics-service. Use the same **ANALYTICS_SERVICE_PASSWORD** configured in [authorization-service](../authorization-service/README.md).
Optionally **spring.profiles.active** can be passed with value **production** which enables logback to send all logs to [Elastic Stack](../elastic-stack/README.md) instead of logging in the console by default.

    $ java -jar analytics-service/build/libs/analytics-service-0.0.1-SNAPSHOT.jar
           -DCONFIG_SERVICE_PASSWORD=xxxx
		   -DANALYTICS_SERVICE_PASSWORD=yyyy
		   -Dspring.profiles.active=production
