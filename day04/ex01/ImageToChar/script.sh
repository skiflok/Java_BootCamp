#!/bin/bash

if [ -d target ]; then
    echo "Directory exists."
    rm -rf target
    mkdir "target"
else
    echo "Directory does not exist."
    mkdir "target"
fi


javac -d target src/java/edu/school21/printer/app/*.java src/java/edu/school21/printer/logic/*.java

java -classpath target edu.school21.printer.app.Main "$@"

cp -r src/resources target

jar -cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

java -jar target/images-to-chars-printer.jar . 0