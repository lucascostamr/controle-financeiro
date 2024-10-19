FROM maven:3.9.9-eclipse-temurin-21

RUN apt-get upgrade\
    && apt-get update -y\
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /home/user/app

COPY . .

RUN mvn clean install

HEALTHCHECK --interval=30s --timeout=30s --start-period=5s --retries=3 CMD [ "ls", "target/app-1.0.0.jar" ]

CMD ["java", "-jar", "target/app-1.0.0.jar"]