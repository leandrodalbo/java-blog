package org.example;

import java.util.HashMap;
import java.util.Map;

/**
 * The problem: a fixed-capacity cache with O(1) get and put, evicting the
 * least-recently-used entry when a new key arrives at capacity.
 *
 * A HashMap alone gives O(1) lookup but no notion of access order. A linked
 * list alone gives O(1) reordering but O(n) lookup by key. Combine them: the
 * map's values ARE the list's nodes, so a key finds its node in O(1), and
 * moving/evicting that node is pure pointer surgery, also O(1).
 */
public class LRUCache
{
    private static class Node
    {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value)
        {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> map;
    private final Node head;
    private final Node tail;

    public LRUCache(int capacity)
    {
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
    public int get(int key)
    {
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
    public void put(int key, int value)
    {
        Node existing = map.get(key);

        if (existing != null)
        {
            existing.value = value;
            remove(existing);
            insertAtFront(existing);
            return;
        }

        if (map.size() == capacity)
        {
            Node leastRecentlyUsed = tail.prev;
            remove(leastRecentlyUsed);
            map.remove(leastRecentlyUsed.key);
        }

        Node node = new Node(key, value);
        insertAtFront(node);
        map.put(key, node);
    }

    /**
     * Unlinks a node using its own prev/next pointers directly, no traversal.
     */
    private void remove(Node node)
    {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    /**
     * Splices a node in right after head, the most-recently-used position.
     */
    private void insertAtFront(Node node)
    {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }
}
