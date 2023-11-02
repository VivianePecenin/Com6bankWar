FROM tomcat:latest

COPY /target/com6bank.war /usr/local/tomcat/webapps/
COPY web.xml /usr/local/tomcat/conf

CMD ["catalina.sh", "run"]
