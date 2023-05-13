#!/bin/bash

if [ -d target ]; then
    echo "Directory 'target' exists. delete directory"
    rm -rf target
    echo "Create directory 'target'"
    mkdir "target"
else
    echo "Directory does not exist. Create directory 'target'"
    mkdir "target"
fi

if [ -d lib ]; then
    echo "Directory 'lib' exists. delete directory"
    rm -rf lib
    echo "Create directory 'lib'"
    mkdir "lib"
else
    echo "Directory does not exist. Create directory lib"
    mkdir "lib"
fi

echo "Download libraries"
curl https://repo1.maven.org/maven2/com/beust/jcommander/1.82/jcommander-1.82.jar -o lib/jcommander-1.82.jar
curl https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.2/JCDP-4.0.2.jar -o lib/JCDP-4.0.2.jar

echo "Extract libraries to 'target'"
# shellcheck disable=SC2164
cd target
jar -xf ../lib/JCDP-4.0.2.jar
jar -xf ../lib/jcommander-1.82.jar
rm -rf META-INF
# shellcheck disable=SC2103
cd ..

cp -r src/resources target

echo "Compile files"
javac -d target -cp target $(find src/java -name '*.java')

echo "Create jar file"
jar -cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

echo "Run program"
java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN

# shellcheck disable=SC2046
#javac -d target -cp "lib/*" $(find src/java -name '*.java')

#java -cp target/images-to-chars-printer.jar:lib/* edu.school21.printer.app.Main --white=RED --black=GREEN
