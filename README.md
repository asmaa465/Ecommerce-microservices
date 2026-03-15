# 🛒 ecommerce-microservices

<div align="center">

![Java](https://img.shields.io/badge/Java_17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

A production-ready **microservices architecture** built with Spring Boot & Spring Cloud — featuring service discovery, API Gateway, centralized configuration, circuit breaking, and real-time monitoring.

</div>

---

## 📌 Project Structure

```
ecommerce-microservices/
├── 📦 api-gateway/            # Central entry point — routes all incoming requests
├── 🛍️  shop-service/           # Manages shop operations & orders
├── 💳 wallet-service/          # Handles wallets & payments
├── 📦 inventory-service/       # Manages stock & product inventory
├── 🔍 eureka-server/           # Service discovery with Netflix Eureka
├── ⚙️  config-server/           # Centralized config with Spring Cloud Config
├── 🖥️  admin-server/            # Monitoring & management with Spring Boot Admin
└── 📄 README.md
```

---

## ⚙️ Key Features

| # | Feature | Description |
|---|---------|-------------|
| 1 | 🔍 **Service Discovery** | All services auto-register with Netflix Eureka |
| 2 | 🚪 **API Gateway** | Single entry point with Spring Cloud Gateway routing |
| 3 | ⚙️ **Centralized Config** | Runtime config management via Spring Config Server |
| 4 | 🛍️ **Core Microservices** | Shop, Wallet, and Inventory as independent services |
| 5 | 🖥️ **Admin Dashboard** | Visual health monitoring via Spring Boot Admin |
| 6 | 🔄 **Circuit Breaker** | Fault tolerance with Resilience4j to prevent cascading failures |
| 7 | 📊 **Resilience Dashboard** | Real-time circuit breaker state visualization |
| 8 | 🧪 **Fault Simulation** | Circuit breaker tested under real failure scenarios |

---

## 🏗️ Architecture Overview

```
                          ┌─────────────────┐
                          │   Config Server  │  ← Centralized configuration
                          └────────┬────────┘
                                   │
        Client Request             ▼
            │           ┌─────────────────┐
            └──────────►│   API Gateway   │  ← Single entry point
                        └────────┬────────┘
                                 │
               ┌─────────────────┼─────────────────┐
               ▼                 ▼                   ▼
        ┌─────────────┐  ┌─────────────┐  ┌─────────────────┐
        │ Shop Service│  │Wallet Service│  │Inventory Service│
        └─────────────┘  └─────────────┘  └─────────────────┘
               │                 │                   │
               └─────────────────┼───────────────────┘
                                 │
                     ┌───────────▼──────────┐
                     │    Eureka Server      │  ← Service Registry
                     └───────────────────────┘
                     ┌───────────────────────┐
                     │    Admin Server        │  ← Monitoring UI
                     └───────────────────────┘
```

---

## 📬 API Endpoints

| Service | Endpoint (via Gateway) | Description |
|---------|------------------------|-------------|
| 🛍️ Shop | `/shop/**` | Manage orders & shopping cart |
| 💳 Wallet | `/wallet/**` | Handle wallets & payments |
| 📦 Inventory | `/inventory/**` | Manage stock & products |
| ⚙️ Config | `/config/**` | Centralized configuration |
| 🖥️ Admin | `/admin/**` | Service monitoring dashboard |

---

## 🚀 Technologies Used

- **Java 17+**
- **Spring Boot** — Microservice foundation
- **Spring Cloud Gateway** — API routing and load balancing
- **Netflix Eureka** — Service discovery and registration
- **Spring Cloud Config Server** — Externalized configuration
- **Spring Boot Admin** — Microservice monitoring UI
- **Resilience4j** — Circuit breaker and fault tolerance
- **Maven** — Dependency management and build tool

---

## 📊 Monitoring & Resilience

### 🖥️ Spring Boot Admin
Provides a live UI to monitor the **health, metrics, and status** of all registered microservices.

### 🔄 Resilience4j Circuit Breaker
Prevents cascading failures across services by short-circuiting calls to unhealthy services, ensuring system stability under fault conditions.

### 📊 Resilience4j Dashboard
Visualizes circuit breaker states (CLOSED, OPEN, HALF-OPEN) and call metrics in real time.

---

## 🏃 Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+

### Run the services in this order:

```bash
# 1. Start Config Server first
cd config-server && mvn spring-boot:run

# 2. Start Eureka Server
cd eureka-server && mvn spring-boot:run

# 3. Start microservices (in any order)
cd shop-service     && mvn spring-boot:run
cd wallet-service   && mvn spring-boot:run
cd inventory-service && mvn spring-boot:run

# 4. Start API Gateway
cd api-gateway && mvn spring-boot:run

# 5. Start Admin Server
cd admin-server && mvn spring-boot:run
```

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).

---

<div align="center">
  Made with using Spring Boot & Spring Cloud
</div>
