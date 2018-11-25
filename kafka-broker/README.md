Kafka Message Broker
=============

Apache Kafka is a fast and scalable messaging queue, capable of handling heavy read and write operations.
Apache Kafka requires a running Zookeeper instance, which is used for reliable distributed co-ordination.
Apache ZooKeeper is a distributed, open-source coordination service for distributed applications. 
ZooKeeper enables kafka to manage multiple clusters of kafka instances. 

### Installation and Running of Apache Zookeeper

* Download latest [Apache Zookeeper release](http://zookeeper.apache.org/releases.html#download) and extract the .tar.gz file. 
* Copy and Rename **zoo_sample.cfg** to **zoo.cfg** file in ZOOKEEPER_HOME\conf directory, were ZOOKEEPER_HOME is the path of extracted zookeeper directory.
* Find & edit the line in zoo.cfg file from "dataDir=/tmp/zookeeper" to "dataDir=ZOOKEEPER_HOME\data" 
* Go to ZOOKEEPER_HOME\bin directory and execute the command "zkserver" to run zookeeper.
* Apache Zookeeper runs on default port 2181.

All the above steps can be executed directly by running below runZooKeeper gradle task for windows.

    $ gradle runZooKeeper

### Installation and Running of Apache Kafka message broker

Before running kafka ensure that Apache Zookeeper instance is already running on default port 2181.

* Download latest stable [Apache Kafka release](https://kafka.apache.org/downloads) and extract the .tgz file.
* Go to config directory in Apache Kafka in path KAFKA_HOME\config were KAFKA_HOME is the path of extracted kafka directory.
* Find "log.dirs" in **server.properties** in config directory, and replace line line "log.dirs=/tmp/kafka-logs" to "log.dir= KAFKA_HOME\kafka-logs".
*  If Zookeeper is running on different machine or cluster, edit "zookeeper.connect:2181" to the custom IP and port.
* Go to KAFKA_HOME directory and execute the below windows command to run kafka.
* Apache Kafka runs on default port 9092.


    .\bin\windows\kafka-server-start.bat .\config\server.properties
 
All the above steps can be executed directly by running below runKafka gradle task for windows.

    $ gradle runKafka

### Creating topics

Kafka topics can be created using the below windows command. Although **zipin** should already exist by default.

    kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic zipin

Alternatively **zipin** topic can be created using below createZipkinTopic gradle task.

    $ gradle createZipkinTopic

### Consuming topics

To produce messages to Kafka topic use the below windows command.

    kafka-console-producer.bat --broker-list localhost:9092 --topic zipin

To consume the messages in Kafka topic use the below windows command.

    kafka-console-consumer.bat --zookeeper localhost:2181 --topic zipin

Alternatively messages from **zipin** topic can viewed by running consumeZipkinTopic gradle task.

    $ gradle consumeZipkinTopic

### Notes

* Microsoft Windows XP or later have  the maximum length of command to 8191 characters. If it exceeds we get an error **The input line is too long. The syntax of the command is incorrect.**.
Apache Kafka command has many classpath dependencies hence, running this in a nested directory could cause above error. To avoid this please run Apache Kafka in a directory within c: root seperately or clone this project in c: root itself.
* On running the bat commands for zookeeper and kafka (or running gradle runKafka) we might get unrelated errors such as **Process 'command 'cmd'' finished with non-zero exit value 255** and when checked details it would be **\IBM\WebSphere was unexpected at this time.**. Not sure about the reason but this can be avoided by removing corresponding path from the CLASSPATH environment variable.
* Another frequent error on running **gradle runKafka** is **The input line is too long. The syntax of the command is incorrect.** which essentially means the windows command executed by gradle script is too long due to additional directory paths. To avoid this case it is recommended to copy kafka binaries in root path of a drive and directly executing the kafka commands in windows terminal.       
