FROM openjdk:8
EXPOSE 8999
ADD target/trot-race-worker-process.jar trot-race-worker-process.jar
ENTRYPOINT ["java","-jar","trot-race-worker-process.jar"]