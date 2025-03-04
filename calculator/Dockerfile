FROM eclipse-temurin:21

RUN mkdir /opt/app

COPY ./target/calculator-0.0.1-SNAPSHOT.jar /opt/app

ENTRYPOINT ["java", "-jar", "/opt/app/calculator-0.0.1-SNAPSHOT.jar"]