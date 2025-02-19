FROM openjdk:17-jdk-slim

#VOLUME /tmp

# Set timezone to Asia/Seoul
RUN apt-get update && apt-get install -y tzdata \
    && ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime \
    && echo "Asia/Seoul" > /etc/timezone \
    && apt-get clean

EXPOSE 8080

# The application's jar file
ARG JAR_FILE=build/libs/*.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar", "--spring.profiles.active=prod"]
