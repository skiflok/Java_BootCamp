# Create directory
mkdir "target"

# Compile files to directory:
javac -d target -cp src/java $(find src/java -name '*.java')

# Copy resources
cp -r src/resources target

# Create jar file
jar -cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

# Run program:
java -jar target/images-to-chars-printer.jar . 0