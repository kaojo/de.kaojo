FROM jboss/wildfly:8.2.0.Final

# Adding Environment Variables
ENV HOME=/opt/jboss 

ENV CONFIG=$HOME/config \
    JBOSS_HOME=$HOME/wildfly 

ENV JBOSS_MODULES=$JBOSS_HOME/modules \
    JBOSS_CONFIG=$JBOSS_HOME/standalone/configuration 

# Add Postgres Driver, add Scripts for configuration of Jboss Server
ADD commands.cli execute.sh postgresql-9.4-1201.jdbc4.jar $CONFIG/

# Run execute.sh --> configures Jboss
RUN $JBOSS_HOME/bin/add-user.sh admin admin --silent && \
    $CONFIG/execute.sh && \
    rm -rf $JBOSS_CONFIG/standalone_xml_history/current 

# expose postgres port
EXPOSE 5432
    
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
