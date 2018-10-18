FROM java:8-jdk

ARG PROVIDER_ORGANIZATION
ARG PROVIDER_ID
ARG PROVIDER_SECRET
ARG PROVIDER_DNS_NAME
ARG PROVIDER_DNS_PORT

ENV HOST_DIR ./ChargingStationProvider/
ENV BASE_CONTAINER_DIR /opt/bigiot-provider/

COPY $HOST_DIR/build.gradle $BASE_CONTAINER_DIR
COPY $HOST_DIR/example.properties $BASE_CONTAINER_DIR
COPY $HOST_DIR/gradlew $BASE_CONTAINER_DIR
COPY $HOST_DIR/settings.gradle $BASE_CONTAINER_DIR
COPY $HOST_DIR/gradle $BASE_CONTAINER_DIR/gradle
COPY $HOST_DIR/src $BASE_CONTAINER_DIR/src

WORKDIR $BASE_CONTAINER_DIR

RUN chmod -R 755 gradlew

RUN sed -i -e "s#PROVIDER_ORGANIZATION#$PROVIDER_ORGANIZATION#g" ./example.properties
RUN sed -i -e "s#PROVIDER_ID#$PROVIDER_ID#g" ./example.properties
RUN sed -i -e "s#PROVIDER_SECRET#$PROVIDER_SECRET#g" ./example.properties
RUN sed -i -e "s#PROVIDER_DNS_NAME#$PROVIDER_DNS_NAME#g" ./example.properties
RUN sed -i -e "s#PROVIDER_DNS_PORT#$PROVIDER_DNS_PORT#g" ./example.properties

RUN cat ./example.properties

RUN ./gradlew build
CMD ["./gradlew", "--stacktrace", "build", "run"]