# Schedulers

Schedulers aren't parallel execution. Operations are always executed sequential.

| Scheduler                     | Description                                                                        | Typical Use Case                                                           |
|-------------------------------|------------------------------------------------------------------------------------|----------------------------------------------------------------------------|
| `Schedulers.boundedElastic()` | A thread pool with a bounded number of threads, expanding as needed within limits. | Ideal for blocking I/O operations (e.g. file or database access, network). |
| `Schedulers.parallel()`       | Uses a fixed-size pool equal to the number of CPU cores.                           | For CPU-bound or parallelized computations.                                |
| `Schedulers.single()`         | Uses a single reusable thread for all tasks.                                       | Suitable for lightweight, serialized work (e.g., event loops).             |
| `Schedulers.immediate()`      | Executes tasks immediately on the current thread.                                  | Useful for testing or when no thread switching is needed.                  |

Number of threads:

* boundedElastic - 10 * number of CPU
* parallel - numer of CPU
* single - 1

Operators:

* subscribeOn -- upstream
* publishOn -- downstream
