# Day 8: Load Balancers and Caching

## Load Balancing

Helps spread traffic across a cluster of servers, improving response time and availability.

- Sits between the client and the server
- Reduces individual server load and prevents a single point of failure

### Basic topology

```
clients --> [ load balancer ] --+--> server 1
                                 +--> server 2
                                 +--> server 3
```

### Benefits

- faster, uninterrupted experience
- less downtime
- higher throughput can be handled

### Algorithms

- round robin: requests distributed sequentially across servers. The current load
is ignored
- weighted round robin: same idea, but servers with more capacity get a larger share
- least connections: routes to the server with the fewest active connections
- least response time: like least connections, but also factors in current average response time

### Failover patterns

- active-passive: one node handles traffic, a standby node takes over only if it fails (detected via health checks)
- active-active: multiple nodes handle traffic at the same time; if one fails, the others absorb its load


## Caching

Improves performance by storing frequently accessed data in temporary storage 

- recently requested data is likely to be requested again (temporal locality)
- short-term memory: limited amount of space but faster than the original source of data

### Content delivery network (CDN)

A geographically distributed set of cache servers that serve content from a location close to the client.

- useful for sites serving large amounts of static content

### Cache writing policies

How a write reaches the cache and the backing store.

- write-through: cache and db updated at the same time, strong consistency but higher latency
- write-around: writes go directly to the db, skipping the cache; the cache is only populated on the next read.
- write-back (write-behind): write the cache only, the database is synchronized later, asynchronously. low latency but risk of losing data if the cache fails.

### Cache invalidation

If the data changed in the database, stale entries already sitting in the cache need to go, or be refreshed.

- TTL (time-to-live): each entry expires automatically after a set duration, stale data can still be served until then
- explicit invalidation: the write path purges (or updates) the matching cache entry as part of the write, tighter consistency but couples the write path to the cache

### Eviction

Removing stored data from the cache when running out of space.

- FIFO (first-in-first-out)
- LIFO (last-in-first-out)
- LRU (least-recently-used)
- LFU (least-frequently-used)
