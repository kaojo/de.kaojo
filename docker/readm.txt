###### How Start the kaojo-App with docker ######

### Docker Images bauen ###
> cd docker/wildfly-8.2
> sudo docker build --tag=kaojo/wildfly-db .
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
> select .......
