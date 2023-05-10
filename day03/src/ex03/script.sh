#!/bin/bash
if [ $# -ne 1 ]; then
  echo "Usage: $0 arg1"
  exit 1
fi
rm -rf download
# shellcheck disable=SC2035
javac -d bin *.java
java -classpath bin ex03.Program "$1"
