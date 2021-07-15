#!/bin/sh

# bash mvn-install-all.sh

NAMES_REPOSITORY=(
    "arch-deps"
	"arch-commons"
    "arch-amqp"
	"config-server"
    "ms-form-create"
)

echo -e "\nListing all projects:"

for REPOSITORY in ${NAMES_REPOSITORY[*]}; do
    echo -e " - $REPOSITORY"
done

echo -e "\nDo you wish run 'mvn clean install' to all projects (Y/N)?"
read CONTINUE_DWNLD;

if [ "$CONTINUE_DWNLD" == "Y" ] || [ "$CONTINUE_DWNLD" == "y" ]; then

    for REPOSITORY in ${NAMES_REPOSITORY[*]}; do
        echo -e "\nrunning project $REPOSITORY\n"
        mvn -f $REPOSITORY/pom.xml clean install 
    done

    echo -e "\nAll project done."
else
    echo -e "\nCommand is aborted."
fi

echo -e "\nPress any key to continue."
read 
