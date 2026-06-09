# Microservices E-Commerce System

A microservices-based backend system built with Spring Boot and Spring Cloud, simulating a basic e-commerce workflow across three independent services.

---

## Architecture Overview

```
Client
  ↓
API Gateway (port 8080)        ← single entry point
  ↓
Eureka Server (port 8761)      ← service discovery
  ↓
┌─────────────────────────────┐
│  User Service   (port 8081) │
│  Product Service(port 8082) │
│  Order Service  (port 8083) │
└─────────────────────────────┘
```

Services communicate internally using **OpenFeign**. When Order Service needs to validate a user or fetch a product, it calls the relevant service by name — Eureka resolves the actual address at runtime.

---

## Services

### Eureka Server — `port 8761`
Service registry. All microservices register here on startup so they can discover each other by name.

<img width="1913" height="691" alt="eureka" src="https://github.com/user-attachments/assets/a703f58d-7028-4645-8c7a-1d8ef1d6ab81" />

---

### API Gateway — `port 8080`
Single entry point for all external requests. Routes to the correct service using Eureka service names.

The gateway supports two routing styles:

| Style | Example URL | How it works |
|---|---|---|
| Service name prefix | `/ORDER-SERVICE/orders/1` | Auto-routed by Eureka discovery locator |
| Clean path | `/orders/1` | Matched by explicit route config |

Both reach the same service. The service name prefix style works out of the box; clean paths require explicit route configuration in `application.yml`.

---

### User Service — `port 8081`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/users` | Create a user |
| `GET` | `/users/{id}` | Get user by ID |

---

### Product Service — `port 8082`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/products` | Create a product |
| `GET` | `/products/{id}` | Get product by ID |

---

### Order Service — `port 8083`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/orders` | Create an order |
| `GET` | `/orders/{id}` | Get order by ID |

**Order creation flow:**
1. Validate user exists → calls User Service via Feign
2. Fetch product → calls Product Service via Feign
3. Check stock availability
4. Calculate total price
5. Save and return order

---

## API Examples

### Create an order (via gateway)

```
POST http://localhost:8080/ORDER-SERVICE/orders
Content-Type: application/json

{
  "userId": 1,
  "productId": 2,
  "quantity": 3
}
```

<img width="1120" height="792" alt="image" src="https://github.com/user-attachments/assets/c2570da0-6899-477e-94e8-87e1e15d169c" />


---

### Get an order (via gateway)

```
GET http://localhost:8080/ORDER-SERVICE/orders/1
```

<img width="1145" height="792" alt="image" src="https://github.com/user-attachments/assets/17bc66eb-e153-48c3-8948-feda65db5a43" />


---

### Resource not found (exception handling)

```
GET http://localhost:8080/ORDER-SERVICE/orders/999
```




<img width="1110" height="746" alt="image" src="https://github.com/user-attachments/assets/3f6a95e0-a00d-4727-9267-59efd7a5925d" />


---

## Inter-Service Communication (OpenFeign)

The Order Service communicates with the User Service and Product Service using OpenFeign, a declarative HTTP client that simplifies REST calls.

Instead of writing HTTP requests manually, we define interfaces and call them like normal Java methods.

Feign + Eureka work together — `USER-SERVICE` , `PRODUCT-SERVICE` are resolved to the actual IP and port at runtime. If the service moves or its port changes, no code needs to change.

**Flow when creating an order:**

```
Order Service
  ├── userClient.getUser(userId)       → calls User Service
  └── productClient.getProduct(productId) → calls Product Service
```

---

## Access Points

| Service | URL |
|---|---|
| Eureka Dashboard | http://localhost:8761 |
| API Gateway | http://localhost:8080 |
---


## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot |
| Gateway | Spring Cloud Gateway (WebFlux) |
| Service Discovery | Spring Cloud Netflix Eureka |
| Inter-service calls | OpenFeign |
| Database | H2 (in-memory) |
| ORM | Spring Data JPA |
| Validation | Jakarta Bean Validation |
| Logging | SLF4J |

---

## How to Run

1. **Build all services** (first time only)

```bash
mvn clean package -DskipTests
```

2. **Start all services with Docker Compose**

```bash
docker compose up --build
```

Verify all services are registered at `http://localhost:8761` before making requests.

---

## Features

- Service discovery via Eureka
- Centralized routing via API Gateway
- Inter-service communication via OpenFeign
- Input validation (`@Valid`, `@NotNull`, `@Min`)
- Centralized exception handling
- H2 in-memory database (no setup required)
