# Spring Security One-Shot Learning Project

This repository is rebuilt from scratch to follow the single video:

`Master Spring Security In One Shot 2026 (JWT + OAuth2 + Authorization, Roles & More)`

## What This Project Covers

- Spring Security architecture basics
- H2-backed users with custom `UserDetailsService`
- basic authentication concepts
- JWT login and JWT authentication filter
- role and permission based authorization
- method security with `@PreAuthorize` and `@PostAuthorize`
- user registration and admin user creation APIs
- optional Google OAuth2 wiring

## Current End State

The runnable project is left in the final practical state of the video:

- public health endpoint
- JWT login endpoint
- protected weather APIs
- permission-based method security
- public user registration
- admin-only user creation
- optional OAuth2 demo endpoints

## Main Files

- [SecurityConfig.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/config/SecurityConfig.java:1)
- [OAuth2SecurityConfig.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/config/OAuth2SecurityConfig.java:1)
- [JwtAuthenticationFilter.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/security/JwtAuthenticationFilter.java:1)
- [JwtUtil.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/security/JwtUtil.java:1)
- [WeatherController.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/controller/WeatherController.java:1)
- [UserController.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/controller/UserController.java:1)

## Default Users

- `admin / admin123`
- `weatheruser / password`

## Useful Endpoints

- `GET /api/public/health`
- `POST /api/auth/login`
- `GET /api/weather`
- `POST /api/weather`
- `DELETE /api/weather/{city}`
- `GET /api/weather/logs/{logId}`
- `POST /api/users/register`
- `POST /api/users/admin/create`
- `GET /oauth-demo/public`
- `GET /oauth-demo/private`

## Run

```bash
mvn test
mvn spring-boot:run
```

## Notes

- H2 is enabled for learning.
- JWT is active by default.
- Google OAuth2 is wired as an optional section because it needs your own Google client ID and client secret.

## Study Notes

- [Video Beginner Notes](docs/VIDEO-ONESHOT-BEGINNER-NOTES.md)
- [Video Revision Notes](docs/VIDEO-ONESHOT-REVISION.md)
