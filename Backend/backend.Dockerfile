FROM maven:3.8-amazoncorretto-19 as BUILD_STAGE

ADD Backend /tmp/Backend

WORKDIR /tmp/Backend

RUN mvn package


FROM amazoncorretto:19.0.1 as DEPLOY_STAGE

COPY --from=BUILD_STAGE /tmp/Backend/target/*.jar coinlibrary.jar

EXPOSE 8080

CMD ["java", "-jar", "coinlibrary.jar", "--spring.config.name=application-prod"]