# Commands to Run Logstash, ELasticSearch and Kibana

### Creating Network
docker network create kong_net

### Instantiate ELasticSearch insatnce
docker-machine ssh | cat /proc/sys/vm/max_map_count | sudo sysctl -w vm.max_map_count=262144  
docker run -p 9200:9200 -p 9300:9300 --ulimit nofile=65535:65535 -v elavol:/usr/share/elasticsearch/data -e "discovery.type=single-node" -e "bootstrap.memory_lock=true" --ulimit memlock=-1:-1 -e ES_JAVA_OPTS="-Xms512m -Xmx512m" --name elasticsearch --network="kong_net" docker.elastic.co/elasticsearch/elasticsearch:7.7.0

### Instantiate Kibana instance
docker run --name kibana --link elasticsearch:elasticsearch --network=”kong_net” -v ./kibvol:/usr/share/kibana/config/ -p 5601:5601 --name kibana docker.elastic.co/kibana/kibana:7.7.0

### Instantiate Logstash instance
docker run --name logstash -p 9600:9600 -p 8080:8080 -v /logvol/:/usr/share/logstash/pipeline/ --network="kong_net" docker.elastic.co/logstash/logstash:7.7.0

### Summary

1. 5 Ports exposed: 9200 , 9300 in elasticsearch ( to be removed), 9600 and 8080 in logstash(to be removed), 5601 kibana dashboard
2. 1 volume, 2 bind: Elastic volume to store data, kibana and logstash binds to pass properties

### Possible Modifications
1. Making docker-compose.yml
2. Adding login for kibana
3. Seperating networks to isolate kibana and elasticsearch

### Documentation links:
1. https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html 
2. https://www.elastic.co/guide/en/logstash/current/index.html
3. https://www.elastic.co/guide/en/kibana/current/index.html