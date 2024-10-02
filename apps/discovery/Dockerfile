FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/discovery-1.0-SNAPSHOT.jar /app/server-discovery.jar

EXPOSE 8761

CMD ["java", "-jar", "/app/server-discovery.jar"]