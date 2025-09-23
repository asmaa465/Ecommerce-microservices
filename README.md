# ecommerce-microservices Project

This repository demonstrates a microservices-based architecture using **Spring Boot** and **Spring Cloud**.
It includes multiple services, service discovery, centralized configuration, API Gateway, monitoring, and resilience patterns.

---

## 📌 Project Structure

```
ecommerce-microservices-project/
├── api-gateway/          # Central entry point for routing requests
├── shop-service/         # Manages shop operations
├── wallet-service/       # Handles wallet and payments
├── inventory-service/    # Manages stock and product inventory
├── eureka-server/        # Service discovery with Netflix Eureka
├── config-server/        # Centralized configuration with Spring Cloud Config
├── admin-server/         # Monitoring and management with Spring Boot Admin
└── README.md
```

---
## 🏗️ Architecture
                  +-------------------------+
                  |     API Gateway         |
                  | (JWT + Routing Filter)  |
                  +-----------+-------------+
                              |
```

---

\|                  |                      |                      |
+---------+      +-----------+         +-----------+          +------------+
\| Shop MS |      | Wallet MS |         | Inventory |          | Config MS  |
\| (8081)  |      | (8082)    |         | (8083)    |          | (8888)     |
+---------+      +-----------+         +-----------+          +------------+

```
        +-------------------------------------------------+
        |           Eureka Service Discovery              |
        |        (http://localhost:8761/eureka)           |
        +-------------------------------------------------+

        +-------------------------------------------------+
        |           Spring Boot Admin Server              |
        |        (http://localhost:8088/admin)            |
        +-------------------------------------------------+

        +-------------------------------------------------+
        |           Resilience4j Dashboard                |
        |        (http://localhost:8080/actuator)         |
        +-------------------------------------------------+

---

## ⚙️ Key Features

1. **Service Discovery** using **Eureka Server**
2. **API Gateway** with **Spring Cloud Gateway**
3. **Centralized Configuration** using **Spring Config Server**
4. **Shop, Wallet, and Inventory Services** implemented as independent microservices
5. **Admin Server** to monitor microservices health and status
6. **Resilience4j Circuit Breaker** for fault tolerance
7. **Resilience4j Dashboard** to monitor circuit breaker states
8. **Tested Circuit Breaker** by simulating fault scenarios

---

## 📬 API Endpoints
| Service   | Endpoint (via Gateway) | Description                   |
| --------- | ---------------------- | ----------------------------- |
| Shop      | `/shop/**`             | Manage orders & shopping cart |
| Wallet    | `/wallet/**`           | Handle wallets & payments     |
| Inventory | `/inventory/**`        | Manage stock & products       |
| Config    | `/config/**`           | Centralized configuration     |
| Admin     | `/admin/**`            | Service monitoring dashboard  |

---

## 🚀 Technologies Used

* **Java 17+**
* **Spring Boot**
* **Spring Cloud (Eureka, Gateway, Config Server)**
* **Spring Boot Admin**
* **Resilience4j**
* **Maven**

---

## 📊 Monitoring & Resilience

* **Spring Boot Admin** provides a UI to monitor the health of all microservices.
* **Resilience4j Circuit Breaker** ensures fault tolerance by preventing cascading failures.
* A **Resilience4j Dashboard** is configured to visualize circuit breaker metrics.
