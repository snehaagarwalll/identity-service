# Identity Service

## Overview

The Identity Service is responsible for user authentication and authorization within the system. It manages user registration, login, password encryption, JWT token generation, and role-based access control.

The service follows a stateless authentication mechanism using JSON Web Tokens (JWT) to secure protected endpoints.

---

## Core Responsibilities

- User Registration
- Secure Password Storage (BCrypt)
- User Login Authentication
- JWT Token Generation
- JWT Validation
- Role-Based Access Control (ADMIN, SELLER, CUSTOMER)
- Global Exception Handling
- Request Validation

---

## Architecture Overview

This service is designed as a standalone microservice that handles identity and access management for the overall system.

It follows a layered architecture:

Controller  → Handles HTTP requests

Service     → Business logic

Repository  → Database interaction

Security    → JWT & Authentication logic

Model       → Entity classes

DTO         → Request/Response models

Exception   → Centralized error handling

Config      → Security configuration

---

## Authentication Mechanism

This service uses JWT-based stateless authentication.

### Login Flow

1. User submits login credentials.
2. Credentials are validated.
3. A JWT token is generated.
4. The token is returned to the client.

### Secured Request Flow

1. Client sends request with header:

   Authorization: Bearer <JWT_TOKEN>

2. Token is validated.
3. User authentication is set in SecurityContext.
4. Role-based authorization rules are applied.
5. Response is returned.

---

## Role-Based Authorization

The following role restrictions are configured:

| Endpoint Pattern       | Access Role |
|------------------------|------------|
| /api/admin/**          | ADMIN      |
| /api/seller/**         | SELLER     |
| /api/customer/**       | CUSTOMER   |
| /api/auth/**           | Public     |

---

## Security Features

- Stateless session management
- JWT signature validation
- Token expiration handling
- Password hashing using BCrypt
- Custom JWT authentication filter
- Centralized exception handling

---

## Technologies Used

- Spring Boot
- Spring Security
- JWT 
- Spring Data JPA
- Lombok
- Maven

---

## Running the Service

1. Clone the repository
2. Navigate to the project directory
3. Run: mvn spring-boot:run
4. Use Postman to test authentication endpoints

---