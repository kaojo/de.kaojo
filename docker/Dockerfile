FROM jboss/wildfly

ENV HOME /opt/jboss
ENV CONFIG $HOME/config
ENV JBOSS_HOME $HOME/wildfly
ENV JBOSS_MODULES $JBOSS_HOME/modules
ENV JBOSS_CONFIG $JBOSS_HOME/standalone/configuration

ADD commands.cli execute.sh postgresql-9.4-1201.jdbc4.jar $CONFIG/

RUN $JBOSS_HOME/bin/add-user.sh admin 0000 --silent && \
    $CONFIG/execute.sh && \
    rm -rf $JBOSS_CONFIG/standalone_xml_history/current
    
EXPOSE 5432
    
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]