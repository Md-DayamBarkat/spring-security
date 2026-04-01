# Learning Roadmap

This project will be expanded in controlled phases so that every security concept is backed by runnable code.

## Phase 1: Foundations

1. Understand Spring Security defaults
2. Add explicit `SecurityFilterChain`
3. Create public and protected endpoints
4. Use in-memory users
5. Use BCrypt password encoding
6. Test authentication behavior

## Phase 2: Authorization Model

1. Add role-based access rules
2. Add endpoint-level restrictions
3. Introduce method security with `@PreAuthorize`
4. Distinguish roles vs authorities

## Phase 3: Database-Backed Authentication

1. Create `User` entity
2. Create repository and service layers
3. Load users from MySQL
4. Store hashed passwords only
5. Add registration flow

## Phase 4: Stateless APIs

1. Introduce JWT
2. Build login endpoint
3. Validate tokens with a custom filter
4. Move from session-based auth to stateless auth
5. Add refresh-token strategy

## Phase 5: Production Hardening

1. Exception handling
2. CORS and CSRF decisions
3. Security headers
4. Account locking and password policies
5. Audit logging
6. Secret management
7. Test strategy and regression coverage

## Phase 6: Production Readiness

1. Threat modeling
2. Secure deployment patterns
3. Database migration strategy
4. Monitoring and alerting
5. Incident response mindset

## Learning rule

Every lesson should leave the project in a runnable state with updated documentation and tests.
