# LRU Cache

LRU eviction strategy

## Problem

Design a cache with a fixed capacity that supports `get` and `put`, both in O(1) time. When `put` is called at full capacity, evict the least-recently-used entry first. A `get` call counts as "using" that entry.

```
capacity 2
put(1, 100)          -> cache: [1]
put(2, 200)          -> cache: [1, 2]
get(1)                -> 100, and 1 is now the most recently used
put(3, 300)          -> capacity exceeded, evicts 2 (least recently used), not 1
get(2)                -> -1, evicted
```

## Tests (RED)

```java
private LRUCache underTest = new LRUCache(2);

@Test
public void shouldReturnMinusOneForMissingKey(){
    assertThat(underTest.get(1)).isEqualTo(-1);
}

@Test
public void shouldReturnValueJustPut(){
    underTest.put(1, 100);

    assertThat(underTest.get(1)).isEqualTo(100);
}

@Test
public void shouldUpdateValueForExistingKeyWithoutEvicting(){
    underTest.put(1, 100);
    underTest.put(2, 200);
    underTest.put(1, 111); // update, not a new key

    assertThat(underTest.get(1)).isEqualTo(111);
    assertThat(underTest.get(2)).isEqualTo(200); // still there
}

@Test
public void shouldEvictLeastRecentlyUsedWhenCapacityExceeded(){
    underTest.put(1, 100);
    underTest.put(2, 200);
    underTest.put(3, 300); // capacity 2, evicts key 1

    assertThat(underTest.get(1)).isEqualTo(-1);
    assertThat(underTest.get(2)).isEqualTo(200);
    assertThat(underTest.get(3)).isEqualTo(300);
}

@Test
public void shouldTreatGetAsUsageProtectingFromEviction(){
    underTest.put(1, 100);
    underTest.put(2, 200);
    underTest.get(1); // key 1 is now most-recently-used
    underTest.put(3, 300); // capacity 2, should evict key 2 instead

    assertThat(underTest.get(1)).isEqualTo(100);
    assertThat(underTest.get(2)).isEqualTo(-1);
    assertThat(underTest.get(3)).isEqualTo(300);
}

@Test
public void shouldTreatPutOnExistingKeyAsUsage(){
    underTest.put(1, 100);
    underTest.put(2, 200);
    underTest.put(1, 111); // updates key 1, marks it most-recently-used
    underTest.put(3, 300); // capacity 2, should evict key 2

    assertThat(underTest.get(1)).isEqualTo(111);
    assertThat(underTest.get(2)).isEqualTo(-1);
    assertThat(underTest.get(3)).isEqualTo(300);
}
```

## Implementation (GREEN)

```java
private static class Node {
    int key;
    int value;
    Node prev;
    Node next;

    Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

private final int capacity;
private final Map<Integer, Node> map;
private final Node head;
private final Node tail;

public LRUCache(int capacity) {
    this.capacity = capacity;
    this.map = new HashMap<>();

    // sentinel nodes: head.next is always the most-recently-used real
    // node, tail.prev is always the least-recently-used. Keeping these
    // dummies means insert/remove never has to null-check a boundary.
    this.head = new Node(0, 0);
    this.tail = new Node(0, 0);
    head.next = tail;
    tail.prev = head;
}

/**
 * O(1): one map lookup, then unlink + reinsert at front (the access
 * itself counts as "use").
 */
public int get(int key) {
    Node node = map.get(key);
    if (node == null) return -1;

    remove(node);
    insertAtFront(node);
    return node.value;
}

/**
 * O(1): existing key is an update (not an eviction candidate); a new
 * key evicts the LRU node only if the cache is already full.
 */
public void put(int key, int value) {
    Node existing = map.get(key);

    if (existing != null) {
        existing.value = value;
        remove(existing);
        insertAtFront(existing);
        return;
    }

    if (map.size() == capacity) {
        Node leastRecentlyUsed = tail.prev;
        remove(leastRecentlyUsed);
        map.remove(leastRecentlyUsed.key);
    }

    Node node = new Node(key, value);
    insertAtFront(node);
    map.put(key, node);
}

private void remove(Node node) {
    node.prev.next = node.next;
    node.next.prev = node.prev;
}

private void insertAtFront(Node node) {
    node.prev = head;
    node.next = head.next;
    head.next.prev = node;
    head.next = node;
}
```

## Why a HashMap and a linked list, not either alone

A `HashMap` alone has no sense of order. Finding the least-recently-used entry means scanning all of them, O(n).
A linked list alone reorders in O(1) but finding a node by key means walking it, also O(n).

Combine them: the map's values ARE the list's nodes. A key finds its node in O(1) through the map, and moving or unlinking that node is O(1) pointer surgery, no traversal either way.

```
head <-> node(3) <-> node(2) <-> node(1) <-> tail
         most recently used         least recently used
```

