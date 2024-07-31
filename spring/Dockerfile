FROM openjdk:17-alpine

RUN mkdir /app
COPY ./build/libs/spring-1.0.0.jar /app/app.jar
WORKDIR /app

EXPOSE ${PORT}

CMD ["java", "-jar", "app.jar"]