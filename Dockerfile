FROM amazoncorretto:17.0.11-alpine AS build
COPY . /build/src
WORKDIR /build/src
RUN ./gradlew clean
RUN ./gradlew buildFatJar --no-daemon

FROM amazoncorretto:17.0.11-alpine
EXPOSE 8080
RUN mkdir /app
COPY --from=build /build/src/build/libs/*.jar /app/SabumbiCat-all.jar
ENTRYPOINT ["java","-jar","/app/SabumbiCat-all.jar"]