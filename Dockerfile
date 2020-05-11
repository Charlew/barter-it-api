FROM adoptopenjdk/openjdk13
ADD build/libs/barter-it-api-0.0.1-SNAPSHOT.jar .
COPY . .
EXPOSE 8080
CMD java -jar barter-it-api-0.0.1-SNAPSHOT.jar
