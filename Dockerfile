FROM openjdk:11
ADD ./target/amy-1.1.1.RELEASE.jar app.jar
ENTRYPOINT java ${JAVA_OPTS}  -jar app.jar