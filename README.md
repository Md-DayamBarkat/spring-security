# Spring Security Learning Project

This repository is a lesson-based Spring Security learning project built with Spring Boot. It is organized as a study project that grows in small, testable steps from basic authentication to more realistic application security patterns.

## Goal

Build a GitHub-friendly reference project that helps you learn Spring Security by stage:

- start with the default security behavior
- add explicit security rules
- understand authentication vs authorization
- move from in-memory users to database-backed users
- later introduce JWT, hardening, and production-oriented practices

## Current Progress

Current lesson: `Lesson 2`

Implemented so far:

- explicit `SecurityFilterChain` configuration
- in-memory users with BCrypt password encoding
- public, authenticated, user-only, and admin-only endpoints
- role-based authorization with `hasRole(...)`
- security tests for anonymous, user, and admin access
- MySQL baseline captured for future lessons

## Tech Stack

- Java 8
- Spring Boot 2.7.18
- Spring Security 5.7.x
- Spring MVC
- Spring Data JPA
- Maven
- MySQL

## Project Structure

```text
spring-security/
|-- docs/                      lesson notes and roadmap
|-- src/main/java/             application source code
|-- src/main/resources/        application configuration
|-- src/test/java/             security tests
|-- pom.xml                    Maven build
|-- README.md                  repository overview
```

## Getting Started

Run the tests:

```bash
mvn test
```

Start the application:

```bash
mvn spring-boot:run
```

## Available Endpoints

- `GET /api/public/hello`
- `GET /api/user/profile`
- `GET /api/user/dashboard`
- `GET /api/admin/dashboard`

## Learning Credentials

Local learning users:

- `appuser / password` with role `USER`
- `adminuser / admin123` with role `ADMIN`

## Database Note

The MySQL datasource setup is intentionally not active yet in `application.yaml`. It is being kept as a baseline for the upcoming database-backed authentication lesson.

Planned local database:

- schema: `spring_security_demo`
- username: `root`
- password: `root`
- url: `jdbc:mysql://localhost:3306/spring_security_demo`

## Documentation

- [Learning Roadmap](docs/LEARNING-ROADMAP.md)
- [Lesson 1](docs/LESSON-01.md)
- [Lesson 2](docs/LESSON-02.md)
- [GitHub Organization Guide](docs/GITHUB-ORGANIZATION.md)

## Notes

This codebase is intentionally built in stages. Some early decisions are made for learning clarity first and will later be replaced by more production-appropriate patterns.
