# Entry 12: Deep Spring

## Spring Data JDBC & the Repository Pattern

### Spring Data JDBC

Spring Data JDBC is a simple layer on top of JDBC. It maps database rows to Java objects **without the extra complexity of JPA/Hibernate**, such as sessions, caching, proxies, and lazy loading.

* **Immutable entities:** Entities are usually immutable, using a `record` or `final` fields. Instead of changing an object, you create a new instance when saving changes.

* **Optimistic locking:** An entity has a `@Version` field. When saving, Spring checks that the version hasn't changed since it was read. If someone else updated it first, the save fails rather than overwriting their changes.

* **`@DataJdbcTest`:** A focused test that loads only the database-related parts of the application, such as repositories and the test database. This makes it faster and more focused than `@SpringBootTest`.


### Repository Pattern

An abstraction that lets the rest of the application access data
without knowing where it comes from: a database, a cache, or somewhere else. Business logic depends on a `Repository` interface; the
concrete implementation (JDBC, JPA, in-memory) can be swapped without
affecting anything.

## Reactive Spring

Traditional Spring MVC is blocking: one thread per request, and that
thread sits idle while it waits on I/O operations.

Reactive programming instead treats data as a stream that gets processed and propagated as it arrives. You subscribe to that stream and react to each item rather than blocking until everything is ready.

**Project Reactor** is the library behind Spring's reactive stack, for
building asynchronous, non-blocking applications on the producer/consumer paradigm.

- **Backpressure**: the consumer controls how much data it receives
  from the producer at once, instead of being flooded faster than it
  can process.

- The reactive stack runs on **Netty** instead of Tomcat: Netty's
  event-loop model handles many connections on a small pool of threads, a better fit for non-blocking I/O than one thread per request.

**WebFlux** is Spring's web framework built on the Reactive Streams
API, the same annotations as MVC (`@RestController`, `@GetMapping`)
but returning `Mono<T>` (0 or 1 item) or `Flux<T>` (0 to N items)
instead of a plain object, so the whole request handling pipeline stays non-blocking end to end.

## API Gateway

Usually the single entry point into a system built from multiple
services. Since every request passes through it, it's a natural
place to implement cross-cutting concerns once instead of repeating
them in every service: security (authentication), request monitoring, and resilience (rate limiting, circuit breaking).

```
   client
     |
     v
+-----------------+
|  API Gateway    |  <- security, monitoring, resilience
+-----------------+
   |     |     |
   v     v     v
 svc-A svc-B svc-C
```

**Spring Cloud Gateway** builds this around three concepts:

- **Route**: matches a request to a destination and forwards it.
- **Predicate**: the condition a request must match to take that
  route, for example a path prefix or a header value.
- **Filter**: manipulates the request or response as it passes through, adding headers, rewriting paths, handling auth flows.

## Spring Data Redis

Redis is an in-memory, non-relational (key-value) data store. Because
everything lives in memory instead of on disk, reads and writes are
extremely fast. `spring-data-redis` gives Spring applications a
`RedisTemplate` (or repository-style access) for using Redis as a
cache or a lightweight message broker.

## Event-Driven Architecture (Spring Cloud Stream)

Services interact indirectly, by producing and consuming events,
instead of calling each other directly. A producer doesn't need to
know who (or how many) consumers exist.

```
 producer --> [ binding ] --> destination --> [ binding ] --> consumer
                                (topic/queue)
```

- **Message**: the event itself, the unit of data passed around.
- **Destination**: where messages land, backed by an actual broker
  topic or queue (Kafka, RabbitMQ).
- **Binding**: the bridge between the application's producer/consumer
  code and a destination, configured declaratively rather than wired
  by hand.

Two common brokers, with different delivery models:

- **Kafka** (pub-sub, event streaming): consumers subscribe to a
  topic, and messages are retained rather than removed on read.
  Multiple independent consumers can read the full stream at their own pace.
- **RabbitMQ** (AMQP): producers publish events onto a queue, and each
  message is consumed once, then removed. A better fit for classic
  task distribution than for multiple independent readers of the same
  stream.

Spring Cloud Stream provides a binder per broker (for example
`spring-cloud-stream-binder-rabbit`) so the application code talks to
the same producer/consumer abstractions regardless of which broker
sits underneath; the broker-specific wiring lives in
`application.properties`.

## Spring Security

A powerful, highly customizable framework that provides both
authentication and access control for a Spring application.

- **Authentication**: the application establishes who the user is.
- **Authorization**: once identity is known, the application decides
  whether that user is allowed to do what they're asking to do.
- **Authorization server**: a dedicated service responsible for
  authenticating users and issuing access tokens, so applications don't each have to implement their own login flow.