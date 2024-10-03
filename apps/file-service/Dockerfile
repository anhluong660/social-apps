FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/file-service-1.0-SNAPSHOT.jar /app/file-service.jar

EXPOSE 9200

CMD ["java", "-jar", "/app/file-service.jar", "--spring.profiles.active=dev"]