FROM openjdk:11
ADD target/bookstore-docker.ar bookstore-docker.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "bookstore-docker.jar"]