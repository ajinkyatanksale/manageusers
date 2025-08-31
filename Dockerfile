
FROM tomcat:10.1-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/manageusers.war /usr/local/tomcat/webapps/ROOT.war

COPY properties/application-docker.properties /opt/config/application.properties

EXPOSE 8080

CMD ["catalina.sh", "run", "-Dspring.config.location=file:/opt/config/"]
