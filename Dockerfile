FROM tomcat:latest

COPY target/com6bank.war /usr/local/tomcat/webapps/com6bank.war
COPY web.xml /usr/local/tomcat/conf/


CMD ["catalina.sh", "run"]
