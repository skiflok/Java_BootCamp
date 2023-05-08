#!/bin/bash
if [ $# -ne 2 ]; then
  echo "Usage: $0 arg2"
  exit 1
fi
# shellcheck disable=SC2035
javac -d bin *.java
java -classpath bin ex02.Program "$1" "$2"
