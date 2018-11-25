FROM java:8-jre

ADD ["build/libs/authorization-service-0.0.1-SNAPSHOT.jar", "app.jar"]
EXPOSE 1000 8787
ENV JAVA_OPTS="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8787,suspend=n"
RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=production -jar /app.jar" ]

