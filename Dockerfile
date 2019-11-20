FROM openjdk:11-jre-slim
COPY ./build/libs/Calculator-0.0.1-SNAPSHOT.jar calculator.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/calculator.jar"]