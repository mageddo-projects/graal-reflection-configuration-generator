SCRIPT_DIR=$(dirname $0)
./gradlew example:fatJar
native-image -J-Xmx5G --no-server -cp "${SCRIPT_DIR}/build/libs/example-all.jar"
