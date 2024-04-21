#!/bin/bash

# Build the application with Gradle
./gradlew build

# Run docker-compose to build and start the services
docker-compose up --build -d
