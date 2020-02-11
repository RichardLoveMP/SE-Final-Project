#!/bin/bash

echo "Please make sure you are running this script with bash version higher than 4.0!"
echo ""
yes="y"

echo -n "Would you like to run maven test? (y/n): "
read maventest

if [ "${maventest,,}" == "$yes" ]; then
    echo "Running maven test"
    mvn -T 1C verify
else
    echo "Skipping maven test"
fi;

echo -n "Would you like to install iTrust to tomcat9? (y/n): "
read mvninstall

if [ "${mvninstall,,}" == "$yes" ]; then
    echo "Installing iTrust to tomcat9"
    mvn -T 1C install -DskipTests

    echo ""
    echo -n "Populating database data to iTrust..."
    java -cp "$(mvn -q dependency:build-classpath -Dmdep.outputFile=/dev/stdout)":target/classes:target/test-classes edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator
    echo "Done!"
else
    echo "Skipping installing to tomcat"
fi;
