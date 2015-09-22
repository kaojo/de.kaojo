#!/bin/bash

docker build --tag=kaojo/wildfly ./wildfly-9.1/

docker build --tag=kaojo/postgres ./postgres-9.4/

docker build --tag=kaojo/jboss-log ./jbossLog/

# build dev images
docker build --tag=kaojo/wildfly-debug ./dev/wildfly-debug/
