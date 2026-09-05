# Entry 13: Real Software Products

## Automation

Building, testing, and deploying software involves a lot of repetitive steps. Doing them by hand costs time and is error-prone.

Automating pipelines is what makes it possible to ship smaller changes, more often, with more confidence.

```
commit -> build -> test -> package -> deploy
          \____________________________/
              automated, not manual
```

### Continuous Integration (CI)

Every commit triggers an automatic build and test run.

- We get feedback in minutes.
- A broken build is visible immediately, and cheap to fix.

### Continuous Delivery vs. Continuous Deployment

Both start from the same place: every build that passes CI is treated as a release candidate, ready to go to production at any time. The difference is what happens next.

- **Continuous Delivery**: a human still decides *when* to release.

- **Continuous Deployment**: that decision is automated too. Every build that passes the pipeline goes to production with no manual gate at all.

Either way, releasing has to be a routine, low-drama event.

## Automated Tests

Tests are what make CI/CD trustworthy in the first place.

- Confidence to refactor: a passing suite means you can restructure code and find out immediately if you broke something.

- Executable documentation: a test shows how the code is meant to be used, and stays accurate because it fails the moment the code drifts from it.

- A safety net for the whole team, not just whoever wrote the code: anyone can change a file they didn't write and trust the suite to catch what they missed.

The **testing pyramid** shapes how many of each kind you write:

```
         / \
        /   \
       / e2e \      few (they are slow)
      /------ \
     / integr. \    some (components working together)
    /---------- \
   /   unit      \  many (fast, isolated and cheap)
  /---------------\
```

- **Unit tests**: a single component in isolation, dependencies
  mocked or stubbed out. Fast enough to run on every save.

- **Integration tests**: real parts of the system working together (a service and a real database, for example).

- **End-to-end (E2E) tests**: drive the whole system the way a user would.

## Cloud Computing

> "We get computing resources dynamically, on demand."

We rent compute, storage, and other infrastructure from a provider, and can scale that usage up or down as demand changes.

Cloud services sit at different levels of abstraction, from how much you manage yourself to how much the provider takes off your hands:

- **IaaS** (Infrastructure as a Service): raw virtual machines,
  storage, and networking. You control the OS and everything above it.

- **PaaS** (Platform as a Service): a managed runtime to deploy code onto; no servers to patch, but less control over the environment.

- **FaaS** (Function as a Service / serverless): you provide a single function; the platform runs it on demand and scales it invisibly.

- **SaaS** (Software as a Service): a complete application, used
  as-is over the network. Nothing to deploy or run at all.

### Properties of cloud-native systems

- **Scalability**: handling increasing load by adding resources.

- **Loose coupling**: different parts of a system know as little as possible about each other, so one can change without breaking the rest.

- **High cohesion**: things that change together are grouped
  together.

- **Fault tolerance**: the system keeps working, or recovers on its own, when a part of it fails.

- **Observability**: the system's internals can be inferred from the data it exposes (logs, metrics, traces) instead of guessing.

- **Manageability**: a system can be updated, configured, or patched while it stays up and running.

Automation, testing, and these properties reinforce each other: a system that's loosely coupled and observable is also the one that's easiest to test and safe to deploy automatically.
