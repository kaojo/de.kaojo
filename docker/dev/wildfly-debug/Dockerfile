FROM kaojo/wildfly

ENV JAVA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n
CMD ["-b","0.0.0.0","-bmanagement","0.0.0.0","--debug"]
