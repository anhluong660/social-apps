FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/gateway-1.0-SNAPSHOT.jar /app/gateway.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/gateway.jar", "--spring.profiles.active=dev"]