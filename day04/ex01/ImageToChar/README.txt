# Create directory
mkdir "target"

# Compile files to directory:
javac -d target src/java/edu/school21/printer/app/*.java src/java/edu/school21/printer/logic/*.java

# Copy resources
cp -r src/resources target

# Create jar file
jar -cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

# Run program:
java -jar target/images-to-chars-printer.jar . 0