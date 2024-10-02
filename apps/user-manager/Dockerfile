FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/user-manager-1.0-SNAPSHOT.jar /app/user-manager.jar

EXPOSE 9001

CMD ["java", "-jar", "/app/user-manager.jar", "--spring.profiles.active=dev"]