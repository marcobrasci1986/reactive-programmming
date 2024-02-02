# Reactive Streams Using Spring WebFlux 6

#
# Reactive Programming

Imperatieve Programming: ![imperative-programming.png](assets%2Fimperative-programming.png)

Reactive Programming;
![reactive-programming.png](assets%2Freactive-programming.png)

### Non-blocking asynchronous components

Non-blocking, code execution will continue, even when calling external data sources.

### Data as stream

Instead of receiving the data back in 1 go, we receive it in pieces, one by one. Until all data is transferred

### Functional programming

![chain-functions.png](assets%2Fchain-functions.png)

## Project Reactor

![project-reactor.png](assets%2Fproject-reactor.png)

# Flux vs Mono

![flux-vs-mono.png](assets%2Fflux-vs-mono.png)

Mono: We only receive one item
Flux: We can receive more than one item

... before closing the data stream

## Errors

![errors.png](assets%2Ferrors.png)

## Spring Webflux

![SpringWebflux.png](assets%2FSpringWebflux.png)

# Project Reactor Operators

![operators.png](assets%2Foperators.png)

## Types

![operator-types.png](assets%2Foperator-types.png)

Reference: https://projectreactor.io/docs/core/release/reference/index.html

## Create operators

![create-operators.png](assets%2Fcreate-operators.png)

Spring Data R2CDB
Reactive Relational Database Connectivity
Driver: r2dbc-mariadb or r2dbc-mysql, ...

## Error handling
![error-handling.png](assets%2Ferror-handling.png)

## Peek operators
![peek-operators.png](assets%2Fpeek-operators.png)

# Unittesting
![Unittesting.png](assets%2FUnittesting.png)