FROM jboss/wildfly:9.0.1.Final

# Adding Environment Variables
ENV HOME=/opt/jboss 

ENV CONFIG=$HOME/config \
    JBOSS_HOME=$HOME/wildfly 

ENV JBOSS_MODULES=$JBOSS_HOME/modules \
    JBOSS_CONFIG=$JBOSS_HOME/standalone/configuration 
    
# Configure add admin user to server
RUN $JBOSS_HOME/bin/add-user.sh admin admin --silent

# Add Postgres Driver, add Scripts for configuration of Jboss Server
COPY commands.cli execute.sh postgresql-9.4-1201.jdbc4.jar $CONFIG/

# Add custome welcome-app context
COPY index.html $JBOSS_HOME/welcome-content/

# Run execute.sh --> configures Jboss
RUN $CONFIG/execute.sh && \
    rm -rf $JBOSS_CONFIG/standalone_xml_history/current 

ENTRYPOINT ["/opt/jboss/wildfly/bin/standalone.sh"]

VOLUME /logs/jboss

CMD ["-b","0.0.0.0","-bmanagement","0.0.0.0"]
