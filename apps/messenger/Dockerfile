FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/messenger-1.0-SNAPSHOT.jar /app/messenger.jar

EXPOSE 9002

CMD ["java", "-jar", "/app/messenger.jar", "--spring.profiles.active=dev"]