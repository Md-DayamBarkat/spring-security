# Lesson 1: Public and Protected Endpoints

Lesson 1 establishes the minimum working Spring Security setup for this project.

## Objective

Learn what Spring Security is doing before building custom authentication flows.

By the end of this lesson, you should understand:

- what a `SecurityFilterChain` is
- how Spring Security protects requests
- how public and protected endpoints are defined
- how in-memory users work
- why passwords must be encoded
- how to verify security behavior with tests

## What was added

### 1. Explicit security configuration

File: `src/main/java/com/app/security/spring_security/config/SecurityConfig.java`

This class defines the security rules for incoming HTTP requests.

Current rules:

- `/api/public/**` is open to everyone
- every other endpoint requires authentication

Authentication mechanisms currently enabled:

- HTTP Basic
- form login

## Why both HTTP Basic and form login are enabled

- HTTP Basic is easy for API testing tools such as Postman and curl
- form login helps you see Spring Security's built-in browser behavior

Later, when we build JWT, this design will change.

### 2. In-memory users

Two learning users are configured:

- `appuser / password` with role `USER`
- `adminuser / admin123` with role `ADMIN`

This is useful only for learning and local prototyping. It is not how real applications usually manage users.

### 3. Password encoding

Passwords are encoded with `BCryptPasswordEncoder`.

This matters because:

- raw passwords must never be stored directly
- Spring Security expects secure password comparison
- BCrypt is the standard baseline you must know

### 4. First endpoints

File: `src/main/java/com/app/security/spring_security/controller/LearningController.java`

Endpoints:

- `GET /api/public/hello`
- `GET /api/user/profile`

Expected behavior:

- public endpoint returns `200 OK` without login
- protected endpoint returns `401 Unauthorized` if you are not authenticated
- protected endpoint returns `200 OK` if valid credentials are sent

## How request security works

At a high level:

1. A request reaches the Spring Security filter chain.
2. Security checks whether the request matches a public rule.
3. If not public, Spring Security requires authentication.
4. If credentials are valid, the `Authentication` object is stored in the security context.
5. The controller can then read the authenticated user.

## How to test manually

Start the app:

```bash
mvn spring-boot:run
```

Test the public endpoint:

```bash
curl http://localhost:8080/api/public/hello
```

Test the protected endpoint without credentials:

```bash
curl -i http://localhost:8080/api/user/profile
```

Test the protected endpoint with credentials:

```bash
curl -i -u appuser:password http://localhost:8080/api/user/profile
```

## Automated tests

File: `src/test/java/com/app/security/spring_security/LearningControllerSecurityTests.java`

The tests prove three core behaviors:

- public endpoint is accessible anonymously
- protected endpoint rejects anonymous access
- protected endpoint accepts valid authentication

## Important design notes

- The application now uses MySQL as the only datasource.
- `ROLE_USER` and `ROLE_ADMIN` are present, but role-based restrictions are not yet implemented.

## What to learn before Lesson 2

You should be able to answer these questions:

1. What is the difference between authentication and authorization?
2. Why is BCrypt needed?
3. Why does `/api/public/hello` work without login?
4. Why does `/api/user/profile` fail for anonymous users?
5. Where does Spring Security keep the authenticated user during request processing?

## Next lesson

Lesson 2 will add role-based authorization so that we can explicitly separate `USER` and `ADMIN` access.
