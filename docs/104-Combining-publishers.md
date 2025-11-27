# Combining publishers

## startWith

In the Reactor framework, Flux.startsWith allows you to prepend values or another Publisher to the beginning of a Flux.
This means that the specified items will be emitted first, before any elements from the original Flux, effectively
letting you “prefix” the stream with additional data. It’s useful when you want to ensure certain elements appear at the
start of a reactive sequence.

## concatWith

When you call flux1.concatWith(flux2), Reactor first subscribes to flux1 and emits all its elements. Only after flux1
completes normally does it subscribe to flux2 and start emitting its elements.
The key point is that concatWith preserves order and does not interleave emissions—the second publisher waits until the
first one finishes. It also stops the sequence entirely if the first publisher ends with an error.

## merge

Merge in Project Reactor combines multiple reactive sources concurrently, meaning their emissions can interleave.
When you call something like Flux.merge(flux1, flux2), Reactor subscribes to both publishers at the same time and emits
items as soon as they arrive, regardless of which source they come from.
Unlike concat, merge does not preserve order—the output sequence reflects the timing of each publisher. If any merged
source errors, the whole merged flux will error immediately.

## zip

Zip in Project Reactor combines multiple reactive sources by pairing their elements together based on their position in
each sequence.
When you call Flux.zip(flux1, flux2), Reactor waits until each source has emitted one item, then applies a zipper
function (or forms a tuple) to produce a single combined element.
The zipped flux finishes when any of the sources completes, because pairing is no longer possible.
This operator ensures strict alignment of items rather than ordering by time.

## flatMap

FlatMap in Project Reactor transforms each emitted item into a new reactive source and then merges all these inner
publishers concurrently.
For every element coming from the original Flux/Mono, flatMap applies a mapping function that returns another Flux/Mono,
and Reactor subscribes to all of them in parallel.
Because the results are merged, emissions can interleave and the final output order is not guaranteed.
This makes flatMap very powerful for asynchronous, non-blocking operations such as calling multiple services at the same
time.

## concatMap

ConcatMap in Project Reactor transforms each emitted item into a new reactive source—just like flatMap—but processes
these inner publishers sequentially.
For every element, Reactor waits for the entire inner publisher to complete before moving on to the next one.
This guarantees that the output order matches the input order, with no interleaving of emissions.

## collectList

CollectList in Project Reactor gathers all elements emitted by a Flux into a single List, and returns it wrapped in a
Mono<List<T>>.

## then

Then in Project Reactor lets you ignore the emissions of a publisher and continue with another reactive sequence only
after the first one completes.
