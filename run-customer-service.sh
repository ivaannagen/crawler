docker stop $(docker ps -a -q)
docker rmi -f customer-service
docker build -t customer-service .
docker run --env ENCRYPTION_PASSWORD=$1 -p 8285:8285 customer-service