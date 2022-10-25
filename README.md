# FeedAppProject


## Database setup:

* Installer postgreSQL (postgres app for mac)
    Port 5434 Username: Password:
    I postgres shell commandoer:

psql (postgres versjon)
    \l (liste opp alle databaser)
    CREATE DATABASE feedappdb; (lager database som heter feedappdb)
    \du (lister opp roller, her skal du finne din bruker)
    GRANT ALL PRIVILEGES ON DATABASE "feedappdb" TO din bruker;
    GRANT ALL PRIVILEGES ON DATABASE "feedappdb" TO postgres;
    \l (no skal feedappdb dukke opp i liste over databaser)
    \c feedappdb (koble til databasen)
    \d (liste opp relasjoner og tables)


## Postman:

USE THIS TEAM WORKSPACE IN POSTMAN INSTEAD: https://app.getpostman.com/join-team?invite_code=8140d1f833d79e1de3d6fc3416e68271&target_code=5e6b99c0970eb0c4cf77a071ba346c4f

Import this Postman JSON into your postman applicaton for API tests

## New branches:

git pull git checkout -b feature/b-1-looking-up-iot YOUR BRANCH NAME WITH TRELLO-CODE (B-1/F-1) git status git add . git commit -m "B-1: implementation of iot" git push --set-upstream origin feature/b-1-looking-up-iot
