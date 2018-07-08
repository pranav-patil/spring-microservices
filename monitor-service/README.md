Monitor Service
=============

### Running the application

Pass the CONFIG_SERVICE_PASSWORD from the [config-service](/../config-service/README.md) to access monitor-service.yml configuration file.

    $ java -jar monitor-service/build/libs/monitor-service-0.0.1-SNAPSHOT.jar
           -DCONFIG_SERVICE_PASSWORD=xxxx 

### Hystrix Dashboard and Monitoring Circuit Breaker status

View the [Hystrix Dashboard](https://cloud.spring.io/spring-cloud-netflix/multi/multi__hystrix_timeouts_and_ribbon_clients.html) with below URL:

http://localhost:8989/hystrix

##### For Monitoring with Hystrix Stream

Enter Host input: http://localhost:8301/actuator/hystrix.stream to monitor circuit breakers for finance-service application or  
                  http://localhost:8309/actuator/hystrix.stream for analytics-service application.

##### For Monitoring with Turbine Stream

Enter Host input: http://localhost:8989/turbine.stream?cluster=FINANCE-SERVICE  (or ANALYTICS-SERVICE to monitor analytics-service)  
Delay: 2000  
Title: My Dashboard Title  

Click "Monitor Stream" to view Hystrix Dashboard for the provided input stream.
