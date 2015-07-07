#!/bin/bash

docker build --tag=kaojo/wildfly-db ./wildfly-8.2/

docker build --tag=kaojo/postgres ./postgres-9.4/
