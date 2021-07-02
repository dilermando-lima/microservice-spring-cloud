#!/bin/sh

# bash docker-compose-up-all-deps.sh

echo -e "\nDo you wish run 'docker-compose up' to all services in container/dependencies/docker-compose (Y/N)?"
read CONTINUE_DWNLD;

if [ "$CONTINUE_DWNLD" == "Y" ]; then

   docker-compose -f container/dependencies/docker-compose.yml down # --remove-orphans
   docker-compose -f container/dependencies/docker-compose.yml up -d # --build --force-recreate

    echo -e "\nAll services are up."
else
    echo -e "\nCommand is aborted."
fi

echo -e "\nPress any key to continue."
read 
