#!/bin/bash

echo "Installing..."
mvn -T 1C install -DskipTests || exit 1
echo "Populating database..."
java -cp "$(mvn -q dependency:build-classpath -Dmdep.outputFile=/dev/stdout)":target/classes:target/test-classes edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator
echo "Done!"