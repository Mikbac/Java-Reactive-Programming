# Backpressure

Backpressure is a flow control mechanism in Reactive Streams (and therefore in Project Reactor) that prevents a fast
data producer from overwhelming a slow consumer.

It’s essentially a way to balance data flow between components in a reactive pipeline.

```
      +----------------+
      |    Publisher   |
      | (fast emitter) |
      +-------+--------+
              |
              | emits elements
              v
    +---------------------+
    |     Backpressure    |
    |       Queue /       |
    |       Buffer        |
    +---------+-----------+
              |
              | delivers elements when requested
              v
      +----------------+
      |   Subscriber   |
      | (slow consumer)|
      +----------------+
```

When queue is full producer stops producing and starts producing when 75% queue is empty.

## Overflow strategy

* buffer in memory
* throw error
* drop new items when queue is full
* keep 1 latest item when queue is full
