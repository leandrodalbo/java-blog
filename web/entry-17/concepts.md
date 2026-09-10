# Entry 17: Operating Systems and Linux

## What an operating system does

An **operating system (OS)** manages the CPU, memory, storage, and I/O devices, and gives each program an interface to use them.

Without it, every application would need to know how to manage a disk controller, network cards, and CPU scheduler directly.

## Kernel space and user space

The **kernel** is the core of the OS. It talks to hardware and decides which process gets the CPU next.

Everything else (your browser, your terminal, your Java program) runs in **user space** with restricted privileges. They use **system calls** to ask the kernel what they need.

```
   user space
 ┌─────────────────────────────────────┐
 │  your program                       │
 │      │  read(), write(), fork()...  │
 │      ▼  system call                 │
 ├─────────────────────────────────────┤
 │              kernel                 │
 │   process scheduler, memory,        │
 │   file system, device drivers       │
 ├─────────────────────────────────────┤
 │              hardware               │
 │        CPU, RAM, disk, NIC          │
 └─────────────────────────────────────┘
```


## Process and threads, quickly

A **process** is a running program: its own memory space, a set of open files and execution state. The OS creates processes, schedules them on the CPU, and cleans up after they exit.

A **thread** is a unit of execution inside a process. Threads in the same process share memory.

## Some basics

- **Shell**: the program that reads the commands you type and asks the kernel to run them. Bash and zsh are shells.

- **File descriptor**: a small integer a process uses to refer to an open file, socket, or pipe.

- **Standard streams**: every process starts with three file descriptors already open: `stdin` (0, input), `stdout` (1, normal output), `stderr` (2, errors).

## Useful and popular commands: grep and pipes

**`grep`** is older than most tools still in daily use. Its name comes from an `ed` (a very old line editor) command: `g/re/p`, "globally search for a regular expression and print matching lines". The command outlived the editor it was named after.

The **pipe** (`|`) connects the standard output of one process directly to the standard input of the next, with nothing written to disk in between.

```
ps aux | grep java
```

```
 ps aux  ──stdout──▶  grep java  ──stdout──▶  terminal
(every process)         (only matches)
```

Nothing here is specific to `grep`, the shell can chain any two programs this way as long as one writes to stdout and the other reads stdin.

## Inter-process communication (IPC)

Processes don't share memory by default. The OS provides ways for them to exchange data:

- **Pipes**: a one way byte stream between two related processes (like the shell pipe above).

- **Shared memory**: the fastest option, the kernel maps the same block of memory into more than one process, no copying needed. It needs synchronization, the OS doesn't order the accesses for you.

- **Sockets**: like a pipe, but works between unrelated processes, across machines, over a network.

- **Signals**: a small integer sent to a process to notify it about something (`SIGKILL`, `SIGTERM`).

## A pipe example implemented in C

`pipe()` asks the kernel for two file descriptors: one for reading, one for writing. 

It becomes real IPC once combined with `fork()`, which clones the calling process.

The child inherits copies of the parent's open file descriptors, including both ends of the pipe.

```c
#include <stdio.h>
#include <unistd.h>
#include <string.h>

int main() {
    int fd[2];
    pipe(fd); // fd[0] = read end, fd[1] = write end

    pid_t pid = fork();  // clones this process

    if (pid == 0) {
        // I am in the child process
        close(fd[1]); // not writing, close that end
        char buffer[64] = {0};
        read(fd[0], buffer, sizeof(buffer));
        printf("child received: %s\n", buffer);
        close(fd[0]);
    } else {
        // I am in the parent process
        close(fd[0]); // not reading, close that end
        char *msg = "hello from parent";
        write(fd[1], msg, strlen(msg) + 1);
        close(fd[1]);
    }

    return 0;
}
```

Save this as a `.c` file and compile it with `gcc`, no special flags needed since `pipe()`, `fork()`, `read()`, and `write()` are all standard POSIX functions declared in `unistd.h`:

```
gcc pipe_demo.c -o pipe_demo
./pipe_demo
# child received: hello from parent
```

```
            pipe(fd)
     fd[1] write ───▶ fd[0] read

              fork()
   ┌──────────────┐        ┌──────────────┐
   │    parent    │        │    child     │
   │ write(fd[1]) │──────▶│  read(fd[0]) │
   └──────────────┘        └──────────────┘
```

