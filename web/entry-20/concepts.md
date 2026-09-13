# Entry 20: Software Design, Writing It Well & Working Together

## Why bad design costs more over time

In a poorly designed system, every change becomes harder and more expensive because developers spend more time fixing bugs, working around fragile assumptions and resolving dependencies.

Adding more developers or producing more output with AI tools doesn't solve the problem. If the design is bad, you simply create more complexity, faster.

## What writing it well actually means

Writing well means it's easy to read, understand and change. A few practices make the biggest difference:

* **Keep It Simple (KISS):** use the simplest solution that works. Unnecessary cleverness creates extra cost.

* **Meaningful names:** names should make a function's purpose clear without needing comments.


```java
// unclear: what is 0.05, and why are we multiplying?
double calc(double a, double b) {
    return a * b * 0.05;
}

// clear: the name carries the intent
double calculateTax(double price, double taxRate) {
    return price * taxRate;
}
```
* **Small, single-purpose functions:** each function should do one thing well. Smaller units are easier to understand, change and test.

The goal is to reduce the amount of context someone needs to understand when making a change in the future.

## Reviews and pair programming

Two people spot different problems, which is why reviews catch bugs, unclear names and missing edge cases that the author may miss. They also spread knowledge across the team.

**Pair programming** takes this further by reviewing it as it's written. It's especially useful for junior developers, who can learn good habits and context directly from more experienced teammates.

Teams with good pair programming practices might not need a separate review at all. This practice shows good communication and collaboration between the team members. These are key to having a successful project.


## Working as a team: the Agile values

Good practices and reviewing well only make sense if every member of the team is clear about the shared goal and pushing in the same direction.

In 2001, the Agile Manifesto defined four values for how teams should work:

* **Individuals and interactions** over processes and tools.
* **Working software** over comprehensive documentation.
* **Customer collaboration** over contract negotiation.
* **Responding to change** over following a plan.

The things on the right still have value but in case of conflict we should prioritise what is on the left. For example, a project plan should never be more important than delivering working software.

