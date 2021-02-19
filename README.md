#Trot-Race-Worker-Process

Trot-Race-Worker-Process is a Spring Boot Java application which helps get the trot race data from a long polling end point and save the data in a PostgreSQL database.

#Running service locally
java -jar trot-race-worker-process.jar --hostname=localhost

#Pull the image from docker hub:
docker pull manjinder50/trot-race-worker-process-image
