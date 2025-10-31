# Reactor

## Publisher

* Mono
    * emits 0 or 1 item
    * followed by an onComplete/onError
    * no stream
    * request -> response model
* Flux
    * emits 0,...,N items
    * followed by an onComplete/onError
    * stream of messages
    * producer emits too much data which consumer can not handle

## Mono

https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html

| Method                                   | Description                                                                                  | Example                                                         |
|------------------------------------------|----------------------------------------------------------------------------------------------|-----------------------------------------------------------------|
| `Mono.just(T value)`                     | Creates a `Mono` that emits the given non-null value and then completes.                     | `Mono.just("Hello")`                                            |
| `Mono.justOrEmpty(Optional<T> optional)` | Creates a `Mono` that emits the value if present, or completes empty if `Optional` is empty. | `Mono.justOrEmpty(Optional.of("Hi"))`                           |
| `Mono.empty()`                           | Creates a `Mono` that completes immediately without emitting any value.                      | `Mono.empty()`                                                  |
| `Mono.error(Throwable error)`            | Creates a `Mono` that terminates with the given error.                                       | `Mono.error(new RuntimeException("Oops"))`                      |
| `Mono.fromCallable(Callable<T>)`         | Creates a `Mono` that invokes a `Callable` when subscribed and emits its result.             | `Mono.fromCallable(() -> computeValue())`                       |
| `Mono.fromSupplier(Supplier<T>)`         | Similar to `fromCallable`, but uses a `Supplier`. Executed lazily upon subscription.         | `Mono.fromSupplier(() -> "lazy value")`                         |
| `Mono.fromRunnable(Runnable)`            | Executes a `Runnable` upon subscription and completes without emitting a value.              | `Mono.fromRunnable(() -> log.info("Done"))`                     |
| `Mono.fromFuture(CompletableFuture<T>)`  | Adapts a `CompletableFuture` into a `Mono`.                                                  | `Mono.fromFuture(CompletableFuture.supplyAsync(() -> "async"))` |
| `Mono.defer(Supplier<Mono<T>>)`          | Defers the creation of a `Mono` until subscription, useful for fresh values each time.       | `Mono.defer(() -> Mono.just(UUID.randomUUID().toString()))`     |
| `Mono.create(Consumer<MonoSink<T>>)`     | Programmatically create a `Mono` by pushing a single value, an error, or completing.         | `Mono.create(sink -> sink.success("value"))`                    |

## Flux

https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html

| Method                             | Description                                                              | Example                                                        |
|------------------------------------|--------------------------------------------------------------------------|----------------------------------------------------------------|
| `Flux.just(T...)`                  | Creates a Flux that emits the specified items and then completes.        | `Flux.just(1, 2, 3)`                                           |
| `Flux.fromIterable(Iterable<T>)`   | Creates a Flux from an `Iterable` (e.g., List, Set).                     | `Flux.fromIterable(List.of("a", "b"))`                         |
| `Flux.range(int start, int count)` | Emits a sequence of integers starting at `start`.                        | `Flux.range(5, 3) // emits 5,6,7`                              |
| `Flux.interval(Duration)`          | Emits increasing `long` values at a fixed interval.                      | `Flux.interval(Duration.ofSeconds(1))`                         |
| `Flux.empty()`                     | Creates a Flux that completes without emitting any items.                | `Flux.empty()`                                                 |
| `Flux.error(Throwable)`            | Creates a Flux that immediately signals an error.                        | `Flux.error(new RuntimeException("fail"))`                     |
| `Flux.map(Function)`               | Transforms each emitted item using the given function.                   | `Flux.just(1,2,3).map(i -> i * 2)`                             |
| `Flux.flatMap(Function)`           | Maps each item to a Publisher and flattens them into a single Flux.      | `Flux.just("a","b").flatMap(s -> Flux.fromArray(s.split("")))` |
| `Flux.filter(Predicate)`           | Filters items that match the predicate.                                  | `Flux.range(1, 10).filter(i -> i % 2 == 0)`                    |
| `Flux.take(long n)`                | Takes only the first `n` items from the sequence.                        | `Flux.range(1, 10).take(3)`                                    |
| `Flux.merge(Publisher...)`         | Merges multiple Flux sources concurrently.                               | `Flux.merge(flux1, flux2)`                                     |
| `Flux.zip(Publisher, ...)`         | Combines elements from multiple sources into tuples.                     | `Flux.zip(flux1, flux2, (a, b) -> a + b)`                      |
| `Flux.doOnNext(Consumer)`          | Performs a side-effect action when each item is emitted.                 | `Flux.just(1,2).doOnNext(System.out::println)`                 |
| `Flux.subscribe(...)`              | Subscribes to the Flux with optional handlers for data, error, complete. | `flux.subscribe(System.out::println)`                          |

## FluxSink

* designed for single subscriber
* thread safe
* delivers everything to subscriber safely
