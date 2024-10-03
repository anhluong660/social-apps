FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/analysis-metric-1.0-SNAPSHOT.jar /app/analysis-metric.jar

EXPOSE 9300

CMD ["java", "-jar", "/app/analysis-metric.jar", "--spring.profiles.active=dev"]
CMD ["java", "-jar", "/app/user-manager.jar   ", "--spring.profiles.active=dev"]