# Day 6: Graphs and Trees

Used more than you'd think: dependencies, permissions, DB indexes, routing.

## Graph

A collection of nodes with edges between them.

- every node can store a list of adjacent vertices

- you could reach potentially each node from any other

```
    A --- B --- C
    |     |     |
    D --- E --- F
```

Adjacency list (each node keeps its own neighbors):

```
A: [B, D]   B: [A, C, E]   C: [B, F]
D: [A, E]   E: [B, D, F]   F: [C, E]
```

Adjacency matrix (V×V grid, 1 where an edge exists):

```
    A  B  C  D  E  F
 A [0, 1, 0, 1, 0, 0]
 B [1, 0, 1, 0, 1, 0]
 C [0, 1, 0, 0, 0, 1]
 D [1, 0, 0, 0, 1, 0]
 E [0, 1, 0, 1, 0, 1]
 F [0, 0, 1, 0, 1, 0]
```

### Graph Algorithms

- Depth-First Search O(V + E)
- Explore as far as possible on each branch
- pre-order
- boolean "visited" needed
- use it for: does a path exist, cycle detection, topological sort

```java
void dfs(Node n){
    Stack stack = new Stack();

    stack.push(n);

    while(!stack.isEmpty()){
        Node stackNode = stack.pop();

        if(!stackNode.visited){
            print(stackNode.data);
            stackNode.visited = true;

            for(Node x: stackNode.adjacents()){
                stack.push(x);
            }
        }

    }

}
```

- Breadth first search O(V + E)
- Explore all the vertices before moving to a neighbour node
- use it for: shortest path in an unweighted graph, "degrees of separation"

```java
void bfs(Node n){
    Queue<Node> q = new LinkedList<>();

    n.visited = true;
    q.add(n);

    while(!q.isEmpty()){
        Node qNode = q.remove();
        print(qNode.data);

        for(Node x: qNode.adjacents()){
          if(!x.visited()){
              x.visited = true;
              q.add(x);
          }
        }

    }

}
```

## Tree

- A connected graph without cycles
- non-linear structure
- organizes data hierarchically

```
        root
        /  \
     child  child
      / \
   leaf leaf
```

### Binary Tree

- up to two children per node
- a perfect binary tree: every level is filled
- a complete binary tree: every level filled except possibly the
  last
- balanced: keeps height O(log N). Combined with the BST rule
  (left < node < right), that's what makes search and inserts O(log N)

```
        4
       / \
      2   6
     / \ / \
    1  3 5  7
```

### Going through a binary tree

- in-order
```java
void inOrder(TreeNode n){
  if(n != null){
    inOrder(n.left);
    print(n.data);
    inOrder(n.right);
  }
}
```
- pre-order
```java
void preOrder(TreeNode n){
  if(n != null){
    print(n.data);
    preOrder(n.left);
    preOrder(n.right);
  }
}
```

- post-order
```java
void postOrder(TreeNode n){
  if(n != null){
    postOrder(n.left);
    postOrder(n.right);
    print(n.data);
  }
}
```


- sum all nodes of a binary tree
```java
int binTreeSum(TreeNode n){
  if(n == null) return 0;
  return binTreeSum(n.left) + n.data + binTreeSum(n.right);
}
```

Time = O(N): every node visited once, O(1) work per call.

Space = O(height), not flat O(N): only one root-to-leaf path sits on
the call stack at a time. O(log N) balanced, O(N) worst case on a
skewed tree, so O(N) isn't wrong as a worst-case bound, just looser
than O(height).

