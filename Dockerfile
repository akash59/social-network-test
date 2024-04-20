# Use an official Maven image
FROM maven:3.6.3-jdk-11-slim as maven_build

# Copy only the project's pom and source code
COPY src /home/app/src
COPY pom.xml /home/app

# Set the working directory
WORKDIR /home/app

# This will download all required dependencies into the image
RUN mvn dependency:go-offline

# Build without testing, as tests will run separately
RUN mvn clean package -DskipTests

# Use the maven image for testing
FROM maven:3.6.3-jdk-11-slim

# Copy built artifacts and other files needed for running the application
COPY --from=maven_build /home/app/target /home/app/target
COPY --from=maven_build /home/app/src /home/app/src
COPY --from=maven_build /home/app/pom.xml /home/app
COPY --from=maven_build /root/.m2 /root/.m2

WORKDIR /home/app

# Command to run tests
CMD ["mvn", "clean", "verify"]
