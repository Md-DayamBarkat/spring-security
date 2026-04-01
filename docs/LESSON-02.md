# Lesson 2: Role-Based Authorization

Lesson 2 adds authorization rules based on roles.

## Objective

Learn how Spring Security decides whether an authenticated user is allowed to access a specific endpoint.

By the end of this lesson, you should understand:

- the difference between `401 Unauthorized` and `403 Forbidden`
- how `hasRole("USER")` and `hasRole("ADMIN")` work
- why authenticated users can still be denied access
- how Spring stores roles as authorities
- why role hierarchy does not exist automatically

## What changed

### 1. Role-based request rules

File: `src/main/java/com/app/security/spring_security/config/SecurityConfig.java`

Current rules:

- `/api/public/**` is open to everyone
- `/api/user/**` requires `ROLE_USER`
- `/api/admin/**` requires `ROLE_ADMIN`
- every other endpoint requires authentication

## 2. New endpoints

File: `src/main/java/com/app/security/spring_security/controller/LearningController.java`

Endpoints now available:

- `GET /api/public/hello`
- `GET /api/user/profile`
- `GET /api/user/dashboard`
- `GET /api/admin/dashboard`

Behavior:

- `public/hello` is open
- `user/profile` requires any authenticated user who matches `/api/user/**` rules
- `user/dashboard` requires `ROLE_USER`
- `admin/dashboard` requires `ROLE_ADMIN`

## Authentication vs authorization

This lesson is where the distinction becomes important.

Authentication answers:

- who are you?

Authorization answers:

- what are you allowed to access?

Examples:

- no credentials on a protected endpoint gives `401`
- valid login with the wrong role gives `403`

## Understanding `hasRole`

When you write:

```java
hasRole("USER")
```

Spring Security checks for the authority:

```text
ROLE_USER
```

That is why `roles("USER")` in the in-memory user setup becomes `ROLE_USER` internally.

## `hasRole` vs `hasAuthority`

`hasRole("USER")`

- convenient shorthand
- Spring automatically prefixes it as `ROLE_USER`

`hasAuthority("ROLE_USER")`

- more explicit
- checks the exact authority string

For learning, `hasRole` is easier first. Later, when we model permissions such as `READ_REPORTS` or `CREATE_USER`, `hasAuthority` becomes more useful.

## Important behavior in this project

The `adminuser` has only `ROLE_ADMIN`.

That means:

- `adminuser` can access `/api/admin/dashboard`
- `adminuser` cannot access `/api/user/dashboard`

This surprises many beginners. Spring Security does not automatically assume that admin includes user privileges.

If you want that behavior, you must define a role hierarchy explicitly.

## Automated tests

File: `src/test/java/com/app/security/spring_security/LearningControllerSecurityTests.java`

The tests now verify:

- public endpoint works anonymously
- protected endpoint rejects anonymous access
- authenticated access works for valid credentials
- `ROLE_USER` can access user endpoints
- `ROLE_ADMIN` cannot access user endpoints without hierarchy
- `ROLE_USER` cannot access admin endpoints
- `ROLE_ADMIN` can access admin endpoints

## Manual test examples

User endpoint as app user:

```bash
curl -i -u appuser:password http://localhost:8080/api/user/dashboard
```

Admin endpoint as app user:

```bash
curl -i -u appuser:password http://localhost:8080/api/admin/dashboard
```

Admin endpoint as admin user:

```bash
curl -i -u adminuser:admin123 http://localhost:8080/api/admin/dashboard
```

User endpoint as admin user:

```bash
curl -i -u adminuser:admin123 http://localhost:8080/api/user/dashboard
```

## What to learn before Lesson 3

You should be able to answer these questions:

1. Why does a logged-in user sometimes get `403`?
2. What does `hasRole("USER")` check internally?
3. Why can `adminuser` not access the user endpoint in this project?
4. When would you choose `hasAuthority` over `hasRole`?
5. What is the difference between authentication failure and authorization failure?

## Next lesson

Lesson 3 will move authentication data into MySQL by introducing a real `User` entity, repository, service, and password persistence model.
