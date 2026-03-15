# Redis

* In memory database (keeps all data in-memory).
* Key-value store (stores data as key-value pairs).
* Single-threaded (all commands are executed sequentially).

## Usa Cases

* Caching
* Pub/Sub
* Message Queue
* Streaming

## Backup

Save data to disk:

```shell
bgsave
``` 


## Transaction

Redis is single-threaded, so all commands are executed sequentially. This means that transactions are atomic by default,
and there is no need for locks or other synchronization mechanisms.

```shell
multi
set keyA 2
set keyB 1
incr keyA
decr keyB
exec # execute transaction
```

```shell
multi
set keyA 2
set keyB 1
incr keyA
decr keyB
discard # discard transaction
```
