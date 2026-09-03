# Entry 11: Spring Roots

## Spring

An open-source framework that provides the context to execute Java
applications: it manages "beans" (the objects your code doesn't have to
construct by hand), configuration, and other resources. It's layered
and modular. A project only pulls in the pieces it needs.

Core modules:

- **core**: the IoC container itself, the foundation everything else sits on
- **data access**: JDBC, transaction management, ORM integration
- **web**: MVC, REST, the servlet layer
- **test**: support for writing unit and integration tests 

## Inversion of Control (IoC)

The principle behind all of this. A long time ago we had to instantiate what we needed: `new UserRepository()`. Spring flips that: the framework creates the objects and hands them to your code instead. Control over *when* and *how* dependencies get built moves out of your class and into the
container. That's the "inversion".

This makes it much easier to test our components in isolation by mocking the dependencies.

## Dependency Injection (DI)

DI is how Spring actually achieves IoC: a class declares what it needs,
and something outside the class provides it, instead of the class
constructing its own dependencies.

- **Constructor injection**: dependencies passed in through the
  constructor. The preferred style, fields can be `final`, and the class
  can be tested with plain `new` and mocks, no Spring container required.

- **Setter injection**: dependencies set through a setter after
  construction. Useful for optional dependencies that aren't required to
  build a valid object.

- **Field injection** (`@Autowired` directly on a field): shortest to
  write, but the class can't be constructed without reflection or a
  running container, which makes plain unit tests harder. Generally
  avoided in favor of constructor injection.

## The IoC Container

The container, usually accessed as an `ApplicationContext`, is what
performs the wiring described above.

```
  @Component classes                     IoC Container
+----------------------+       +--------------------------------+
|  UserService         |      |  1. scan classpath, find beans  |
|    needs UserRepo    +----->|  2. resolve each dependency     |
|  UserRepository      |      |  3. inject (wire) them together |
+----------------------+      |  4. manage scope + lifecycle    |
                              +---------------------------------+
```

- creates and wires objects (beans)
- manages each bean's lifecycle: instantiate, populate its dependencies,
  run any init callbacks, keep it ready for use, run destroy callbacks
  on shutdown
- bean scopes: **singleton** (default, one shared instance per container), **prototype** (a new instance every time the bean is requested), **request** / **session** (web-only, one instance per HTTP request or session)
- autowiring: the container matches a dependency by type (or name) to a
  bean it already manages, and injects it, no manual wiring code needed

### Stereotype annotations

The annotations that mark a class as a bean the container should manage:

- `@Component`: generic bean, the base annotation the others build on
- `@Service`: business logic layer
- `@Repository`: persistence layer
- `@Controller` / `@RestController`: web layer. `@RestController` is
  `@Controller` plus `@ResponseBody`, the return value is serialized
  straight to the response body (JSON) instead of resolved to a view.

## Spring Boot

Takes an opinionated view of Spring: instead of wiring everything by
hand, it configures sensible defaults based on the starters (dependencies)
added to the project.

- no XML configuration
- faster path to production, convention over manual setup
- embedded HTTP server (Tomcat by default), no separate servlet
  container to deploy the artifact

### Main annotations

`@SpringBootApplication` is shorthand for three annotations combined:

- `@Configuration`: marks the class as a source of bean definitions
- `@ComponentScan`: scans the package (and subpackages) for
  `@Component`-annotated classes to register as beans
- `@EnableAutoConfiguration`: configures beans automatically based on
  what's on the classpath, for example a `DataSource` bean appears once
  a JDBC driver dependency is present

`@RestController`: handles incoming requests, see stereotype annotations
above.

`@SpringBootTest`: loads the full application context, for integration
tests that need real wiring instead of mocks.

## Actuator

Adds production-ready endpoints for inspecting a running application
without attaching a debugger.

- `/health`: whether the app and its dependencies (database, disk
  space...) are up
- `/metrics`: runtime metrics, JVM memory, request counts, and similar
- useful for real-time monitoring, and for catching a performance
  regression before it becomes an incident