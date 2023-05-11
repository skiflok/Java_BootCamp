#!/bin/bash
if [ $# -ne 3 ]; then
  echo "Usage: $0 arg3"
  exit 1
fi
#rm -rf download
# shellcheck disable=SC2035
javac -d target src/java/edu/school21/printer/app/*.java src/java/edu/school21/printer/logic/*.java
#java -classpath target edu.school21.printer.app.Main . 0 ../it.bmp
# shellcheck disable=SC2086
java -classpath target edu.school21.printer.app.Main "$1" "$2" $3