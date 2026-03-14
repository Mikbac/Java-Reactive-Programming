# Redis

In memory database (keeps all data in-memory).

## Usa Cases

* Caching
* Pub/Sub
* Message Queue
* Streaming

## Commands

https://redis.io/docs/latest/commands/

### Start Redis:

```bash
docker compose -f docker/redis/docker-compose.yml up -d
docker exec -it redis bash
redis-cli
ping
```

### Set value:

```bash
set keyA valueB

set keyA "valueB valueB"

set user:1:name Alice
set user:1:email alice@mail.com
set user:2:name Bob
set user:2:email bob@mail.com

# expire key after 10 seconds
set keyA valueA EX 10
# get time to expire
ttl keyA
# extend expiration time
expire keyA 60

# set expiration time
set keyA valueA EXAT 1773361582

# update value and keep ttl
set keyA valueB KEEPTTL

# update ony if present
set keyA valueB XX

# update only if not present
set keyA valueB NX
```

### increase/decrease value

```bash
set keyA 1
# integer
incr keyA
incr keyA 10

decr keyA

# float
incrbyfloat keyB .2
```


### Get value:

```bash
get keyA
```

### Get all keys:

```bash
keys <pattern>
keys *
keys user*
```

```bash
scan 0
scan 0 MATCH user*
scan 0 MATCH user* COUNT 2
```

### Remove keys:

```bash
del keyA keyB
# remove all keys
flushdb
```


### Check if key exists

```bash
exists keyA
```
