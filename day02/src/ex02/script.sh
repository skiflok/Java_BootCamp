#!/bin/bash
if [ $# -ne 1 ]; then
  echo "Usage: $0 arg1"
  exit 1
fi
# shellcheck disable=SC2035
javac -d bin *.java command/*.java
java -classpath bin ex02.Program "$1"