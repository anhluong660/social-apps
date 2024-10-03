FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/social-service-1.0-SNAPSHOT.jar /app/social-service.jar

EXPOSE 9003

CMD ["java", "-jar", "/app/social-service.jar", "--spring.profiles.active=dev"]