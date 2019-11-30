set -e

SCRIPT_DIR=$(dirname $0)
cd $SCRIPT_DIR

echo "> compiling"
./gradlew fatJar

echo "> generating native image"
native-image -J-Xmx5G --no-server -cp ./build/libs/example-all.jar

echo "> running"
./reflection-configura
