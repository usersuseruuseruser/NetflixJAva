# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim as build
# Set the working directory in the container
WORKDIR /workspace/app

RUN apt-get update && apt-get install -y unzip

# Copy the Gradle distribution
COPY ./gradle-8.7-bin.zip /workspace/app/
RUN unzip /workspace/app/gradle-8.7-bin.zip -d /workspace/app/

# Set the GRADLE_USER_HOME environment variable
ENV GRADLE_USER_HOME=/workspace/app/gradle-8.7

# Copy the Gradle configuration files
COPY gradlew .
COPY gradle gradle
COPY gradle.properties .
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Grant permissions for the gradlew script to execute
RUN chmod +x ./gradlew

# Copy the rest of your app's source code
COPY src src

ENV GRADLE_DIST_URL=file:///workspace/app/gradle-8.7-bin.zip

RUN sed -i 's|distributionUrl=.*|distributionUrl='"$GRADLE_DIST_URL"'|' gradle/wrapper/gradle-wrapper.properties

# Build the project and skip tests
RUN ./gradlew build -x test --no-daemon

# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

EXPOSE 8080


# Copy the jar file from the build stage
COPY --from=build /workspace/app/build/libs/*.jar ./


# Run the jar file
ENTRYPOINT ["java","-jar","semester-work-useruseruuseruser-0.0.1-SNAPSHOT.jar"]