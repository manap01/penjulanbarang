FROM tomcat:9.0-jdk21

# Hapus folder ROOT lama
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy WAR & extract manual
COPY dist/AplikasiGajihKaryawan.war /usr/local/tomcat/webapps/ROOT.war
RUN cd /usr/local/tomcat/webapps && \
    jar -xf ROOT.war && \
    mv ROOT.war ROOT.war.bak

EXPOSE 8080
CMD ["catalina.sh", "run"]