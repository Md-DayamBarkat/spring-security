# Spring Security One-Shot Revision Notes

## One-Line Summary

This project shows Spring Security architecture, JWT authentication, role and permission based authorization, method security, user management, and optional Google OAuth2 login.

## Super Short Notes

- Spring Security protects requests before controllers run.
- Authentication means verifying identity.
- Authorization means deciding access.
- H2 is used to store users for learning.
- `CustomUserDetailsService` loads users from DB.
- `AuthenticationManager` authenticates login requests.
- JWT token is created after successful login.
- `JwtAuthenticationFilter` checks token on later requests.
- roles and permissions are converted to authorities.
- `@PreAuthorize` checks before method.
- `@PostAuthorize` checks after method.

## Main Classes

- [SecurityConfig.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/config/SecurityConfig.java:1)
- [JwtUtil.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/security/JwtUtil.java:1)
- [JwtAuthenticationFilter.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/security/JwtAuthenticationFilter.java:1)
- [CustomUserDetailsService.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/service/CustomUserDetailsService.java:1)
- [WeatherController.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/controller/WeatherController.java:1)
- [UserController.java](/C:/Users/osndl.dayamb/Downloads/spring-security/spring-security/src/main/java/com/app/security/spring_security/controller/UserController.java:1)

## Default Users

- `admin / admin123`
- `weatheruser / password`

## Main Permissions

- `WEATHER_READ`
- `WEATHER_WRITE`
- `WEATHER_DELETE`
- `USER_CREATE`

## Fast Revision

1. Login API authenticates user.
2. JWT token is returned.
3. Client sends token in bearer header.
4. JWT filter validates token.
5. Security context gets authenticated user.
6. Authorization checks role and permissions.
7. Method security protects controller/service methods.

## Important Status Codes

- `200` = success
- `401` = not authenticated
- `403` = authenticated but not allowed

## OAuth2 Reminder

Google OAuth2 part is optional in this repo.

Why:

- it needs real Google client credentials
- without them, the app cannot complete Google login

## 7 Things to Remember

1. `SecurityFilterChain` is the central security config.
2. `AuthenticationManager` handles authentication.
3. `DaoAuthenticationProvider` works with DB-backed users.
4. JWT is stateless.
5. Roles are broad access levels.
6. Permissions are exact allowed actions.
7. Method security gives fine-grained control.
