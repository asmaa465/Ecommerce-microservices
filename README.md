# 🛒 Spring Boot Microservices with API Gateway & JWT Authentication

This project demonstrates a **microservices-based architecture** using **Spring Boot**, **Spring Cloud Gateway**, and **Eureka Service Discovery**.
It includes **JWT authentication**, centralized routing, and three core microservices: **Shop**, **Wallet**, and **Inventory**.

---

## 🚀 Features

* **API Gateway** with Spring Cloud Gateway
* **JWT Authentication** filter (for securing routes)
* **Eureka Service Discovery** for service registration
* **Three microservices**:

  * 🛍️ **Shop Service** → Manage shops & products
  * 💰 **Wallet Service** → Handle payments & wallet balances
  * 📦 **Inventory Service** → Manage stock & inventory
* Centralized authentication with JWT
* Load balancing via `lb://` URIs

---

## 🏗️ Architecture

```
                  +---------------------+
                  |   API Gateway       |
                  |  (JWT Auth Filter)  |
                  +----------+----------+
                             |
      -------------------------------------------------
      |                        |                      |
+-----------+          +--------------+        +--------------+
| Shop MS   |          | Wallet MS    |        | Inventory MS |
| (Port 8081)|         | (Port 8082)  |        | (Port 8083)  |
+-----------+          +--------------+        +--------------+

         +--------------------------------------------+
         |        Eureka Service Discovery            |
         | (Registry at http://localhost:8761)        |
         +--------------------------------------------+
```

---

## ⚙️ Tech Stack

* **Java 17+**
* **Spring Boot 3.x**
* **Spring Cloud Gateway**
* **Spring Security (JWT)**
* **Eureka Discovery Server**
* **Maven**

---

## 📂 Project Structure

```
.
├── api-gateway/              # Spring Cloud Gateway with JWT filter
├── shop-service/             # Shop microservice
├── wallet-service/           # Wallet microservice
├── inventory-service/        # Inventory microservice
├── eureka-server/            # Eureka Discovery Server
└── README.md
```

---

## 🔑 Authentication Flow

* `POST /user/register` → Register a new user
* `POST /user/login` → Authenticate & get JWT token
* All other requests require `Authorization: Bearer <token>` header
* Gateway adds `X-User-Email` header (from JWT) to downstream services

---

## 📬 API Endpoints

| Service   | Endpoint (via Gateway) | Description             |
| --------- | ---------------------- | ----------------------- |
| Shop      | `/shop/**`             | Manage products/shops   |
| Wallet    | `/wallet/**`           | Handle wallets/payments |
| Inventory | `/inventory/**`        | Manage stock/inventory  |

