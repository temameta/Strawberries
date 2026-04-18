FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /build
COPY gradlew .
COPY gradle gradle
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew
COPY settings.gradle .
COPY build.gradle .
COPY api/user-api api/user-api
COPY service/user-service service/user-service
RUN ./gradlew :service:user-service:build -x test

FROM eclipse-temurin:21-jdk-alpine AS extractor
WORKDIR /layers
COPY --from=builder /build/service/user-service/build/libs/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring
COPY --from=extractor /layers/dependencies/ ./
COPY --from=extractor /layers/spring-boot-loader/ ./
COPY --from=extractor /layers/snapshot-dependencies/ ./
COPY --from=extractor /layers/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]