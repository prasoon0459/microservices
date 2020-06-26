# Commands to Admin Service and Postgres

### Creating Network
```
docker network create kong_net
```
### Instantiate PostgreSQL insatnce
```
docker run --shm-size=256m --network=kong_net -e "PGDATA=/var/lib/postgresql/data/pgdata" -v uservol:/var/lib/postgresql/data -e "POSTGRES_USER=postgres" -e "POSTGRES_DB=testdb" -e "POSTGRES_PASSWORD=password" --name userdb -d postgres:9.6
```
### Instantiate Admin instance
```
docker run -e JAVA_OPTS=-Djava.security.egd=file:/dev/./urandom --network=kong_net --name admin -d aayush21/admin:beta
```
### Endpoints
1. Get /user - List of users in body. Exception on error.
2. Post /user - Request with 1 user JSON in body, returns "Already Exists" or "Added" in body on success. Exception on error.
3. Put /user - Request with 1 user JSON in body, returns "Does not Exist" or "Updates" in body on success. Exception on error.
4. Delete /user/{username} - Request with uid in pathvariable, returns "Does not Exist" or "Deleted" in body on success. Exception on error.

### Sample Request Body
{"username":"aayush-ag21",
 "name":"aayush",
 "password":"notmypassword",
 "phone":"1234567890",
 "valid":true,
 "roles":["ROLE_ADMIN","ROLE_USER"]
}

### Summary
1. NO Ports exposed. Internal - 9090 for admin, 5432 for postgres
2. 1 volume: Postgres volume to store userdb
3. To enter psql - docker exec -ti userdb psql -U postgres -d testdb
4. `-v "$PWD/my-postgres.conf":/etc/postgresql/postgresql.conf -c 'config_file=/etc/postgresql/postgresql.conf'`

### Possible Modifications
1. Making docker-compose.yml
2. Changing JDK Version
3. Logging for Admin service
4. Endpoint logic

### Documentation links:
1. https://hub.docker.com/_/postgres
2. https://docs.docker.com
3. https://www.postgresql.org/docs/10/app-psql.html
