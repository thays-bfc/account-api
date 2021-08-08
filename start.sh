echo "Build with Gradle"
docker run --rm -u gradle -v "$PWD":/home/gradle/project -w /home/gradle/project gradle:jdk11 gradle build
echo "Up application"
docker-compose up