# Commands to Run Kong,Konga and PostgreSQL

### Important Points
* All commands are in detached mode, hence use " docker logs <container name> "to view status.
* " docker inspect <container_or_volume_or_network name> " to view properties for debugging.
* Optionally docker-compose.yml by navigating "docker-compose up" can be used. However, the "docker network create kong_net" still needs to be run before hand.
* You might need to add '&' at end of docker-compose up or use Ctrl+Z to exit without stopping.

### Creating Network
`docker network create kong_net`

### Instantiate PostgreSQL insatnce
``` docker run --shm-size=256m -e "PGDATA=/var/lib/postgresql/data/pgdata" -v posvol:/var/lib/postgresql/data --name kong_database --network=kong_net -e "POSTGRES_USER=kong" -e "POSTGRES_DB=kong" -e "POSTGRES_PASSWORD=kong" postgres:9.6```

### Setup KONG database in PostgreSQL
* docker pull kong
* docker run --rm --network=kong_net -e "KONG_DATABASE=postgres" -e "KONG_PG_HOST=kong_database" -e "KONG_PG_PASSWORD=kong" kong kong migrations bootstrap

### Setup KONGA database in PostgreSQL
* docker pull pantsel/konga
* docker run --rm --network=kong_net pantsel/konga -c prepare -a postgres -u postgres://kong:kong@kong_database:5432/konga

### Instantiate KONG instance
* docker run --name kong --network=kong_net -e "KONG_DATABASE=postgres" -e "KONG_PG_HOST=kong_database" -e "KONG_PG_PASSWORD=kong" -e "KONG_ADMIN_LISTEN=0.0.0.0:8001, 0.0.0.0:8444 ssl" -p 80:8000 -p 443:8443 kong

### Instantiate KONGA instance
* docker run -p 1337:1337 --network kong_net -e "TOKEN_SECRET=key" -e "DB_ADAPTER=postgres" -e "DB_URI=postgres://kong:kong@kong_database:5432/konga" -e "NODE_ENV=production" --name konga pantsel/konga

### Summary
1. 3 Ports mapped: 80 for http kong , 443 for https kong, 1337 for konga admin.
2. 1 volume: Postgres volume to store configurations for kong and konga.
3. To enter psql - docker exec -ti kong_database psql -U kong -d kong
4. -v "$PWD/my-postgres.conf":/etc/postgresql/postgresql.conf -c 'config_file=/etc/postgresql/postgresql.conf'

### Possible Modifications
1. Both kong and konga interact with Postgres as kong.
2. Add starting user database for konga in docker secrets. https://github.com/pantsel/konga/blob/master/docs/SEED_DEFAULT_DATA.md
3. Add support for multiple node mode in kong.
4. -v "$PWD/my-postgres.conf":/etc/postgresql/postgresql.conf -c 'config_file=/etc/postgresql/postgresql.conf'

### Post SetUp
1. Go to the <IP:1337> , then register as admin.
2. Login
3. Enter Name for connection and then http://kong:8001  (You might want to generate an API key for the Kong Admin endpoint).
4. Set up necessary services, upstreams, routes and plugins.
5. Add auth service as http://auth:8079/auth. Route is /auth.
6. Add admin service as http://admin:9090/user. Route is /admin.

### Documentation links:
1. https://docs.konghq.com
2. https://github.com/pantsel/konga
3. https://hub.docker.com/_/postgres
4. https://docs.docker.com
5. https://www.postgresql.org/docs/10/app-psql.html
