# 🛒 Spring Boot Microservices with API Gateway, JWT, Config Server & Admin Server

This project demonstrates a **microservices-based architecture** using **Spring Boot**, **Spring Cloud Gateway**, **Eureka Service Discovery**, and **Spring Cloud Config**.
It includes **JWT authentication**, centralized configuration, monitoring with **Spring Boot Admin**, and five core microservices: **Shop**, **Wallet**, **Inventory**, **Config Server**, and **Admin Server**.

---

## 🚀 Features

* **API Gateway** with Spring Cloud Gateway
* **JWT Authentication** filter (for securing routes)
* **Eureka Service Discovery** for service registration
* **Spring Cloud Config Server** for centralized configuration
* **Spring Boot Admin Server** for monitoring microservices
* **Three business microservices**:

  * 🛍️ **Shop Service** → Manage shops & products
  * 💰 **Wallet Service** → Handle payments & wallet balances
  * 📦 **Inventory Service** → Manage stock & inventory
* Load balancing via `lb://` URIs
* Centralized authentication with JWT

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
+--------------+        +--------------+       +--------------+
| Shop MS      |        | Wallet MS    |       | Inventory MS |
| (Port 8081)  |        | (Port 8082)  |       | (Port 8083)  |
+--------------+        +--------------+       +--------------+

          +-------------------------------------------+
          |        Eureka Service Discovery           |
          |   (Registry at http://localhost:8761)     |
          +-------------------------------------------+

          +-------------------------------------------+
          |       Spring Cloud Config Server          |
          | (Centralized config at http://localhost)  |
          +-------------------------------------------+

          +-------------------------------------------+
          |         Spring Boot Admin Server          |
          | (Monitoring dashboard at http://localhost)|
          +-------------------------------------------+
```

---

## ⚙️ Tech Stack

* **Java 17+**
* **Spring Boot 3.x**
* **Spring Cloud Gateway**
* **Spring Security (JWT)**
* **Eureka Discovery Server**
* **Spring Cloud Config Server**
* **Spring Boot Admin Server**
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
├── config-server/            # Centralized Config Server
├── admin-server/             # Admin Server for monitoring
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

| Service       | Endpoint (via Gateway) | Description                  |
| ------------- | ---------------------- | ---------------------------- |
| Shop          | `/shop/**`             | Manage products/shops        |
| Wallet        | `/wallet/**`           | Handle wallets/payments      |
| Inventory     | `/inventory/**`        | Manage stock/inventory       |
| Config Server | `/config/**`           | Centralized configuration    |
| Admin Server  | `/admin/**`            | Service monitoring dashboard |
