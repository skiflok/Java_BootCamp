#1 Create directory:
    mkdir "12target" "12lib"

#2 Download libraries:
    curl https://repo1.maven.org/maven2/com/beust/jcommander/1.82/jcommander-1.82.jar -o lib/jcommander-1.82.jar
    curl https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.2/JCDP-4.0.2.jar -o lib/JCDP-4.0.2.jar

#3 Extract libraries to 'target':
    cd target
    jar -xf ../lib/JCDP-4.0.2.jar
    jar -xf ../lib/jcommander-1.82.jar
    rm -rf META-INF
    cd ..

#4 Copy resources
    cp -r src/resources target

#5 Compile files to directory:
    javac -d target -cp target $(find src/java -name '*.java')

#6 Create jar file
    jar -cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

#7 Run program:
    java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN