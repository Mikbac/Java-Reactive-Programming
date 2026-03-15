# Commands

Redis keeps all values as strings, but it provides commands to manipulate them as integers or floats.

https://redis.io/docs/latest/commands/

### Start Redis:

```shell
docker compose -f docker/redis/docker-compose.yml up -d
docker exec -it redis shell
redis-cli
ping
```

### Set value:

```shell
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

```shell
set keyA 1
# integer
incr keyA
incr keyA 10

decr keyA

# float
incrbyfloat keyB .2
```

### Get value:

```shell
get keyA
```

### Get all keys:

```shell
keys <pattern>
keys *
keys user*
```

```shell
scan 0
scan 0 MATCH user*
scan 0 MATCH user* COUNT 2
```

### Remove keys:

```shell
del keyA keyB
# remove all keys
flushdb
```

### Check if key exists

```shell
exists keyA
```

## Hash

A group of related key-value pairs. It is useful to represent objects.

Insert:

```shell
hset user:1 name Alice email a@mail.com position developer
hset user:2 name Bob email b@mail.com position designer
```

check type:

```shell
type user:1
# hash
```

Get value:

```shell
hget user:1 name
# "Alice"
 hgetall user:1
# 1) "name"
# 2) "Alice"
# 3) "email"
# 4) "a@mail.com"
# 5) "position"
# 6) "developer"
```

Get keys:

```shell
hkeys user:1
# 1) "name"    
# 2) "email"   
# 3) "position"
```

Remove key:

```shell
hdel user:1 name
```

## List

Redis list can be used as a queue or stack.

```shell
rpush mylist a b c
keys *
# 1) "mylist"
type mylist
# list
```

range:

```shell
lrange mylist 0 1
# 1) "a"
# 2) "b"
```

pop from left:

```shell
lpop mylist
# "a"
lpop mylist
# "b"
llen mylist
# 1
```

queue example:

```shell
# producer
rpush myqueue task1 task2 task3
rpush myqueue task4 task5
# consumer
lpop myqueue
# "task1"
lpop myqueue 3
# 1) "task2"
# 2) "task3"
# 3) "task4"
```

stack example:

```shell
# producer
rpush mystack task1 task2 task3
rpush mystack task4 task5
# consumer
rpop mystack
# "task5"
rpop mystack 3
# 1) "task4"
# 2) "task3"
# 3) "task2"
```

## Set

An unordered collection of unique strings.

```shell
sadd myset a b c
sadd myset c d e
smembers myset
# 1) "a"
# 2) "b"
# 3) "c"
# 4) "d"
# 5) "e"
scard myset
# 5
srem myset c
scard myset
# 4
sismember myset a
# 1
sismember myset c
# 0
spop myset # pop random element
# "a"
```

intersection:

```shell
sadd set:A a b c
sadd set:B c d e
sadd set:C c e f
sinter set:A set:B
# 1) "c"
sinter set:A set:C
# 1) "c"
sinter set:B set:C
# 1) "c"
# 2) "e"
sinter set:A set:B set:C
# 1) "c"
```

union:

```shell
sunion set:A set:B
# 1) "c"
# 2) "d"
# 3) "e"
# 4) "a"
# 5) "b"
```

difference:

```shell
sdiff set:A set:B
# 1) "a"
# 2) "b"
```

intersection store:

```shell
sinterstore set:AB set:A set:B
smembers set:AB
# 1) "c"
```

## Sorted Set

A sorted set is a collection of unique strings ordered by a score.

```shell
zadd myzset 2 b 1 a 5 e 3 c 4 d
zrange myzset 0 -1
# 1) "a"
# 2) "b"
# 3) "c"
# 4) "d"
# 5) "e"
zrange myzset 0 -1 withscores
# 1) "a"
# 2) "1"
# 3) "b"
# 4) "2"
# 5) "c"
# 6) "3"
# 7) "d"
# 8) "4"
# 9) "e"
# 10) "5"
zrange myzset -1 -1
# 1) "e"
zrange myzset 0 0 rev
# 1) "e"
zrank myzset b
# 1
zrevrank myzset b
# 3
```

increase score:

```shell
zincrby myzset 2 c
```

pop element with the lowest score:

```shell
zpopmin myzset
# 1) "a"
# 2) "1"
```

pop element with the highest score:

```shell
zpopmax myzset
# 1) "e"
# 2) "5"
```
