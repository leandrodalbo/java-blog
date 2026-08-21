# Day 7: System Design concepts

## CAP Theorem

- It is not possible for a distributed system to simultaneously provide consistency, availability
and partition tolerance. Only 2 can be picked.

- In practice networks partition whether you want it or not, so the real choice is: during a partition, favor consistency or favor availability.

## Distributed Systems

- A collection of independent nodes working together, over a network, to achieve the same goal.

## System Design

Thinking about scalability could save time and money.

### Scalability 

The capability of a system to grow and handle an increasing demand without losing performance.
A scalable system balances the load between all the participating nodes.

- Horizontal scaling: adding more nodes into your pool of resources
- Vertical scaling: adding more CPU, RAM or storage to an existing server.

### Reliability

In case of failure the system will remain operative. It will keep delivering its services even when some of the components are failing.

- Redundancy and Replication Strategies

Redundancy: duplication of nodes, removes a single point of failure.
Replication: copying data across multiple nodes to keep them in sync (e.g. leader/follower).


### Availability 

It is the percentage of time that a system is operative, including through failures.
An AP system sacrifices consistency to stay responsive as much as possible.


## Consistency Patterns
  
- The data must be the same across the system. All the users get the same data at the same time independently of the node.

### Eventual consistency

- The inconsistency is temporary and events are eventually synchronized everywhere.

### Strong consistency

- The data synchronization is a priority. Every read operation must always reflect the latest value.

### Causal consistency

- Operations that are causally related (one depends on the other) must be seen in the same order by everyone. Unrelated (concurrent) operations are free to appear in a different order for different observers.

## Partition Tolerance

The system keeps working even when the network between nodes breaks down and some nodes can't talk to each other, not just when a node is down.

### Reliability vs Availability

IF IT IS RELIABLE => IT IS AVAILABLE but being AVAILABLE does not mean it is RELIABLE.

## Other concepts

### Idempotency 

A repeated operation will always produce the same result, no matter how many times it runs.

## Efficiency

- Response time: it is the latency. Low latency => highly responsive system.
- Throughput: the rate of data or operations a system processes per unit of time, e.g. requests/sec or bytes/sec.

## Monolith vs Microservices

### Monolith
- A single codebase and deployable unit.
- Coupling components
- single point of failure

### Microservices

- Independent self contained services
- easier to scale and deploy
- can use multiple technologies
- increase complexity
- network latency