# Spring Micro Services Showcase

[Microservices](https://microservices.io/) is an architectural style that structures an application as a collection of loosely coupled services, where each service implements business capabilities. A microservice runs in its own process and communicates other services via HTTP API. Every microservice can be deployed, upgraded, scaled, and restarted independently of the other services in an application.
It enables continuous delivery/deployment of large, complex applications. It allows better component isolation and high resilience against component failures. Smaller components in microservices can be scaled easily to meet increasing demand for a specific component. It increases developer independence and allows parallel development across multiple smaller teams.
Microservices brings additional complexity as the developers have to mitigate fault tolerance, network latency, and deal with load balancing. Also deployment and testing of such a distributed system is complicated and tedious.

[Spring Boot](https://docs.spring.io/spring-boot/docs/1.5.10.RELEASE/reference/htmlsingle/) enables to spawn stand-alone, production-grade Spring-based Applications with very little configuration, hence it is widely used in micro services arena. It is preconfigured with the Spring's standard configuration and has an embedded Tomcat or Jetty to provide full fledged server functionality.
[Spring Cloud](http://projects.spring.io/spring-cloud/) framework, provides a collection of tools and solutions to some of the commonly encountered patterns when building distributed systems. It addresses solutions for some of the common problems in distributed systems including Configuration management, Service discovery, Circuit breakers and Distributed sessions.
[Docker](https://www.docker.com/) is a open platform to create, deploy, and run applications as a lightweight, portable, self-sufficient container, which can run virtually anywhere.
These tools and platforms form the foundation for spring micro services project.

The spring micro services showcase contains the following services in action:

* [Elastic Stack](elastic-stack/README.md): ElasticSearch-Logstash-Kibana provides log storage and management.
* [Kafka Broker](kafka-broker/README.md): Kafka Message broker provides messaging capabilities to zipkin trace messages to Zipkin server.
* [Zipkin Service](zipkin-service/README.md): Zipkin enables to trace requests spanning across multiple services.

* [Config Service](config-service/README.md): Configuration service provides access to spring **application.yml** configuration files for corresponding service stored in its centralized (currently local) location.
* [Discovery Service](discovery-service/README.md): Eureka discovery service allows micro services to find and communicate with each other.
* [Authorization Service](authorization-service/README.md): Authorization service is responsible for providing OAuth2 access tokens after authentication and validating request access tokens before allowing access to the authorized services.
* [Data Service](data-service/README.md): Data service provides reactive services using Spring WebFlux to fetch various data (currently stock data).
* [Finance Service](finance-service/README.md): Finance service provides services to fetch financial data especially stock details.
* [Analytics Service](analytics-service/README.md): Analytics services consume data from various sources, mainly finance-service and data-service and provide analytical details regarding the corresponding data.
* [Monitor Service](monitor-service/README.md): Monitor service mainly gathers hystrix circuit breaker data from finance and analytics services, and displays the [Hystrix Dashboard](https://github.com/Netflix-Skunkworks/hystrix-dashboard).
