FROM openjdk:11-jre-slim
ADD target/backend.jar .
EXPOSE 8080
CMD java -jar backend.jar
