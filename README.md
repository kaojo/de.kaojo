# de.kaojo
The goal of this Project is to build a dockerized Webapplication to use as a private Social Network. It should be secure and as enrcpted as possible.

The Project will be done in stages:

Stage 0: Set up a Basic Webchat
Stage 1: Set up persistence of messages, usermanagment and use encryption as much as possible
Stage 2: Pimp up Chat witch pictures and other media, persist them in a seconde database and make Galleries for Review
Stage 3: Add third database for relations between users, visualise connections
Stage 4: Generate Chatrooms depending on relations between users

TODO:

Basic funcitionalities:
* Implement automated SetUp of DB, DefaultRole, DefaultChatRoom (dockerizing might be tricky, maybe the only chane to do this is while building the postgres-docker-image)
* Finish Registration, activation email
* authentication via Jboss-Loginmodule needs to load the user into the context (maybe use a custom interceptor)
* Prohibit login from not activated accounts, maybe jboss login module config has to change to reflect this (custom interceptor ??? )
* UserCRUD, implement read update delete page
* create adminpage for user-administration
* Design a Logging concept (Annotation-based?, Static-method-based?)
* Choose 

Chat:
* implement all ChatManager methods
* Implement persisting Messages and ChatRooms to DB
* Implement Creation of new ChatRooms
* Implement chatRoom administration (create chatRoom-administration lightbox or separate page)
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


Security functionalities:
* enforce SSL
* encrypt private data in DB. (mostly chat messages for now)
* design and implement a concept to prevent "SQL-injection"
* implement a concept for "output-escaping"
* make the configuration of dockerimages secure, don't store passwords and usernames in the code, use for example environment variables or dialogs instead
* refactore docker image structure to reflect base, dev, live configuration

