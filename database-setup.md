# Comandos para iniciar bancos de dados

## MySQL
docker run --name mysql_space -p 3306:3306 -e MYSQL_ROOT_PASSWORD=educacional01 -e MYSQL_DATABASE=space_mission -e MYSQL_USER=aldrin -e MYSQL_PASSWORD=educacional01 -d mysql:latest --default-authentication-plugin=mysql_native_password

## Redis
docker run --name redis_space -p 6379:6379 -d redis:latest redis-server --appendonly yes --requirepass educacional01

## Parar containers
docker stop mysql_space redis_space

## Remover containers
docker rm mysql_space redis_space