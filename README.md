# Java Spring project "Social networking site"
Friend-service

## Description

This service is responsible for managing friendships.
Within this service, friendships are created between users and a list of possible friends for each user. The service provides all the functionality related to the friends section of our social network.

## Service Technologies

- Java version 11
- Spring Framework
- Flyway
- Lombok
- Mapstruct
- Spring Data JPA
- PostgreSQL
- Spring Security
- Spring Cloud OpenFeign
- Spring Cloud Netflix Eureka
- JWT(JsonWebToken)
- Nexus repository
- Swagger OpenApi

## Technical description
### How to run the application on your device:
1. (Pre-configuring the PostgreSQL database) Specify in the application.yaml file, or in the environment variables in your IDE, the required application configuration parameters to run:
    - SERVER_PORT (The port of your application. Specify it manually if you are not going to use the default port: 8086)
    - DB_PASSWORD (Password for the database)
    - DB_URL (The address of the database your application connects to. You should specify it manually if you are not going to use default postgresql url: jdbc:postgresql://localhost:5432/friend_service)
    - SECRET_KEY (Your application's secret key. This is needed to protect your service which uses JWT technology)
    - EUREKA_URI (Address of your Eureka server. Specify it if you are not going to use the default address: http://localhost:8081/eureka )
2. Run the file FriendApp.java.
