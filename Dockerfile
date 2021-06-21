FROM java:11
ADD ./target/amy-1.0.0.RELEASE.jar app.jar
ENTRYPOINT java ${JAVA_OPTS}  -jar app.jar