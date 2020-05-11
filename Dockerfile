FROM gradle:6.4.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM adoptopenjdk/openjdk13
ADD build/libs/barter-it-api-0.0.1-SNAPSHOT.jar .
COPY . .
EXPOSE 8080
CMD java -jar barter-it-api-0.0.1-SNAPSHOT.jar
