FROM adoptopenjdk/openjdk12:alpine-slim

RUN mkdir /deployment 

COPY target/spring-boot-admin-server.jar /deployment/application.jar

WORKDIR /deployment/

CMD ["java", "-Xmx256m", "-jar", "application.jar"]
