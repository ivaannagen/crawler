docker stop $(docker ps -a -q)
docker rmi -f character-service
docker build -t character-service .
docker run --env ENCRYPTION_PASSWORD=$1 -p 8080:8080 character-service