FROM tomcat:9.0-jdk21
WORKDIR /usr/local/tomcat/webapps/
COPY dist/AplikasiGajihKaryawan.war ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]