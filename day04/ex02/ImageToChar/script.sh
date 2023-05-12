#!/bin/bash

if [ -d target ]; then
#    echo "Directory exists."
    rm -rf target
    mkdir "target"
else
#    echo "Directory does not exist."
    mkdir "target"
fi


cp -r src/resources target
# shellcheck disable=SC2046
javac -d target -cp lib/jcommander-1.82.jar:lib/JCDP-4.0.2.jar $(find src/java -name '*.java')

jar -cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN