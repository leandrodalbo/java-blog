# Entry 10: System Design Concepts Mix

## SQL vs NoSQL

**Relational databases** are structured, with a fixed, predefined schema.

- **SQL**: data organized into rows and columns, fixed schema.
- **ACID compliant**: reliability, every transaction leaves the data in a consistent state.

**Non-relational databases** are unstructured, with a dynamic schema that can vary.

- **Key-value store** (Redis): every record is a key mapped to a value, nothing more.
- **Document-based**: data grouped into collections, but each document in a collection can have a different structure.
- **Horizontal scalability**: easier to scale by adding more machines.
- Trades ACID guarantees for performance and scalability.

### Reasons to use SQL

- ACID compliance is required.
- The data is structured and its shape is known upfront.

### Reasons to use NoSQL

- Volume of data would otherwise become the bottleneck.
- Large volumes of data need to be stored and the structure isn't important, or varies too much to fit a fixed schema.

## Pub/Sub

A producer publishes messages to a topic instead of talking to consumers directly. Every consumer subscribed to that topic gets its own copy of
the message.

```
CLIENT --req--> SERVER --publish--> [ topic ]
                                        |
                        +---------------+---------------+
                        |               |               |
                  consumer 1      consumer 2       consumer N
```

Kafka is the usual example.

- Decouples producers from consumers, neither needs to know the other exists.
- Messages are persisted, a consumer that comes back online can catch up.
- Easy to scale: add more consumers to spread the processing load.
- Multiple consumers can read the same message independently.

### Disadvantages

- **No atomicity**: publishing several messages isn't a single unit of work, some can succeed while others fail.
- **No idempotency**: delivery is usually at-least-once, so a consumer
  can see the same message twice (e.g. after a retry) and has to be
  written to handle duplicates itself.

### Message queue, not the same thing

A **message queue** (RabbitMQ) looks similar but the delivery model is
different: each message is consumed by exactly one consumer, not every
subscriber. Multiple consumers reading the same queue compete for
messages, which spreads load rather than fanning the same message out
to everyone. It's middleware used to let services communicate
asynchronously without a topic's broadcast behavior.

## API Gateway

A single entry point that sits in front of multiple backend services.

- Every client talks to the gateway, not to the services directly.
- Routes each request to the right backend service.
- Common place to handle cross-cutting concerns once instead of in every
  service: authentication, rate limiting, and logging.

## Proxy

An intermediate server between the client and the backend that can
filter, transform, or forward a request.

- A **forward proxy** sits in front of the client, hiding the client's
  identity from whatever server it talks to.
- A **reverse proxy** sits in front of the backend instead, hiding the
  backend's servers from the client. It can also terminate SSL, cache
  responses, or compress them before they reach the client.

## Load Balancer vs API Gateway

Both sit between the client and "the backend", which is what makes them
easy to mix up with each other, and with a reverse proxy. A **load
balancer** is really a reverse proxy specialized for one job: spreading
traffic across many *replicas of the same service*. An **API gateway**
routes to many *different services* instead of replicas of one, and adds
concerns that apply across all of them: auth, rate limiting,
request/response transformation.

```
Load balancer:  client --> [ LB ]  -->  service A (replica 1)
                                    -->  service A (replica 2)
                                    -->  service A (replica 3)

API gateway:    client --> [ GW ]  -->  users service
                                    -->  orders service
                                    -->  payments service
```
