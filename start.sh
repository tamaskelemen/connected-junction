#!/bin/sh
pkill -f 'java -jar'
git pull
./mvnw package
nohup java -jar target/demo-0.0.1-SNAPSHOT.war &