docker stop $(docker ps -a -q)
docker rmi -f crawler-service
docker build -t crawler-service .
docker run --env ENCRYPTION_PASSWORD=$1 -p 8285:8285 crawler-service