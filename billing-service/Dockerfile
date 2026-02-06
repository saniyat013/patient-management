FROM gradle:8.7.0-jdk21 AS builder

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle ./gradle
COPY src ./src

RUN ./gradlew build -x test

FROM openjdk:26-ea-21-jdk-slim AS runner

WORKDIR /app

COPY --from=builder /app/build/libs/billing-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4001
EXPOSE 9001

ENTRYPOINT ["java", "-jar", "app.jar"]
