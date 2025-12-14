# Sinks

| Sink               | Publisher | Number of Subscribers | Description                              |
|--------------------|-----------|-----------------------|------------------------------------------|
| `one`              | `Mono`    | N                     |                                          |
| `many - unicast`   | `Flux`    | 1                     |                                          |
| `many - multicast` | `Flux`    | N                     | Does't reply values to late subscribers. |
| `many - replay`    | `Flux`    | N                     | Replay all values to late subscribers.   |
