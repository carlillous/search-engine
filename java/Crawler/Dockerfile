FROM openjdk:11-jre-slim

WORKDIR /app

COPY target .
#COPY ../DataLake/src/main/resources/credentials.json .

RUN mv *.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]