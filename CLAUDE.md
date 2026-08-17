# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project state

This is a Spring Boot 4.1 / Kotlin 2.3 project generated from Spring Initializr. It currently contains only the
default scaffold (`PokemonTrackerApiApplication.kt` and a context-loads test) — no domain code, controllers,
entities, or repositories exist yet. Treat architectural decisions as greenfield unless new code says otherwise.

## Stack

- Kotlin, JVM toolchain 17, Gradle (Kotlin DSL) with the wrapper
- Spring Boot 4.1: `spring-boot-starter-data-jpa`, `spring-boot-starter-webmvc`
- Jackson via `jackson-module-kotlin` for JSON (de)serialization
- PostgreSQL as the runtime JDBC driver (`org.postgresql:postgresql`) — no datasource is configured yet in
  `application.properties`
- Test stack: `spring-boot-starter-data-jpa-test`, `spring-boot-starter-webmvc-test`, `kotlin-test-junit5`, run on
  JUnit Platform

Base package: `com.davenotdavid.pokemontrackerapi`.

## Commands

Use the Gradle wrapper (`./gradlew`), not a system-installed Gradle.

- Build: `./gradlew build`
- Run the app: `./gradlew bootRun`
- Run all tests: `./gradlew test`
- Run a single test class: `./gradlew test --tests "com.davenotdavid.pokemontrackerapi.PokemonTrackerApiApplicationTests"`
- Run a single test method: `./gradlew test --tests "com.davenotdavid.pokemontrackerapi.PokemonTrackerApiApplicationTests.contextLoads"`
- Clean build: `./gradlew clean build`

## Notes for future work

- JPA entities in this project should annotate with `jakarta.persistence.Entity`, `MappedSuperclass`, or
  `Embeddable` — the `allOpen` Gradle plugin config (`build.gradle.kts`) automatically opens classes with these
  annotations so Kotlin's default-final classes work with JPA proxying (no need to manually mark entities `open`).
- The Kotlin compiler is configured with `-Xjsr305=strict` (strict null-safety enforcement on JSR-305 annotated
  Java APIs) and `-Xannotation-default-target=param-property` (annotations on constructor properties apply to both
  the parameter and the property by default) — keep this in mind when annotating constructor-injected fields.
