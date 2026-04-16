# Spring Security One-Shot Beginner Notes

Video:
`Master Spring Security In One Shot 2026 (JWT + OAuth2 + Authorization, Roles & More)`

## Goal of the Video

This video teaches Spring Security from big picture to practical implementation.

It covers:

- architecture
- authentication
- authorization
- JWT
- method security
- user management
- OAuth2 with Google

## Part 1. Spring Security Architecture

### What is Spring Security?

Spring Security is a framework that protects your application.

It protects:

- APIs
- web applications
- user data

### What is Authentication?

Authentication means:

`Who are you?`

Example:

- username and password are checked
- if correct, user is authenticated

### What is Authorization?

Authorization means:

`What are you allowed to do?`

Example:

- user can read data
- admin can read, create, update, delete

### High-level flow

Simple flow:

`Request -> Security Filters -> Authentication -> Authorization -> Controller -> Response`

Important names from the video:

- `SecurityFilterChain`
- `AuthenticationManager`
- `AuthenticationProvider`
- `SecurityContextHolder`

## Part 2. Basic Authentication

When Spring Security dependency is added:

- app becomes secured automatically
- default user is created
- generated password appears in logs

Then the video moves to custom configuration.

Important idea:

- basic auth sends username and password with requests

In this rebuilt project, basic auth is taught in notes, but the final runnable flow is JWT-based because the video moves forward to JWT.

## Part 3. Database-backed Users

The video stores users in database.

In this project:

- H2 database is used for learning
- users are stored in `app_users`
- `AppUserRepository` loads them
- `CustomUserDetailsService` converts them to Spring Security `UserDetails`

Files:

- [AppUser.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/entity/AppUser.java:1)
- [AppUserRepository.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/repository/AppUserRepository.java:1)
- [CustomUserDetailsService.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/service/CustomUserDetailsService.java:1)

## Part 4. JWT Authentication

### Why JWT is used

Basic auth is simple, but JWT is better for many APIs.

JWT helps because:

- client logs in once
- server returns token
- client sends token on next requests
- server validates token

### JWT flow

1. User sends username and password to login API
2. App authenticates user
3. App creates JWT token
4. Client stores token
5. Client sends token in `Authorization: Bearer <token>`
6. JWT filter validates token
7. Request continues

Files:

- [AuthController.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/controller/AuthController.java:1)
- [JwtUtil.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/security/JwtUtil.java:1)
- [JwtAuthenticationFilter.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/security/JwtAuthenticationFilter.java:1)

## Part 5. Roles and Permissions

The video explains:

- a user has a role
- a role has permissions

In this project:

- `USER`
- `ADMIN`

Permissions:

- `WEATHER_READ`
- `WEATHER_WRITE`
- `WEATHER_DELETE`
- `USER_CREATE`

Files:

- [Role.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/enums/Role.java:1)
- [Permission.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/enums/Permission.java:1)

## Part 6. Authorization

Authorization decides whether a logged-in user can access something.

In this project:

- normal user can read weather
- admin can write and delete weather
- admin can create users

Examples:

- `@PreAuthorize("hasAuthority('WEATHER_READ')")`
- `@PreAuthorize("hasAuthority('WEATHER_WRITE')")`
- `@PreAuthorize("hasAuthority('USER_CREATE')")`

## Part 7. Method Security

The video explains `@PreAuthorize` and `@PostAuthorize`.

### `@PreAuthorize`

Checks permission before method runs.

Used in this project on:

- weather read
- weather write
- weather delete
- admin user creation

### `@PostAuthorize`

Checks result after method runs.

Used in this project on weather log access:

- a user can read only their own log
- admin can read any log

File:

- [WeatherService.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/service/WeatherService.java:1)

## Part 8. User Management

The video also covers user registration and admin user creation.

In this project:

- public registration endpoint creates `USER`
- admin creation endpoint can create users with chosen role

Endpoints:

- `POST /api/users/register`
- `POST /api/users/admin/create`

## Part 9. OAuth2 with Google

The video ends with Google OAuth2 login.

Important idea:

- your app uses Google as authorization server
- user logs in with Google
- Google returns control back to your app

In this project, OAuth2 is optional by default because it needs your own Google credentials.

To use it later:

1. create Google OAuth credentials
2. fill client ID and client secret in `application.properties`
3. set `app.security.oauth2.enabled=true`

Files:

- [OAuth2SecurityConfig.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/config/OAuth2SecurityConfig.java:1)
- [OAuth2DemoController.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/controller/OAuth2DemoController.java:1)

## Endpoints to Remember

Public:

- `GET /api/public/health`
- `POST /api/auth/login`
- `POST /api/users/register`

Protected:

- `GET /api/weather`
- `POST /api/weather`
- `DELETE /api/weather/{city}`
- `GET /api/weather/logs/{logId}`
- `POST /api/users/admin/create`

## Very Easy Memory Points

1. Authentication = who are you
2. Authorization = what can you do
3. JWT = token-based authentication
4. Role = high-level identity like admin or user
5. Permission = exact action like read or write
6. `@PreAuthorize` = check before method
7. `@PostAuthorize` = check after method

## What to Practice

1. Login as `admin`
2. Get JWT token
3. Call weather read API
4. Add weather using admin token
5. Try the same with normal user and observe `403`
6. Register a new user
7. Try admin create API with normal user and observe failure
