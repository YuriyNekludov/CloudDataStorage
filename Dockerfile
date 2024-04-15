FROM maven AS build
WORKDIR /ar
COPY /src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean package -DskipTests

FROM openjdk
WORKDIR /
COPY /src /src
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]