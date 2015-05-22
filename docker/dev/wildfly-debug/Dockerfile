FROM kaojo/wildfly-db

ENV JAVA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n

EXPOSE 8787

CMD ["/opt/jboss/wildfly/bin/standalone.sh","-b","0.0.0.0","-bmanagement","0.0.0.0","--debug"]
