#!/bin/bash
if [ $# -ne 1 ]; then
  echo "Usage: $0 arg2"
  exit 1
fi
# shellcheck disable=SC2035
javac -d bin *.java
java -classpath bin ex03.Program "$1"
