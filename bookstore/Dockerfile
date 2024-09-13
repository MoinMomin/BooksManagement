# the first stage of our build will use a maven 3.8.1 parent image
FROM maven:3.8.1-openjdk-11-slim AS MAVEN_BUILD
# Add Maintainer Info
LABEL maintainer="4u2moin@gmial.com"
# copy the pom and src code to the container
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package
# the second stage of our build will use open jdk 11 on alpine
FROM adoptopenjdk/openjdk11:alpine-slim
# copy only the artifacts we need from the first stage and discard the rest
COPY --from=MAVEN_BUILD /usr/src/app/target/booksStore.jar /usr/app/booksStore.jar
# Make port 8080 available to the world outside this container
EXPOSE 8080
# set the startup command to execute the jar
#CMD ["java -Dspring.profiles.active=$SPRING_ACTIVE_PROFILES", "-jar", "/usr/app/booksStore.jar"]
CMD java -Dspring.profiles.active=$SPRING_ACTIVE_PROFILES -jar /usr/app/booksStore.jar