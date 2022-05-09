# java-kafka-ses

instrunctions on how to setup, run and send/consume messages in Kafka: https://github.com/jfsax/kafka-basics

## before running the project
- set the following environment variables:
```
AWS_ACCESS_KEY="YOUR_AWS_ACCESS_KEY"
AWS_SECRET_KEY="YOUR_AWS_SECRET_KEY"
KAFKA_TOPIC="TOPIC_NAME"
KAFKA_HOST="IP:9092"
```

## running the project
- go to the root directory
```cd kafkases```

- generate jar file:
```./build.sh```

- run:
```./start.sh```
