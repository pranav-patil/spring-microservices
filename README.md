# Spring Micro Services Showcase
[Spring Boot](https://docs.spring.io/spring-boot/docs/1.5.10.RELEASE/reference/htmlsingle/) demonstrates the capabilities of spawning but micro services for all the functionality 
including MVC, JPA, Spring Security.

In this showcase you'll see the following in action:

* [Zuul Server](https://cloud.spring.io/spring-cloud-netflix/multi/multi__router_and_filter_zuul.html)
* [Eureka Server](https://github.com/Netflix/eureka/wiki/Eureka-at-a-glance)

To run the application:
-------------------
From the command line with Git and Gradle:

    $ git clone https://github.com/pranav-patil/spring-microservices.git
    $ cd spring-microservices
    $ gradle clean build
    $ java -jar config-server/build/libs/config-server-0.0.1-SNAPSHOT.jar
