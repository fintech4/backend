FROM openjdk:17-jdk-slim

ENV TZ=Asia/Seoul

EXPOSE 8080

# The application's jar file
ARG JAR_FILE=build/libs/*.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar",  "app.jar", "--spring.profiles.active=prod"]

