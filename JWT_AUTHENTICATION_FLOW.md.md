# JWT Authentication Flow – Identity Service

This explains how JWT authentication works internally when the server receives a request and sends a response.

---

# 1️⃣ Login Flow (Token Generation)

When a user logs in:

1. Client sends POST request to:
   /api/auth/login

2. AuthController receives the request.

3. UserService:
   - Fetches user from database.
   - Verifies password using BCryptPasswordEncoder.
   - If credentials are valid → calls JwtUtil.generateToken().

4. JwtUtil:
   - Sets subject (email)
   - Adds role claim
   - Sets issued date
   - Sets expiration time
   - Signs token using secret key 
   - Returns compact JWT string

5. Token is returned in response body.

Client stores the token for future requests.

---

# 2️⃣ Secured Request Flow (Token Validation)

When accessing protected endpoints:

1. Client sends request with header:

   Authorization: Bearer <JWT_TOKEN>

2. JwtAuthenticationFilter intercepts the request.

3. Filter:
   - Extracts token from header.
   - Calls JwtUtil.extractUsername().
   - Validates token using JwtUtil.isTokenValid().

4. If token is valid:
   - CustomUserDetailsService.loadUserByUsername() is called.
   - User details are fetched from database.
   - Authentication object is created.
   - SecurityContextHolder is updated.

5. Request proceeds to controller.

6. Spring Security checks role authorization.

7. If authorized → response returned.
   If not → 403 Forbidden.

---

# 3️⃣ JWT Authentication Flow Diagram

Login Flow:

Client  
  ↓  
AuthController  
  ↓  
UserService  
  ↓  
JwtUtil (Generate Token)  
  ↓  
JWT Returned  

---

Secured Request Flow:

Client (Request with JWT)  
  ↓  
JwtAuthenticationFilter  
  ↓  
JwtUtil (Validate Token)  
  ↓  
CustomUserDetailsService  
  ↓  
SecurityContext Updated  
  ↓  
Controller  
  ↓  
Response  

---

# 4️⃣ Security Folder – Class Responsibilities

## JwtUtil

Purpose:
- Generate JWT token
- Extract username from token
- Validate token signature and expiration

Key Methods:
- generateToken()
- extractUsername()
- isTokenValid()

---

## JwtAuthenticationFilter

Purpose:
- Intercepts every HTTP request
- Extracts JWT from Authorization header
- Validates token
- Sets authentication in SecurityContext

Ensures stateless authentication.

---

## CustomUserDetailsService

Implements:
UserDetailsService

Purpose:
- Loads user from database by email
- Converts User entity into Spring Security UserDetails
- Used during authentication and token validation

---

## SecurityBeansConfig

Purpose:
- Defines PasswordEncoder bean (BCryptPasswordEncoder)
- Defines AuthenticationManager bean

---

## SecurityConfig

Purpose:
- Disables CSRF
- Sets session policy to STATELESS
- Defines role-based authorization rules
- Registers JwtAuthenticationFilter in filter chain

---

# 5️⃣ Exception Handling During Authentication

GlobalExceptionHandler handles:

- Invalid credentials
- User already exists
- User not found
- Validation errors

Provides structured API responses.

---

# 6️⃣ Key Concepts Used

- Stateless Authentication
- JWT (JSON Web Token)
- Role-Based Access Control (RBAC)
- Filter Chain
- Security Context
- BCrypt Password Encoding

---

# Conclusion

The Identity Service uses a stateless JWT-based authentication mechanism.  
Authentication and authorization are managed using Spring Security filters and custom service implementations to ensure secure and scalable access control.