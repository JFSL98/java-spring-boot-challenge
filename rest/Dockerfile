FROM eclipse-temurin:21

RUN mkdir /opt/app

COPY ./target/rest-0.0.1-SNAPSHOT.jar /opt/app

ENTRYPOINT ["java", "-jar", "/opt/app/rest-0.0.1-SNAPSHOT.jar"]