#!/bin/sh
pkill -f 'java -jar'
git pull
./gradlew backend:clean assemble --console=plain
nohup java -jar backend/build/libs/backend-1.0-SNAPSHOT.jar &