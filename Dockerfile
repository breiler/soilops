FROM openjdk:8-jdk-alpine
RUN addgroup -S soilops && adduser -S soilops -G soilops
USER soilops:soilops

COPY target/soilops-*-exec.jar soilops.jar
ENTRYPOINT ["java","-jar","/app.jar"]