FROM openjdk:21-jdk

WORKDIR /app
COPY build/libs/config.jar config.jar

ENV PORT=8888
EXPOSE $PORT

CMD java -jar config.jar