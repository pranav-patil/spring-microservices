FROM java:8-jre

ADD ["build/libs/discovery-service-0.0.1-SNAPSHOT.jar", "app.jar"]
EXPOSE 8302 8787
ENV JAVA_OPTS="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8787,suspend=n"
RUN sh -c 'touch /app.jar'
HEALTHCHECK CMD curl -f http://localhost:8302/actuator/health || exit 1
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=production -jar /app.jar" ]

