# java-kafka-ses

## requirements
- <a href="https://www.oracle.com/java/technologies/downloads/">java</a>
- <a href="https://maven.apache.org/download.cgi">maven</a>
- <a href="https://github.com/jfsax/kafka-basics">kafka producer</a>

## before running the project
- set the following environment variables:
```
AWS_ACCESS_KEY="YOUR_AWS_ACCESS_KEY"
AWS_SECRET_KEY="YOUR_AWS_SECRET_KEY"
KAFKA_TOPIC="TOPIC_NAME"
KAFKA_HOST="YOUR_IP:9092"
KAFKA_GROUP_ID_READER="KAFKA_GROUP_ID"
```

- change the message `sender` and the `recipient`

## running the project
- go to the root directory
```
cd java-kafka-ses/kafkases
```

- generate jar file:
```
./build.sh
```

- run:
```
./start.sh
```
