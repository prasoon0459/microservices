# Commands to Run Logstash, ELasticsearch and Kibana

### Important Points
* All commands are in detached mode, hence use `docker logs <container name>` to view status.
* `docker inspect <container_or_volume_or_network name>` to view properties for debugging.

### Creating Network
```
docker network create kong_net
```
### Instantiate Elasticsearch instance
* Check system config by following steps : 1. `docker-machine ssh 2. cat /proc/sys/vm/max_map_count` .  If value is less that 262144, run `sudo sysctl -w vm.max_map_count=262144`
```
docker run -d --ulimit nofile=65535:65535 -v elavol:/usr/share/elasticsearch/data -e "discovery.type=single-node" -e "bootstrap.memory_lock=true" --ulimit memlock=-1:-1 -e ES_JAVA_OPTS="-Xms512m -Xmx512m" --name elasticsearch --network="kong_net" docker.elastic.co/elasticsearch/elasticsearch:7.7.0
```
* ports 9200 and 9300 are not exposed

### Instantiate Kibana instance
```
docker run -d --name kibana --link elasticsearch:elasticsearch --network="kong_net" -v kibvol:/usr/share/kibana/config/ -p 5601:5601 --name kibana docker.elastic.co/kibana/kibana:7.7.0
```
### Instantiate Logstash instance
```
docker run -d --name logstash -v logvol:/usr/share/logstash/pipeline/ -v logstash_persist:/usr/share/logstash/data/ --network="kong_net" docker.elastic.co/logstash/logstash:7.7.0
```
* Ports 8000(http input) 9600(output) are not exposed.

### Summary
1. 1 Ports exposed: 5601 kibana dashboard
2. 2 volume, 2 bind:1 Elastic volume to store data, 1 kibana bind for config, 1 logstash bind for config, 1 logstash volume for data buffering.

### Possible Modifications
1. Making docker-compose.yml
2. Adding login for kibana
3. Seperating networks to isolate kibana and elasticsearch

### Documentation links:
1. https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html 
2. https://www.elastic.co/guide/en/logstash/current/index.html
3. https://www.elastic.co/guide/en/kibana/current/index.html
