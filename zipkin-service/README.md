Zipkin Service
=============

[OpenZipkin](https://github.com/openzipkin/zipkin) is a distributed tracing system which enables to trace requests spanning across multiple micro services. 
Distributed tracing is a process of collecting end-to-end transaction graphs in real time. 
Zipkin collects the request data in form of traces and spans. A trace represents entire journey of a request, while a span represents single operation call.
The first service called has traceId and spanId, while the subsequent service call have additional parentId including traceId and spanId, which essentially is spanId of the caller service.  

[Spring Cloud Sleuth](http://cloud.spring.io/spring-cloud-sleuth/single/spring-cloud-sleuth.html) is a tracer which adds unique trace and span ids to Slf4J MDC. 
It has an instrumentation or sampling policy represented by application property spring.sleuth.sampler.probability.

### Running the Zipkin default application (using http for trace requests)

In order to run the default [zipkin-server](https://github.com/openzipkin/zipkin/tree/master/zipkin-server) use the gradle runZipkin task

    $ gradle runZipkin

The runZipkin downloads the [zipkin-server-executable jar](https://search.maven.org/remote_content?g=io.zipkin.java&a=zipkin-server&v=LATEST&c=exec) from maven repository.
It then executes the jar using default java command. Instead of using the above gradle task, you can directly execute the below command.  

    $ java -jar zipkin-server-2.10.1-exec.jar

### Running the Zipkin Kafka application (using kafka message broker for trace messages)

Before running zipkin with kafka message broker, go to kafka-broker service to run kafka message broker.
By default the kafka message broker executes on port 9092. Then use the below runZipkinKafka gradle task to run zipkin stream server configured with default kafka message broker. 

    $ gradle runZipkinKafka
    
Instead of using the above gradle task, you can directly execute the below java command with the additional kafka config argument [zipkin.collector.kafka.bootstrap-servers](https://github.com/openzipkin/zipkin/tree/master/zipkin-autoconfigure/collector-kafka).

    $ java -jar zipkin-server-2.10.1-exec.jar -Dzipkin.collector.kafka.bootstrap-servers=127.0.0.1:9092

### Accessing Zipin Server

To view the zipin traces for micro services using zipin's brave UI, access the server with default port 9411.   

http://localhost:9411/
