[![Build](https://github.com/Orisakwe-Nwokocha/kafka-tutorial/actions/workflows/build.yml/badge.svg)](https://github.com/Orisakwe-Nwokocha/kafka-tutorial/actions/workflows/build)

# Orisha Spring Boot Kafka Tutorial

## Run the App
### From the Command Line
```shell
docker-compose up -d
./mvnw spring-boot:run
```
## Tags
Tags have been used to snapshot the project at certain points in time.

To checkout a branch run the following:
```shell
git checkout ${TAG_NAME}
```

Here are the available tags:

| Branch name   | Description                               |
| -----------   | -----------                               |
| `tutorial`    | The unplished end-result of the tutorial. |

## Kafka Command Line
Here are the commands used to interact with Kafka in the tutorial:

#### Create a Topic
```shell
docker exec broker \
kafka-topics --bootstrap-server broker:9092 \
             --create \
             --topic "customer.visit"
```

### Command Line Consumer
```shell
docker exec --interactive --tty broker \
kafka-console-consumer --bootstrap-server broker:9092 \
                       --topic "customer.visit" \
                       --from-beginning
```

### Command Line Producer
```shell
docker exec --interactive --tty broker \
   kafka-console-producer --bootstrap-server broker:9092 \
                          --topic "customer.visit"
```

## Jackson Dependencies
In order to use Jackson and the module for Java8 DateTimes, use the
following dependencies:
```xml
<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-databind</artifactId>
</dependency>
<dependency>
  <groupId>com.fasterxml.jackson.datatype</groupId>
  <artifactId>jackson-datatype-jsr310</artifactId>
</dependency>
```
