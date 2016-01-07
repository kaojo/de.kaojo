###### How Start the kaojo-App with docker ######

### Docker Images bauen ###
> cd docker/wildfly-8.2
> sudo docker build --tag=kaojo/wildfly .
> cd ../postgres-9.4
> sudo docker build --tag=kaojo/postgres .

### Starten der App ###
> cd docker
> sudo docker-compose up

### Verbinden mit laufender Docker Postgres Datenbankinstance ###

# Welche docker Instanzen laufen #
> sudo docker ps

# Verbinden mit docker Instanz #
> sudo docker exec -it <docker_container_name> bash

# SQL Abfragen abschicken #
> su -l postgres
> psql kaojo
> select ......

# Connection from host to postgres docker, get the exposed port with docker ps
psql -h localhost -p <exposedPort> -d kaojo -U kaojo --password

# Create Liquibase ChangeLog from project root directory
liquibase --driver=org.postgresql.Driver
       --classpath=./config/postgresql-9.4-1201.jdbc4.jar
       --changeLogFile=db-changelog.xml
       --url="jdbc:postgresql://localhost:5431/kaojo"
       --username=kaojo
       --password=admin
       generateChangeLog

### Deployen auf den JBoss ###
> mvn clean wildfly:deploy


