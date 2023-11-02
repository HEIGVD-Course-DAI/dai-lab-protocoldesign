FROM maven:3.9.4-amazoncorretto-21
WORKDIR /app
#COPY target/*.jar /app/app.jar
COPY . .
RUN mvn clean package
RUN ls
CMD ["java", "-jar", "target/client-1.0.jar"]


#FROM openjdk:21
#WORKDIR /app
#COPY target/*.jar /app/app.jar
#CMD ["java", "-jar", "app.jar"]