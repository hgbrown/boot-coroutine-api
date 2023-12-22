# SpringBoot Coroutine API demo

Demonstrates the use of Coroutine API to provide a non-blocking, asynchronous API for a Spring Boot application.

## Testcontainers support

This project
uses [Testcontainers at development time](https://docs.spring.io/spring-boot/docs/3.2.0/reference/html/features.html#features.testing.testcontainers.at-development-time).

Testcontainers has been configured to use the following Docker images:

* [`postgres:latest`](https://hub.docker.com/_/postgres)

To run the demo using the TestContainer support for development, run the `main` function in
the `TestBootCoroutineApiApplication` class. This will start the Spring Boot application and the PostgreSQL database in
a Docker container.

The application is set up to use the `dev` profile by default which is only available in the `test` classpath.
Attempting to start the application from the `BootCoroutineApiApplication` class will fail since no `dev` profile
application properties are available.

To run the application in a non-dev environment requires the use of an alternative profile to be provided along with the
configuration properties for the PostgreSQL database.

## Reference Documentation

* [Spring Boot Testcontainers support](https://docs.spring.io/spring-boot/docs/3.2.0/reference/html/features.html#features.testing.testcontainers)
* [Testcontainers R2DBC support Reference Guide](https://java.testcontainers.org/modules/databases/r2dbc/)
* [Testcontainers Postgres Module Reference Guide](https://java.testcontainers.org/modules/databases/postgres/)
* [Coroutines section of the Spring Framework Documentation](https://docs.spring.io/spring/docs/6.1.1/spring-framework-reference/languages.html#coroutines)
* [Spring Data R2DBC](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#data.sql.r2dbc)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#web.reactive)
* [Testcontainers](https://java.testcontainers.org/)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing data with R2DBC](https://spring.io/guides/gs/accessing-data-r2dbc/)
* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
* [R2DBC Homepage](https://r2dbc.io)


