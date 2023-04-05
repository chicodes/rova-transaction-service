FROM public.ecr.aws/docker/library/maven:3.6-jdk-11-slim AS build

COPY pom.xml /app/
RUN mvn dependency:go-offline

COPY src/main /app/src/main/
COPY src/test /app/src/test/

WORKDIR /app
RUN mvn clean install -DskipTests

#
# Package stage
#

FROM public.ecr.aws/docker/library/openjdk:11-jre-slim

WORKDIR /app

COPY --from=build /app/target/medusa_merchant-0.0.1-SNAPSHOT.jar portal.jar

EXPOSE 3394

ENTRYPOINT ["java","-jar","portal.jar"]
