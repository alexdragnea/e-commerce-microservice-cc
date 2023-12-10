FROM openjdk:17
EXPOSE 8093
COPY ./target/notification-service-0.0.1-SNAPSHOT.jar notification-service.jar
ENTRYPOINT ["java","-jar","/notification-service.jar"]