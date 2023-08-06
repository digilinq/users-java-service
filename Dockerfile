FROM maven:3.9.3-eclipse-temurin-20 AS build
WORKDIR /build
COPY pom.xml pom.xml
COPY api api
COPY web web
COPY repository repository
COPY service service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:20.0.2_9-jre
WORKDIR /work
COPY --from=build /build/web/target/users.jar users.jar
ENTRYPOINT ["java","-jar","users.jar"]