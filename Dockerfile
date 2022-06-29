FROM maven:3.8.1-adoptopenjdk-16 AS build
RUN mkdir /src
COPY . /src
WORKDIR /src
#RUN mvn --settings /src/settings.xml clean install
RUN mvn clean install

FROM  openjdk:16-jdk-slim-buster
RUN mkdir /app
COPY --from=build /src/target/*.jar /app/app.jar

WORKDIR /app
ENV JAVA_OPTS "$JAVA_OPTS -Dspring.profiles.active=dev"

EXPOSE 5000 9090
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]