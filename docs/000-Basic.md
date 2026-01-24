# Basics

## Process

* An instance of a computer program, it includes code, resources, etc.

## Thread

* Part of process, process can contain on or more threads.
* Threads within a process can share the memory space.

## Thread scheduler

* A thread scheduler is a part of an operating system's kernel or a user-level thread library that manages the execution
  of threads, deciding which thread runs on a CPU core and for how long.

## Java Platform Thread

* 1 Java (PPlatform) Thread == 1 OS Thread

## Heap vs Stack

* Heap Memory:
    * Shared among all threads.
    * Managed by Garbage Collector.
    * Stores all objects and JRE classes.
* Stack Memory:
  Each thread has its own stack. It stores:
    * Method calls
    * Local variables
    * Object references (not objects themselves)

## IO models

* sync+blocking
* async
* non-blocking
* non-blocking+async

## Communication Patterns

* request -> response
* request -> streaming response
* streaming request -> response
* bidirectional streaming

## Streaming advantages

* connection is established only once
* reduction in network traffic
* no need to wait for the previous request to complete
