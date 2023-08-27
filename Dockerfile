FROM maven:3-eclipse-temurin-20 AS build
RUN mkdir /src
COPY . /src
WORKDIR /src

ADD settings.xml /root/.m2/settings.xml

RUN mvn clean install

FROM  eclipse-temurin:20_36-jdk
RUN mkdir /app
COPY --from=build /src/target/*.jar /app/app.jar

WORKDIR /app

ENV JAVA_OPTS "$JAVA_OPTS \
              -XX:+UseParallelGC \
              -XX:ActiveProcessorCount=2 \
              -XX:MaxRAMPercentage=75 \
              -Duser.timezone=America/Fortaleza"

EXPOSE 5000 9090
ENTRYPOINT ["sh", "-c", "java ${JVM_OPTS} -jar app.jar"]