FROM gradle:8-jdk17 AS builder
WORKDIR /app

COPY . .

RUN gradle build

RUN mkdir dist && cp -r build/libs/app.jar dist/app.jar
RUN cp -r scripts dist/scripts

FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=builder /app/dist .

RUN chmod +x app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "app.jar"]
