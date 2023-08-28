FROM exoplatform/jdk:openjdk-17-ubuntu-2004
#FROM openjdk:17
ARG JAR_FILE
COPY ${JAR_FILE} /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ami-0.1.0-SNAPSHOT.jar"]