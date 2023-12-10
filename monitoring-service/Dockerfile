FROM openjdk:17
EXPOSE 8765
COPY ./target/monitoring-service-0.0.1-SNAPSHOT.jar monitoring-service.jar
ENTRYPOINT ["java","-jar","/monitoring-service.jar"]