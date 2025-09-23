# 🛒 Spring Boot Microservices with API Gateway & JWT Authentication

This project demonstrates a **microservices-based architecture** using **Spring Boot**, **Spring Cloud Gateway**, and **Eureka Service Discovery**.
It includes **JWT authentication**, centralized routing, and three core microservices: **Shop**, **Wallet**, and **Inventory**.

---

## 🚀 Features

* **API Gateway** with Spring Cloud Gateway
* **JWT Authentication** filter (for securing routes)
* **Eureka Service Discovery** for service registration
* **Three microservices**:

  * 🛍️ **Shop Service** → Manage carts & orders
  * 💰 **Wallet Service** → Handle users, payments & wallets
  * 📦 **Inventory Service** → Manage stock & products
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
+-----------+          +--------------+        +--------------+
| Shop MS   |          | Wallet MS    |        | Inventory MS |
| (Port 8083)|         | (Port 8082)  |        | (Port 8081)  |
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
* **MySQL**

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

* `POST /wallet/User/user/register` → Register a new user
* `POST /wallet/User/user/login` → Authenticate & get JWT token
* All other requests require `Authorization: Bearer <token>` header
* Gateway injects `X-User-Email` header (from JWT) into downstream requests

---

## 📬 API Endpoints

All endpoints are accessed **through the API Gateway** (`http://localhost:8088`).
JWT is required for all endpoints except `/user/register` and `/user/login`.

---

### 🛍️ Shop Service (`/shop/**`)

**Shopping Cart**

* `POST /shop/cart/create/{userId}` → Create a shopping cart for a user
* `GET /shop/cart` → Get all shopping carts
* `GET /shop/cart/{id}` → Get shopping cart by ID
* `DELETE /shop/cart/{shoppingCartId}` → Delete shopping cart
* `POST /shop/cart/{shoppingCartId}/add-item?productId=&quantity=` → Add item to cart
* `DELETE /shop/cart/{shoppingCartId}/remove-item/{cartItemId}` → Remove item from cart

**Orders**

* `POST /shop/orders/{orderId}/add-item?productId=&quantity=` → Add item to order
* `DELETE /shop/orders/{orderId}/remove-item/{orderItemId}` → Remove item from order
* `POST /shop/orders/from-cart/{shoppingCartId}` → Create order from shopping cart
* `POST /shop/orders/{orderId}/pay` → Pay for an order

---

### 💰 Wallet Service (`/wallet/**`)

**Users**

* `POST /wallet/User/user/register` → Register a new user
* `POST /wallet/User/user/login?email=&password=` → Login and get JWT token
* `GET /wallet/User/user/{id}` → Get user by ID
* `GET /wallet/User/usercheck/{id}` → Check if user exists

**Wallets**

* `POST /wallet/wallet/{id}/WalletCreation` → Create wallet for user
* `POST /wallet/wallet/{id}/Deposit/{amount}` → Deposit money
* `POST /wallet/wallet/{id}/Withdrawal/{amount}` → Withdraw money
* `POST /wallet/wallet/{id}/pay/{amount}` → Pay from wallet
* `GET /wallet/wallet/{id}/Transaction` → Get all wallet transactions

---

### 📦 Inventory Service (`/inventory/**`)

**Inventory**

* `POST /inventory/CreateInventory?productId=&quantity=` → Create inventory
* `GET /inventory` → Get all inventories
* `GET /inventory/{id}` → Get inventory by ID
* `DELETE /inventory/{id}` → Delete inventory
* `POST /inventory/isAvailable/{id}` → Check if product is available
* `PUT /inventory/reduce/{productId}?quantity=` → Reduce stock
* `GET /inventory/ProductQuantity/{productId}` → Get product quantity
* `PUT /inventory/{inventoryId}/setQuantity/{quantity}` → Set inventory quantity

**Products**

* `POST /inventory/products/create` → Add product
* `GET /inventory/products` → Get all products
* `GET /inventory/products/{id}` → Get product by ID
* `PUT /inventory/products/{id}` → Update product
* `DELETE /inventory/products/{id}` → Delete product
* `GET /inventory/products/{id}/price` → Get product price
