FROM maven:3.9.9-eclipse-temurin-21

RUN apt-get update\
    && apt-get upgrade -y

WORKDIR /home/user/app

COPY . .

RUN mvn clean install

CMD ["java", "-jar", "target/app-1.0.0.jar"]