# Combining publishers

## startWith

In the Reactor framework, Flux.startsWith allows you to prepend values or another Publisher to the beginning of a Flux.
This means that the specified items will be emitted first, before any elements from the original Flux, effectively
letting you “prefix” the stream with additional data. It’s useful when you want to ensure certain elements appear at the
start of a reactive sequence.

