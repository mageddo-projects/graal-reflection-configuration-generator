set -e

SCRIPT_DIR=$(dirname $0)
cd $SCRIPT_DIR

./gradlew fatJar
native-image -J-Xmx5G --no-server -cp ./build/libs/example-all.jar
