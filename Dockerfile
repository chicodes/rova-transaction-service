FROM public.ecr.aws/docker/library/maven:3.6-jdk-11-slim AS build

COPY pom.xml /app

COPY src/main /app/src


WORKDIR /app
RUN mvn clean install -DskipTests

#
# Package stage
#

FROM public.ecr.aws/docker/library/openjdk:11-jre-slim

WORKDIR /app

COPY --from=build /app/target/transactionservice-0.0.1-SNAPSHOT.jar transactionservice.jar

EXPOSE 3394

ENTRYPOINT ["java","-jar","transactionservice.jar"]
