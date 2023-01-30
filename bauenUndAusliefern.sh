./gradlew docker build
docker login nexus-nb.data-experts.net:8445
./gradlew docker dockerTag dockerPush