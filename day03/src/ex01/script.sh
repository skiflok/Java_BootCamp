#!/bin/bash
if [ $# -ne 1 ]; then
  echo "Usage: $0 arg1"
  exit 1
fi
# shellcheck disable=SC2035
javac -d bin *.java
java -classpath bin ex01.Program "$1"