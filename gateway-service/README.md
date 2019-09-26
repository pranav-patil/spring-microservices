Gateway Service (Spring Cloud Gateway)
=============

The [Spring Cloud Gateway](https://cloud.spring.io/spring-cloud-gateway/single/spring-cloud-gateway.html) is built using Spring Framework 5, Project Reactor and Spring Boot 2. It supports non-blocking APIs and WebSockets unlike [Netflix Zuul](http://cloud.spring.io/spring-cloud-netflix/single/spring-cloud-netflix.html#_router_and_filter_zuul). Spring Cloud Gateway has superior performance compared to Netflix Zuul as it uses Netty’s EventLoop and uses Spring Boot2 as a default web server. Spring Cloud Gateway uses Spring Webflux to provide non-blocking calls, exposes a Netty based server, uses a Netty based client to make the downstream microservice calls and uses reactor-core for the rest of the flow.
When clients send requests to Spring Cloud Gateway, the Gateway Handler Mapping determines the Route matched by the request and it is sent to sent to the Gateway Web Handler. The Gateway Web Handler runs the request through a (pre) filter chain, then executing actual proxy request and later any post filters.

* **Route**: A Route is basic building block of the gateway. It is defined by an ID, a destination URI, collections of predicates and filters. A route is matched if aggregate predicate is true.
* **Predicate**: It is a Java 8 Function Predicate. It is used to match the HTTP request, such as headers or parameters with a route.
* **Filter**: These are instances Spring Framework GatewayFilter constructed in with a specific factory. Here, requests and responses can be modified before or after sending the downstream request.

### Running the Gateway Service

The **CONFIG_SERVICE_PASSWORD** is a required parameter to run gateway-service as it enables to access gateway-service.yml configuration file from the [config-service](../config-service/README.md).
Optionally **spring.profiles.active** can be passed with value **production** which enables logback to send all logs to [Elastic Stack](../elastic-stack/README.md) instead of logging in the console by default.

    $ java -jar gateway-service/build/libs/gateway-service-0.0.1-SNAPSHOT.jar
           -DCONFIG_SERVICE_PASSWORD=xxxx
		   -Dspring.profiles.active=production

 
