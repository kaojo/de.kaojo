#!/bin/bash

docker build --tag=kaojo/wildfly-db ./wildfly-8.2/

sudo docker build --tag=kaojo/postgres ./postgres-9.4/
