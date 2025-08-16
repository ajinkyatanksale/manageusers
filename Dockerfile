
FROM tomcat:10.1-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/manageusers.war /usr/local/tomcat/webapps/manageusers.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
