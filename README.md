# de.kaojo
My Java EE learning project

This Project is meant to teach me some lessons in Setting Up a webserver.

The Project will be done in stages:

Stage 0: Set up a Basic Webchat
Stage 1: Set up persistence of messages, usermanagment and use encryption as much as possible
Stage 2: Pimp up Chat witch pictures and other media, persist them in a seconde database and make Galleries for Review
Stage 3: Add third database for relations between users, visualise connections
Stage 4: Generate Chatrooms depending on relations between users



INSERT INTO roles VALUES ( '1', '1', 'user');


TODO:


Basic funcitionalities:
* Implement automated SetUp of DB, DefaultRole, DefaultChatRoom
* Finish Registration, validate Input, set email
* UserCRUD, implement read update delete page
* create adminpage for userAddministration


Chat:
* Implement persisting Messages and ChatRooms to DB
* Implement Creation of new ChatRooms
* Implement Invitation to ChatRooms
* Implement Loading of ChatRooms and Messagehistory on visiting chat.xhtml
* Implement "Loading older Messages"
* Implement Image messages

Image DB:
* Chooose a suitible DB
* dockerize it
* Integrate it
* make Galery page
* connect to Chat

Graph DB:
* Design Relationship model between Accounts
* Choose suitable DB
* dockerize it
* integrate it
* make relationship page crud stuff
* implement automated chatRoom generation

