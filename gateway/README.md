# Commands to Run Kong,Konga and PostgreSQL

### Creating Network
docker network create kong_net

### Instantiate PostgreSQL insatnce
docker run --shm-size=256m -e "PGDATA=/var/lib/postgresql/data/pgdata" -v posvol:/var/lib/postgresql/data --name kong_database --network=kong_net -e "POSTGRES_USER=kong" -e "POSTGRES_DB=kong" -e "POSTGRES_PASSWORD=kong" postgres:9.6

### Setup KONG database in PostgreSQL
docker pull kong
docker run --rm --network=kong_net -e "KONG_DATABASE=postgres" -e "KONG_PG_HOST=kong_database" -e "KONG_PG_PASSWORD=kong" kong kong migrations bootstrap

### Setup KONGA database in PostgreSQL
docker pull pantsel/konga
docker run --rm --network=kong_net pantsel/konga -c prepare -a postgres -u postgres://kong:kong@kong_database:5432/konga

### Instantiate KONG instance
docker run --name kong --network=kong_net -e "KONG_DATABASE=postgres" -e "KONG_PG_HOST=kong_database" -e "KONG_PG_PASSWORD=kong" -e "KONG_ADMIN_LISTEN=0.0.0.0:8001, 0.0.0.0:8444 ssl" -p 80:8000 -p 443:8443 kong

### Instantiate KONGA instance
docker run -p 1337:1337 --network kong_net -e "TOKEN_SECRET=key" -e "DB_ADAPTER=postgres" -e "DB_URI=postgres://kong:kong@kong_database:5432/konga" -e "NODE_ENV=production" --name konga pantsel/konga

