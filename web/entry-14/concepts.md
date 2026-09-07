# Entry 14: Up & Running in Production

## Virtualization vs. containers

- **Virtualization** uses a hypervisor to abstract the hardware. It can run several full operating systems on one physical machine, each isolated from the others.

- A **container** is a running instance of an image: an application packaged with everything it needs to run. Containers are much lighter than VMs because they share the host's kernel instead of booting their own OS.

```
Virtual machines              Containers

+--------+  +--------+        +--------+  +--------+
|  App   |  |  App   |        |  App   |  |  App   |
+--------+  +--------+        +--------+  +--------+
|Guest OS|  |Guest OS|        +--------------------+
+--------+  +--------+        | container runtime  |
|    Hypervisor      |        +--------------------+
+--------------------+        |     Host OS        |
|    Host OS         |        +--------------------+
+--------------------+
```

This is what makes containers portable: the same image runs the same way on a laptop, a CI runner, or a cloud VM, since it carries its own dependencies and only relies on the host kernel underneath.

### Docker concepts

- **Image**: a read only, layered archive with the libraries and binaries needed to run an application. A running container adds its own writable layer on top.

- **dockerd**: the daemon running in the background that builds images, and starts, stops and manages containers.

- **Docker Engine (client/server)**: the `docker` command line tool is a thin client. It talks to `dockerd` over a REST API (usually a Unix socket) to do the actual work.

```
docker CLI  --REST API-->  dockerd  --manages-->  images, containers, networks, volumes
```

## Orchestration

A single Docker host handles a handful of containers fine on its own. What it can't do is spread them across many machines, restart them when a machine dies, or scale them up under load. That's the job of an orchestrator: something that brings containers up or down as needed, from a declarative description of the desired state.

- **Cluster**: a set of nodes (machines) running containerized applications.

### Kubernetes

- **Pod**: the smallest deployable unit. Wraps one or more tightly coupled containers that share networking and storage.

- **Worker node**: a VM (or physical machine) that runs pods, using three pieces: **kubelet** keeps its pods running and healthy, **kube-proxy** routes network traffic to them, and a container runtime actually runs them.

- **Control plane**: the brain of the cluster, usually running on its own dedicated nodes. Four pieces do the work: **kube-apiserver** (the front door every request goes through), **etcd** (stores the cluster's state), **kube-scheduler** (picks which node a new pod runs on), and **kube-controller-manager** (notices drift from the declared state and fixes it, like restarting a dead pod).

```
                +-------------------+
                |   Control plane   |
                |  (API, scheduler) |
                +---------+---------+
                          |
        +-----------------+-----------------+
        |                 |                 |
  +-----v-----+     +-----v-----+     +-----v-----+
  | Worker    |     | Worker    |     | Worker    |
  | node      |     | node      |     | node      |
  | [pod][pod]|     | [pod]     |     | [pod][pod]|
  +-----------+     +-----------+     +-----------+
```

A few `kubectl` commands cover most of a working day:

```
kubectl get pods                 # list running pods
kubectl describe pod my-pod      # inspect a pod's events and status
kubectl logs my-pod              # read a container's logs
kubectl apply -f deployment.yaml # create or update resources from a file
```

### How do we create the infrastructure?

**Terraform** (HashiCorp) lets developers define, provision and manage infrastructure across multiple cloud providers using a declarative configuration language. It keeps a state file recording what it last created, so it knows exactly what to change on the next apply.

```
terraform init    # download providers, set up the working directory
terraform fmt     # format the configuration files
terraform plan    # preview what would change
terraform apply   # create or update the real infrastructure
terraform destroy # tear it all down
```

## Monitoring, tracing and distributed logging

Metrics, logs and traces are often called the three pillars of observability: together they let you tell what a system is doing without guessing. Monitoring is the practice of watching them and reacting when something looks wrong.

- **Metrics**: numeric measurements of system performance, like response time or database query counts.
- **Monitoring**: continuously gathering that data to detect problems, often with alerts when a metric crosses a threshold.
- **Tracing**: following a single request as it moves across multiple services, to find bottlenecks or failures in a distributed system. Each request carries a trace ID that ties its spans together across services.
- **Distributed logging**: collecting logs from every service into one searchable place, so a single request can be followed through them by that same trace ID.

### Prometheus

An open source monitoring tool that pulls metrics from configured targets at regular intervals and stores them as time series, queryable with PromQL.

### Grafana

An open source dashboard and visualization tool. It can use Prometheus (among other systems) as a data source to turn those metrics into readable charts and alerts.
