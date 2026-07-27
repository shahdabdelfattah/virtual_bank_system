# Virtual Bank System

A distributed banking application built using Spring Boot and a microservices architecture. The system simulates core banking operations including user management, account management, money transfers, logging, and a Backend-for-Frontend (BFF) service.

## Architecture

The system consists of the following microservices:

- User Service
- Account Service
- Transaction Service
- Logging Service
- BFF Service

Supporting infrastructure:

- PostgreSQL
- Apache Kafka
- Kafka UI
- Docker & Docker Compose

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- PostgreSQL
- Apache Kafka
- Docker
- Docker Compose
- Maven

---

## Project Structure

```
virtual_bank/
│
├── user-service/
├── account-service/
├── transaction-service/
├── logging-service/
├── bff-service/
│
├── postgres/
│   └── init/
│
├── docker-compose.yml
├── .env.example
└── README.md
```

---

# Microservices

## User Service

Responsible for authentication and user management.

### Features

- User registration
- User login
- JWT authentication
- Password hashing using BCrypt
- Retrieve user profile

### Main Endpoints

```
POST /users/register
POST /users/login
GET  /users/{userId}/profile
```

---

## Account Service

Responsible for bank account management.

### Features

- Create account
- Retrieve account by ID
- Retrieve all user accounts
- Transfer balance between accounts
- Scheduled inactive account detection

### Main Endpoints

```
POST /accounts
GET  /accounts/{accountId}
GET  /users/{userId}/accounts
PUT  /accounts/transfer
```

---

## Transaction Service

Responsible for money transfer processing.

### Features

- Initiate transfer
- Execute transfer
- Transaction history
- Integration with Account Service
- Daily interest calculation using Spring Scheduler

### Main Endpoints

```
POST /transactions/transfer/initiation
POST /transactions/transfer/execution
GET  /accounts/{accountId}/transactions
```

---

## Logging Service

Consumes Kafka events and stores application logs.

### Features

- Kafka Consumer
- PostgreSQL persistence
- Centralized logging

---

## Backend For Frontend (BFF)

Provides a single endpoint for frontend applications by aggregating data from multiple services.

### Dashboard Response

Returns:

- User information
- User accounts
- Recent transactions for each account

### Endpoint

```
GET /bff/dashboard/{userId}
```

---

## Prerequisites

Install:

- Java 21
- Maven 3.9+
- Docker Desktop

---

## Environment Variables

Create a `.env` file in the project root.

Example:

```properties
# PostgreSQL
POSTGRES_USER= your_username
POSTGRES_PASSWORD= your_password
POSTGRES_PORT=...

# Kafka
KAFKA_PORT=...
KAFKA_UI_PORT=...
LOGGING_TOPIC= topic_name

# databases
USER_DB= db_name
ACCOUNT_DB= db_name
TRANSACTION_DB= db_name
LOGGING_DB= db_name

# services
USER_SERVICE_PORT=...
LOGGING_SERVICE_PORT=...
TRANSACTION_SERVICE_PORT=...
BFF_SERVICE_PORT=...
ACCOUNT_SERVICE_PORT=...
```

---

## Running the Project

### 1. Clone the repository

```bash
git clone <repository-url>
cd virtual_bank
```

---

### 2. Build all services

```bash
cd user-service
mvn clean package -DskipTests

cd ../account-service
mvn clean package -DskipTests

cd ../transaction-service
mvn clean package -DskipTests

cd ../logging-service
mvn clean package -DskipTests

cd ../bff-service
mvn clean package -DskipTests

cd ..
```

---

### 3. Start the system

```bash
docker compose up --build
```

To run in detached mode:

```bash
docker compose up -d --build
```

---

## Available Services

| Service | URL |
|----------|-----|
| User Service | http://localhost:8081 |
| Logging Service | http://localhost:8082 |
| Transaction Service | http://localhost:8083 |
| Kafka UI | http://localhost:8085 |
| BFF Service | http://localhost:8086 |
| Account Service | http://localhost:8087 |

---

## API Endpoints

### User Service

| Method | Endpoint |
|---------|----------|
| POST | /users/register |
| POST | /users/login |
| GET | /users/{id}/profile |

---

### Account Service

| Method | Endpoint |
|---------|----------|
| POST | /accounts |
| GET | /accounts/{id} |
| GET | /users/{userId}/accounts |
| PUT | /accounts/transfer |

---

### Transaction Service

| Method | Endpoint |
|---------|----------|
| POST | /transactions/transfer/initiation |
| POST | /transactions/transfer/execution |
| GET | /accounts/{accountId}/transactions |

---

### BFF Service

| Method | Endpoint |
|---------|----------|
| GET | /bff/dashboard/{userId} |

---

## Docker

Build and start all services:

```bash
docker compose up --build
```

Stop containers:

```bash
docker compose down
```

Stop containers and remove volumes:

```bash
docker compose down -v
```

---

## Authors
- Alaa Mostafa
- Salma Mohamed Hafez
- Shahd Ahmed Abdelfatah

