FROM tomcat:9.0-jdk21

# Hapus aplikasi default
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR (Tomcat auto-deploy)
COPY dist/AplikasiGajihKaryawan.war /usr/local/tomcat/webapps/ROOT.war

# Setup database & import SQL langsung saat build
RUN apt-get update && apt-get install -y mysql-client

EXPOSE 8080
CMD ["catalina.sh", "run"]