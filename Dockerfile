FROM tomcat:9.0-jdk21

# Hapus aplikasi default
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy & extract WAR manual
COPY dist/AplikasiGajihKaryawan.war /tmp/app.war
RUN mkdir -p /usr/local/tomcat/webapps/ROOT && \
    cd /usr/local/tomcat/webapps/ROOT && \
    jar -xf /tmp/app.war && \
    rm /tmp/app.war

EXPOSE 8080
CMD ["catalina.sh", "run"]