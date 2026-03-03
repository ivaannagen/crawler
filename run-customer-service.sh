docker stop $(docker ps -a -q)
docker rmi -f customer-service
docker build -t customer-service .
docker run -p 8285:8285 customer-service