FROM eclipse-temurin:23-jdk AS builder

WORKDIR /app
COPY build.gradle settings.gradle ./
COPY gradle gradle
COPY gradlew .
COPY src ./src
ARG SPRING_PROFILES_ACTIVE=prod
RUN chmod +x gradlew
RUN ./gradlew build -x test --no-daemon -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}

FROM eclipse-temurin:23-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]