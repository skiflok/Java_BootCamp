#!/bin/bash

if [ -d target ]; then
#    echo "Directory exists."
    rm -rf target
    mkdir "target"
else
#    echo "Directory does not exist."
    mkdir "target"
fi

# shellcheck disable=SC2046
javac -d target -cp src/java $(find src/java -name '*.java')

cp -r src/resources target

jar -cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

java -jar target/images-to-chars-printer.jar "$@"