FROM  openjdk:11-jre-slim
EXPOSE 8079
VOLUME /tmp
ADD target/authApp-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]