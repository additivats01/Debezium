# CDC Setup

## Overview
This document provides step-by-step instructions for setting up a Change Data Capture (CDC) environment using Podman containers. The setup includes Apache Kafka, Zookeeper, Kafdrop, Schema Registry, Kafka Connect, ActiveMQ, and Elasticsearch. Additionally, Java requirements are outlined for running the project.

---

## Kafka and Zookeeper Setup

### Create a Pod for Kafka Components

```bash
podman pod create --name kafkapp -p 9000:9000 -p 9092:9092 -p 8083:8083 -p 2181:2181 -p 29092:29092

This command creates a Pod named 'kafkapp' with ports exposed for Kafka components.

Zookeeper Container

podman run --name zookeeper --pod kafkapp -dt -e ZOOKEEPER_CLIENT_PORT=2181 -e ZOOKEEPER_TICK_TIME=2000 confluentinc/cp-zookeeper:7.3.2

Launches a Zookeeper container within the 'kafkapp' Pod.

Kafka Container

podman run --name kafka --pod kafkapp -dt -e KAFKA_BROKER_ID=1 -e KAFKA_ZOOKEEPER_CONNECT=localhost:2181 -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=PLAINTEXT:PLAINTEXT,PLAINTEXT_INTERNAL:PLAINTEXT -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092,PLAINTEXT_INTERNAL://localhost:29092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 -e KAFKA_TRANSACTION_STATE_LOG_MIN_ISR=1 -e KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR=1 -e TOPIC_AUTO_CREATE=true confluentinc/cp-kafka:7.3.2


Runs a Kafka container within the 'kafkapp' Pod with specified configurations.

Kafdrop Container

podman run --name kafdrop --pod kafkapp -d -e KAFKA_BROKERCONNECT=localhost:9092 -e JVM_OPTS="-Xms32M -Xmx64M" -e SERVER_SERVLET_CONTEXTPATH="/" docker.io/obsidiandynamics/kafdrop:latest

Deploys Kafdrop for Kafka monitoring and management within the 'kafkapp' Pod.

Kafka Connect Setup

podman run -itd --name connect --pod kafkapp -e CONNECT_REST_PORT=8083 -e CONNECT_BOOTSTRAP_SERVERS=localhost:29092 -e CONNECT_GROUP_ID=1 -e CONNECT_KEY_CONVERTER=org.apache.kafka.connect.storage.StringConverter -e CONNECT_VALUE_CONVERTER=io.confluent.connect.avro.AvroConverter -e CONNECT_CONFIG_STORAGE_TOPIC=my_connect_configs -e CONNECT_REST_ADVERTISED_HOST_NAME=localhost -e CONNECT_OFFSET_STORAGE_TOPIC=my_connect_offsets -e CONNECT_STATUS_STORAGE_TOPIC=my_connect_statuses -e CONNECT_VALUE_CONVERTER_SCHEMA_REGISTRY_URL='http://localhost:8081' -e   CONNECT_CONFIG_STORAGE_REPLICATION_FACTOR="1" -e CONNECT_OFFSET_STORAGE_REPLICATION_FACTOR="1" -e CONNECT_STATUS_STORAGE_REPLICATION_FACTOR="1" -e CONNECT_PLUGIN_PATH=/usr/share/java,/usr/share/confluent-hub-components,/data/connect-jars,/usr/share/filestream-connectors -v /home/user/Documents/ConnectPlugin/:/data/connect-jars docker.io/confluentinc/cp-kafka-connect


Configures and runs a Kafka Connect container within the 'kafkapp' Pod.


Plugin for Source Connector (Debezium Postgres)


curl --location 'http://localhost:8083/connectors' \
--header 'Content-Type: application/json' \
--data '{
    "name": "CDCDBPluginR",
    "config": {
    "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
    "database.user": "pguser",
    "database.dbname": "strapiDB",
    "database.port": "5432",
    "plugin.name": "pgoutput",
    "key.converter.schemas.enable": "false",
    "topic.prefix": "NPI.Strapi",
    "decimal.handling.mode": "string",
    "database.hostname": "192.168.100.247",
    "database.password": "password",
    "value.converter.schemas.enable": "false",
    "name": "StrapiDBconv",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
    "key.converter": "org.apache.kafka.connect.json.JsonConverter"
  }
}'


Registers a Debezium Postgres connector using the Kafka Connect REST API.


ActiveMQ Setup


podman run --name activemq -itd  -p 61616:61616 -p 8161:8161 rmohr/activemq

Starts an ActiveMQ container with exposed ports.


Elasticsearch Setup


podman run --name avelastic --network avelastic -p 9200:9200 -it -m 4GB -e discovery.type=single-node -e xpack.security.enabled=false -e xpack.security.enrollment.enabled=false docker.elastic.co/elasticsearch/elasticsearch:8.8.2

Runs an Elasticsearch container with specified configurations.


Java Requirements
Java version: 11
Java Home setup
Maven version: 3.8.6 and above
Command to run project: mvn clean compile quarkus:dev -DskipTests

Database
Two databases: Fresh and Renewal


This document provides a detailed explanation of each component along with the corresponding commands for setup. Feel free to customize it further as needed.

