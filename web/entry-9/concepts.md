# Entry 9: Scaling the Database

## Sharding / Data Partitioning

Splitting a single database into smaller pieces spread across multiple
machines, so no one node has to store or serve all the data.

### Horizontal Partitioning (Sharding)

Rows are the unit of split. The same table is cut into pieces called
shards, each holding a subset of the rows on its own machine.

```
                       Locations table
        +------------+------------+------------+
        | zip < 33k  | 33k-66k    | zip >= 66k |
        |  shard 1   |  shard 2   |  shard 3   |
        +------------+------------+------------+
```

### Vertical Partitioning

Columns are the unit of split. A single row's fields live on different
machines.

```
              users
        +------------------+       +-------------------+
        |  profile columns |       |  metadata columns |
        |  (name, email)   |       |  (last_login...)  |
        |     shard 1      |       |     shard 2       |
        +------------------+       +-------------------+
```

Example: `user.profile` is read on every page load, `user.metadata`
rarely is. Splitting them keeps hot and cold data from competing for
the same cache and disk.

## Partitioning Strategies

These strategies decide which shard a given row lands in. They only
apply to horizontal partitioning; vertical partitioning is a fixed
decision made at table design time, not a per-row one.

### Range-based

Partitions are assigned a contiguous range of a chosen key.

```
zip_code < 33000            -> shard 1
33000 <= zip_code < 66000   -> shard 2
zip_code >= 66000           -> shard 3
```

Simple, and range queries ("all zips between X and Y") stay on one or
two shards. Downside: an uneven key distribution creates hot shards,
for example most rows clustering just above one of the boundaries.

### Hash-based

A hash function maps the entity's key to a shard number.

```
shard = hash(id) % number_of_shards
```

Spreads data evenly regardless of the key's natural distribution. The
problem is resharding: adding or removing a shard changes the divisor,
so `hash(id) % number_of_shards` gives a different result for almost
every key, and nearly all the data has to move. Consistent hashing is
the usual fix, it remaps only a small slice of keys when the shard
count changes.

### List-based

Each partition is assigned an explicit list of values.

```
shard 1: Europe
shard 2: Americas
shard 3: Asia
```

Good when the partitioning key already has meaningful, known groupings,
like region, tenant, or business unit.

## Partitioning Drawbacks

- Data no longer lives on one server, so a query touching multiple
  shards has to be sent to each one separately, then the results
  combined by hand.
- Joins across shards aren't supported by the database anymore. They
  have to be done in application code, or avoided by design.
- Referential integrity (foreign keys) can't be enforced across shards
  by the database. It becomes the application's responsibility.

## Database Replication

Copying the same data across multiple nodes, primarily to scale *read*
operations and to survive a node going down.

```
       clients --writes--> [ master ]
                               |
                        replicates to
                 +-------------+-------------+
                 |             |             |
           [ replica 1 ] [ replica 2 ] [ replica 3 ]
                 ^             ^             ^
                 +-------- reads from clients -------+
```

- **Master**: handles write operations, the single source of truth.
- **Replicas** (slaves): handle read operations, scaling read capacity
  horizontally.
- If the master fails, one replica is promoted to become the new
  master (failover), the same idea as active-passive load balancing.

### Synchronous vs asynchronous replication

- **Synchronous**: the master waits for at least one replica to confirm
  the write before acknowledging it to the client. Strong consistency,
  higher write latency.
- **Asynchronous**: the master acknowledges the write immediately and
  replicates in the background. Low latency, but a replica can briefly
  serve stale data, and a write can be lost if the master crashes
  before replicating.

## Indexes

A separate data structure, built on one or more columns of a table,
that lets the database find matching rows without scanning every row.

```
   index (on last_name)              table
+---------------------+        +-----------------+
| "Adams" -> row 42   |------->| row 42: Adams... |
| "Diaz"  -> row 7    |------->| row 7:  Diaz...  |
| "Osei"  -> row 15   |------->| row 15: Osei...  |
+---------------------+        +-----------------+
```

- Usually built as a **B-tree**, a sorted structure that makes lookups
  fast: `O(log n)`.
- Speeds up `WHERE`, `JOIN`, and `ORDER BY` on the indexed column(s).
- Trade-off: every `INSERT`/`UPDATE`/`DELETE` has to update the index
  too, and the index itself takes extra storage.
