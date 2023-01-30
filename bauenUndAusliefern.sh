./gradlew docker build
docker login nexus.profil.data-experts.net:8445
./gradlew docker dockerTag dockerPush