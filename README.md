# Task S402 - API Rest with Spring Boot

## Description

In this assignment, you will develop three independent Spring Boot applications, each with a REST API that implements a complete CRUD (Create, Read, Update, Delete) over different entities. You will work with three different databases: H2, MySQL, and MongoDB.

Through these exercises, you will learn how to:

- Create REST APIs using Spring Boot.
- Manage data persistence with Spring Data JPA and Spring Data MongoDB.
- Correctly apply HTTP verbs (GET, POST, PUT, DELETE) and properly handle response status codes.
- Implement dynamic routes with Path Params and Query Params.
- Write and execute automated tests using TDD (Test-Driven Development) to ensure the expected behavior of the business logic and endpoints.
- Handle exceptions globally through a `GlobalExceptionHandler`.
- Properly structure the project following the MVC (Model-View-Controller) pattern.
- Create relationships between entities using JPA.
- Introduce the use of DTOs and validate input data with validation annotations.
- Create a Dockerfile to package the application into a Docker image ready for production environments.
- Configure the database connection through environment variables.

## 🛠 Technologies
- Java

##    Project Structure
````bash
├── fruit-api-h2
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── cat
│       │   │       └── itacademy
│       │   │           └── s04
│       │   │               └── s02
│       │   │                   └── n01
│       │   │                       └── fruit
│       │   │                           ├── controller
│       │   │                           │   └── FruitController.java
│       │   │                           ├── dto
│       │   │                           │   ├── FruitMapper.java
│       │   │                           │   ├── FruitRequestDTO.java
│       │   │                           │   └── FruitResponseDTO.java
│       │   │                           ├── exception
│       │   │                           │   ├── ErrorResponse.java
│       │   │                           │   └── GlobalExceptionHandler.java
│       │   │                           ├── FruitApiH2Application.java
│       │   │                           ├── model
│       │   │                           │   └── Fruit.java
│       │   │                           ├── repository
│       │   │                           │   └── FruitRepository.java
│       │   │                           └── service
│       │   │                               ├── FruitServiceImpl.java
│       │   │                               └── FruitService.java
│       │   └── resources
│       │       └── application.properties
│       └── test
│           └── java
│               └── cat
│                   └── itacademy
│                       └── s04
│                           └── s02
│                               └── n01
│                                   └── fruit
│                                       ├── FruitRepositoryTest.java
│                                       ├── FruitsControllerTest.java
│                                       └── FruitsServiceImlTest.java
├── fruit-api-mongo
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── cat
│       │   │       └── itacademy
│       │   │           └── s04
│       │   │               └── t02
│       │   │                   └── n03
│       │   │                       └── fruit
│       │   │                           ├── controller
│       │   │                           │   └── OrderController.java
│       │   │                           ├── dto
│       │   │                           │   ├── OrderItemDTO.java
│       │   │                           │   ├── OrderItemMapper.java
│       │   │                           │   ├── OrderMapper.java
│       │   │                           │   ├── OrderRequestDTO.java
│       │   │                           │   └── OrderResponseDTO.java
│       │   │                           ├── exception
│       │   │                           │   ├── ClientNameIsEmptyException.java
│       │   │                           │   ├── ErrorResponse.java
│       │   │                           │   ├── FruitsEmptyException.java
│       │   │                           │   ├── GlobalExceptionHandler.java
│       │   │                           │   ├── OrderIdDoesNotExists.java
│       │   │                           │   └── OrderNotFoundException.java
│       │   │                           ├── FruitApiMongoApplication.java
│       │   │                           ├── model
│       │   │                           │   ├── OrderItem.java
│       │   │                           │   └── Order.java
│       │   │                           ├── repository
│       │   │                           │   └── OrderRepository.java
│       │   │                           └── service
│       │   │                               ├── OrderServiceImpl.java
│       │   │                               └── OrderService.java
│       │   └── resources
│       │       ├── application.properties
│       │       └── application-test.properties
│       └── test
│           └── java
│               └── cat
│                   └── itacademy
│                       └── s04
│                           └── t02
│                               └── n03
│                                   └── fruit
│                                       ├── FruitApiMongoApplicationTests.java
│                                       ├── OrderControllerTest.java
│                                       ├── OrderRepositoryTest.java
│                                       └── OrderServiceTest.java
├── fruit-api-mysql
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── cat
│       │   │       └── itacademy
│       │   │           └── s04
│       │   │               └── t02
│       │   │                   └── n02
│       │   │                       └── fruit
│       │   │                           └── t02
│       │   │                               └── n02
│       │   │                                   └── fruit
│       │   │                                       ├── controller
│       │   │                                       │   ├── FruitController.java
│       │   │                                       │   └── ProviderController.java
│       │   │                                       ├── dto
│       │   │                                       │   ├── FruitMapper.java
│       │   │                                       │   ├── FruitRequestDTO.java
│       │   │                                       │   ├── FruitResponseDTO.java
│       │   │                                       │   ├── ProviderMapper.java
│       │   │                                       │   ├── ProviderRequestDTO.java
│       │   │                                       │   └── ProviderResponseDTO.java
│       │   │                                       ├── exception
│       │   │                                       │   ├── ErrorResponse.java
│       │   │                                       │   ├── FruitIdDoesNotExists.java
│       │   │                                       │   ├── FruitNameIsEmpty.java
│       │   │                                       │   ├── GlobalExceptionHandler.java
│       │   │                                       │   ├── ProviderHasFruits.java
│       │   │                                       │   ├── ProviderNameAlreadyExists.java
│       │   │                                       │   ├── ProviderNameIsEmpty.java
│       │   │                                       │   └── ProviderNotFound.java
│       │   │                                       ├── FruitApiMysqlApplication.java
│       │   │                                       ├── model
│       │   │                                       │   ├── Fruit.java
│       │   │                                       │   └── Provider.java
│       │   │                                       ├── repository
│       │   │                                       │   ├── FruitRepository.java
│       │   │                                       │   └── ProviderRepository.java
│       │   │                                       └── service
│       │   │                                           ├── FruitServiceImpl.java
│       │   │                                           ├── FruitService.java
│       │   │                                           ├── ProviderServiceImpl.java
│       │   │                                           └── ProviderService.java
│       │   └── resources
│       │       ├── application.properties
│       │       └── application-test.properties
│       └── test
│           └── java
│               └── cat
│                   └── itacademy
│                       └── s04
│                           └── t02
│                               └── n02
│                                   └── fruit
│                                       └── t02
│                                           └── n02
│                                               └── fruit
│                                                   ├── FruitRepositoryTests.java
│                                                   ├── FruitsControllerTests.java
│                                                   ├── FruitsServiceTests.java
│                                                   ├── ProviderRepositoryTests.java
│                                                   ├── ProvidersControllerTests.java
│                                                   └── ProvidersServiceTests.java
├── production
│   ├── fruit-api-h2
│   │   └── fruit-api-h2-0.0.1-SNAPSHOT.jar
│   ├── fruit-api-mongo
│   │   ├── build
│   │   │   └── mongodb
│   │   │       └── Dockerfile
│   │   ├── data
│   │   ├── docker-compose.yml
│   │   └── fruit-api-mongo-0.0.1-SNAPSHOT.jar
│   └── fruit-api-mysql
│       ├── build
│       │   └── mysql
│       │       ├── Dockerfile
│       │       ├── mysqld.cnf
│       │       └── seconddatabase.sql
│       ├── docker-compose.yml
│       └── fruit-api-mysql-0.0.1-SNAPSHOT.jar
└── README.md
````

## Execution

Enter the `production` directory and then the subdirectory of the desired project.

The projects include a Docker setup to host the database. In the case of H2, there is no Docker setup because it runs in memory.

Open Docker with:

```bash
docker compose up -d
```

Run the JAR file with:

```bash
java -jar your-jar-name.jar
```

# Fruit API Endpoint Documentation

This document describes the REST endpoints proposed in the assignment for the three Spring Boot projects. The endpoint design follows common REST conventions: collection-oriented resource names, standard HTTP methods, and appropriate status codes for CRUD operations.

## General conventions

- Base format: JSON request and response bodies are expected for create and update operations, which aligns with common Spring Boot REST API practice.
- Resource naming: plural nouns such as `/fruits`, `/providers`, and `/orders` are consistent with REST naming guidelines.
- Common success codes: `200 OK` for successful reads and updates, `201 Created` for successful creations, and `204 No Content` for successful deletions are standard CRUD patterns.
- Common error codes: `400 Bad Request` is used for invalid input, while `404 Not Found` is used when the requested resource does not exist.


## Level 1: H2 fruit stock API

This project manages fruit stock using an H2 in-memory database and a REST API built with Spring Boot.

### Data model

| Entity | Fields |
| :-- | :-- |
| `Fruit` | `id: Long`, `name: String`, `weightInKilos: int` |

### Endpoints

#### POST `/fruits`

Creates a new fruit record.

**Request body**

```json
{
  "name": "Apple",
  "weightInKilos": 12
}
```

**Success response**

- `201 Created` when the fruit is stored successfully.

**Error responses**

- `400 Bad Request` when `name` is blank or `weightInKilos` is invalid.


#### GET `/fruits`

Returns all registered fruits.

**Success response**

- `200 OK` with a JSON array of fruits.
- If there are no fruits, the response should still be `200 OK` with an empty array.

**Example response**

```json
[
  {
    "id": 1,
    "name": "Apple",
    "weightInKilos": 12
  },
  {
    "id": 2,
    "name": "Banana",
    "weightInKilos": 8
  }
]
```


#### GET `/fruits/{id}`

Returns one fruit by its identifier.

**Path parameter**

- `id`: fruit identifier.

**Success response**

- `200 OK` with the fruit details when the ID exists.

**Error responses**

- `404 Not Found` when no fruit matches the provided ID.


#### PUT `/fruits/{id}`

Updates an existing fruit.

**Path parameter**

- `id`: fruit identifier.

**Request body**

```json
{
  "name": "Green Apple",
  "weightInKilos": 14
}
```

**Success response**

- `200 OK` with the updated fruit.

**Error responses**

- `400 Bad Request` when the request body is invalid.
- `404 Not Found` when the fruit ID does not exist.


#### DELETE `/fruits/{id}`

Deletes a fruit by its identifier.

**Path parameter**

- `id`: fruit identifier.

**Success response**

- `204 No Content` when the fruit is deleted successfully.

**Error responses**

- `404 Not Found` when the fruit ID does not exist.


## Level 2: MySQL fruit and provider API

This project extends the previous API by adding providers and persisting the data in MySQL, while keeping the Level 1 fruit endpoints available.

### Data model

| Entity | Fields |
| :-- | :-- |
| `Provider` | `id: Long`, `name: String`, `country: String` |
| `Fruit` | `id: Long`, `name: String`, `weightInKilos: int`, `provider: Provider` |

The relationship between `Fruit` and `Provider` is a typical parent reference and can be modeled with JPA using `@ManyToOne`.

### Provider endpoints

#### POST `/providers`

Creates a new provider.

**Request body**

```json
{
  "name": "Fresh Iberia",
  "country": "Spain"
}
```

**Success response**

- `201 Created` when the provider is created successfully.

**Error responses**

- `400 Bad Request` when the provider name is blank or duplicated according to the business rules described in the assignment.


#### GET `/providers`

Returns all providers.[^2][^3]

**Success response**

- `200 OK` with a JSON array of providers.


#### PUT `/providers/{id}`

Updates a provider.

**Path parameter**

- `id`: provider identifier.

**Request body**

```json
{
  "name": "Fresh Iberia Group",
  "country": "Spain"
}
```

**Success response**

- `200 OK` with the updated provider.

**Error responses**

- `400 Bad Request` when the provider name is blank or duplicated.
- `404 Not Found` when the provider ID does not exist.


#### DELETE `/providers/{id}`

Deletes a provider.[^2][^3]

**Path parameter**

- `id`: provider identifier.

**Success response**

- `204 No Content` when the provider is deleted successfully and has no associated fruits.

**Error responses**

- `400 Bad Request` when the provider still has associated fruits and therefore cannot be removed under the assignment rules.
- `404 Not Found` when the provider ID does not exist.


### Fruit endpoints in Level 2

The fruit endpoints from Level 1 remain active, but fruit creation and filtering now depend on a valid provider relationship.

#### POST `/fruits`

Creates a fruit associated with an existing provider.

**Request body**

```json
{
  "name": "Orange",
  "weightInKilos": 20,
  "providerId": 1
}
```

**Success response**

- `201 Created` when the fruit and provider relationship are valid.

**Error responses**

- `400 Bad Request` when the request body is invalid or no provider is supplied.
- `404 Not Found` when `providerId` does not match an existing provider.


#### GET `/fruits?providerId={id}`

Returns fruits filtered by provider ID using a query parameter, which is a standard pattern for filtering REST collections.

**Query parameter**

- `providerId`: provider identifier.

**Success response**

- `200 OK` with the list of fruits supplied by the selected provider.

**Error responses**

- `404 Not Found` when the provider does not exist.


## Level 3: MongoDB order API

This project manages fruit orders using MongoDB document persistence, where each order can include embedded items.[^7][^8]

### Data model

| Entity | Fields |
| :-- | :-- |
| `Order` | `id: String`, `clientName: String`, `deliveryDate: LocalDate`, `items: List<OrderItem>` |
| `OrderItem` | `fruitName: String`, `quantityInKilos: int` |

Spring Data MongoDB supports CRUD document operations such as save, update, query, and delete for this kind of model.

### Endpoints

#### POST `/orders`

Creates a new order document.

**Request body**

```json
{
  "clientName": "Laura Pérez",
  "deliveryDate": "2026-05-16",
  "items": [
    {
      "fruitName": "Apple",
      "quantityInKilos": 3
    },
    {
      "fruitName": "Banana",
      "quantityInKilos": 2
    }
  ]
}
```

**Success response**

- `201 Created` with the stored order.

**Error responses**

- `400 Bad Request` when `clientName` is missing, the item list is empty, an item has no name, an item quantity is not positive, or the delivery date is earlier than the next day.


#### GET `/orders`

Returns all orders.

**Success response**

- `200 OK` with a JSON array of orders.
- If there are no orders, the API should return `200 OK` with an empty list.


#### GET `/orders/{id}`

Returns an order by its identifier.

**Path parameter**

- `id`: order identifier.

**Success response**

- `200 OK` with the full order document when the ID exists.

**Error responses**

- `404 Not Found` when the order does not exist.


#### PUT `/orders/{id}`

Updates an existing order.

**Path parameter**

- `id`: order identifier.

**Request body**

```json
{
  "clientName": "Laura Pérez",
  "deliveryDate": "2026-05-18",
  "items": [
    {
      "fruitName": "Apple",
      "quantityInKilos": 5
    }
  ]
}
```

**Success response**

- `200 OK` when the order is updated successfully.

**Error responses**

- `400 Bad Request` when the payload is invalid.
- `404 Not Found` when the order ID does not exist.


#### DELETE `/orders/{id}`

Deletes an order by identifier.

**Path parameter**

- `id`: order identifier.

**Success response**

- `204 No Content` when the order is deleted successfully.

**Error responses**

- `404 Not Found` when the order does not exist

## Level 1

### CRUD Exercise with H2

In this first level, you will develop a REST API to manage the stock of a fruit store through a backend application built with Spring Boot.

The goal is to allow management of fruit stock entries, recording for each one the product name and its weight in kilograms.

You will work with an in-memory SQL database (H2), widely used in development and testing environments because of its speed and simple configuration. [web:10][web:13]

### User Stories and Acceptance Criteria

> ⚠️ We recommend tracking each of the following user stories using a Kanban board (such as GitHub Projects, Trello, etc.). In addition, it is good practice to make a clear and descriptive commit once each story is completed.

#### 1. Register a new fruit

As an inventory manager,  
I want to be able to add a new fruit entry indicating its name and weight in kilograms,  
so that I can keep an updated record of incoming products.

**Acceptance criteria:**

- If the data is valid, the system returns HTTP 201 Created with the fruit details.
- If the name is empty or the weight is invalid, it returns HTTP 400 Bad Request.

#### 2. View all fruits

As an inventory manager,  
I want to be able to see a list of all registered fruits,  
so that I can have an overall view of the available stock.

**Acceptance criteria:**

- The system returns HTTP 200 OK and a JSON array with all fruits.
- If there are no registered fruits, it returns an empty array with HTTP 200 OK.

#### 3. View a specific fruit

As an inventory manager,  
I want to be able to check the details of a specific fruit by its identifier,  
so that I can access information about a specific product efficiently.

**Acceptance criteria:**

- If the ID exists, the system returns HTTP 200 OK with the fruit details.
- If the ID does not exist, it returns HTTP 404 Not Found with an explanatory message.

#### 4. Update an existing fruit

As an inventory manager,  
I want to be able to update the name or weight of a registered fruit,  
so that I can correct mistakes or reflect changes in product information.

**Acceptance criteria:**

- If the data is valid, the system returns HTTP 200 OK with the updated fruit.
- If the ID does not exist, it returns HTTP 404 Not Found.
- If the data is invalid, it returns HTTP 400 Bad Request.

#### 5. Delete a fruit

As an inventory manager,  
I want to be able to delete a fruit by its identifier,  
so that I can ensure the stock only contains relevant and updated information.

**Acceptance criteria:**

- If the ID exists, the system deletes the fruit and returns HTTP 204 No Content.
- If the ID does not exist, the system returns HTTP 404 Not Found with an error message.

### Project Setup

Go to [start.spring.io](https://start.spring.io/) and generate a Spring Boot project with the following characteristics:

| Parameter | Value |
|---|---|
| Project | Maven or Gradle |
| Language | Java |
| Spring Boot | 3.x.x (latest stable) |
| Group | `cat.itacademy.s04.t02.n01` |
| Artifact / Name | `fruit-api-h2` |
| Description | `REST API for managing fruit stock with H2` |
| Package name | `cat.itacademy.s04.s02.n01.fruit` |
| Packaging | Jar |
| Java | 21 (LTS) |

**Dependencies:**

- Spring Boot DevTools
- Spring Web
- Spring Data JPA
- H2 Database
- Validation
- Lombok (optional, but recommended to reduce boilerplate code)

### Technical Statement

You will work with an entity called `Fruit`, which will have the following properties:

```text
Fruit
Long id
String name
int weightInKilos
```

Using the JPA specification, you must persist this entity in an H2 database, following the MVC architecture. [web:10][web:13]

Remember that JPA will automatically generate the table and ID values for each fruit using the `@Id` annotation together with `@GeneratedValue`. [web:10]

Organize the project by creating the following packages, according to your main package:

```text
cat.itacademy.s04.t02.n01.controllers
cat.itacademy.s04.t02.n01.model
cat.itacademy.s04.t02.n01.services
cat.itacademy.s04.t02.n01.repository
cat.itacademy.s04.t02.n01.exception
```

The class located inside the `controllers` package (`FruitController`, for example) must be able to handle the following operations through REST endpoints:

### Expected Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/fruits` | Create fruit |
| PUT | `/fruits/{id}` | Update fruit |
| DELETE | `/fruits/{id}` | Delete by id |
| GET | `/fruits/{id}` | Get one fruit by id |
| GET | `/fruits` | Get all fruits |

### Important

You must take API design best practices into account, correctly using error codes and responses in case of invalid calls. You may consult information about `ResponseEntity`. [web:10]

You must avoid exposing JPA entities directly in controllers, using the DTO pattern to manage input and output data, and validating them with Bean Validation annotations such as `@NotBlank`, `@NotNull`, or `@Positive`.

You must implement a `GlobalExceptionHandler` to manage exceptions globally in the application. This will allow errors to be captured and handled centrally, improving robustness and consistency in exception handling.

Using a generative AI, you must create a `Dockerfile` for the project that allows building an image optimized for production environments. The goal is to understand **line by line** how an image is generated through a **multi-stage build** divided into two stages:

- **Build stage:** compile the application and generate the `.jar` file.
- **Final stage:** copy only the `.jar` into a lightweight image to run it in production.

You must develop the project following the **TDD (Test-Driven Development)** approach. That is, before implementing each feature, you must write the corresponding test that defines its expected behavior.

You may use:

- `@SpringBootTest` with `MockMvc`, or libraries such as `RestAssured`, to test REST endpoints.
- `Mockito` to test services in isolation (unit tests).

## Level 2

### CRUD Exercise with MySQL

In this second project, you will extend the functionality of the previous application by adding fruit supplier management (you may build on what you already had in Level 1). Each fruit record must be associated with a supplier, allowing you to register the origin of each product and query which fruits each company supplies.

This new project will use MySQL as the database and will introduce a relationship between entities through JPA, specifically an `@ManyToOne` association between `Fruit` and `Provider`.

### User Stories and Acceptance Criteria

#### 1. Register a supplier

As a purchasing manager,  
I want to be able to add new suppliers by indicating their name and country,  
so that I can keep track of who supplies the fruits.

**Acceptance criteria:**

- The system must allow registering suppliers with a name and country.
- Suppliers with the same name or an empty name cannot be registered.
- If the supplier has been correctly registered, HTTP 201 Created is returned.

#### 2. Add a fruit with a supplier

As a purchasing manager,  
I want to add a new fruit associated with an existing supplier,  
so that I can correctly register the origin of each product.

**Acceptance criteria:**

- When creating a fruit, the ID of a valid supplier must be provided.
- Fruits cannot be added without a supplier.
- If the supplier does not exist, HTTP 404 Not Found is returned.
- If the data is valid, HTTP 201 Created is returned.

#### 3. Filter fruits by supplier

As a stock manager,  
I want to be able to see all fruits supplied by a supplier,  
so that I can track their supply.

**Acceptance criteria:**

- The system must allow querying fruits filtered by supplier ID.
- If the supplier exists, HTTP 200 OK is returned with the fruits.
- If it does not exist, HTTP 404 Not Found is returned.

#### 4. Update a supplier

As a purchasing manager,  
I want to be able to update a supplier’s information,  
so that I can keep the data correct and up to date.

**Acceptance criteria:**

- If the supplier ID exists, the name and country can be updated.
- If the data is valid, HTTP 200 OK is returned with the updated supplier.
- If the ID does not exist, HTTP 404 Not Found is returned.
- Suppliers cannot be updated with an empty name or a name that already exists.

#### 5. Delete a supplier

As a purchasing manager,  
I want to be able to delete a supplier,  
so that I can keep the database clean and up to date.

**Acceptance criteria:**

- If the supplier ID exists and it has no associated fruits, it can be deleted.
- If deletion is successful, HTTP 204 No Content is returned.
- If the ID does not exist, HTTP 404 Not Found is returned.
- If the supplier has associated fruits, it cannot be deleted and HTTP 400 Bad Request is returned with an explanatory message.

### Project Setup

Go to [start.spring.io](https://start.spring.io/) and generate a Spring Boot project with the following characteristics: [web:10][web:13]

| Parameter | Value |
|---|---|
| Project | Maven or Gradle |
| Language | Java |
| Spring Boot | 3.x.x (latest stable) |
| Group | `cat.itacademy.s04.t02.n02` |
| Artifact / Name | `fruit-api-MySQL` |
| Description | `REST API for managing fruit stock with MySQL` |
| Package name | `cat.itacademy.s04.t02.n02.fruit` |
| Packaging | Jar |
| Java | 21 (LTS) |

**Dependencies:**

- Spring Boot DevTools
- Spring Web
- Spring Data JPA
- MySQL Driver
- Validation
- Lombok (optional)

### Technical Statement

You will work with two related entities:

```text
Provider
Long id
String name
String country

Fruit
Long id
String name
int weightInKilos
Provider provider
```

You must persist these entities in a MySQL database, managing the relationship with JPA (`@ManyToOne`).

### Minimum Expected Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/providers` | Create supplier |
| GET | `/providers` | List suppliers |
| PUT | `/providers/{id}` | Update supplier |
| GET | `/fruits?providerId={id}` | Get fruits from a supplier |
| DELETE | `/providers/{id}` | Delete supplier |

In addition to the new endpoints related to suppliers, all **Level 1 endpoints** must continue working correctly with the new data structure.

### Important

Make sure you also comply with all non-functional requirements established in Level 1.

Use DTOs to manage input and output information, avoiding direct exposure of model entities.

Apply validations to DTO fields using annotations such as `@NotBlank`, `@Positive`, or `@NotNull`, with support from Spring’s validation library.

Using a generative AI, create a Dockerfile to package the application into a Docker image optimized for production, allowing configuration of the MySQL database connection through environment variables.

To facilitate the development environment, add a Docker Compose file to bring up the necessary infrastructure, such as the MySQL database service.

**Optional:** You may complement the assignment with endpoint integration tests using `@SpringBootTest` and `MockMvc`, and/or service unit tests with Mockito.

## Level 3

### CRUD Exercise with MongoDB

In this third project, you will develop a REST API to manage fruit orders placed by clients, using MongoDB as the persistence system.

Each order will include the client’s name, the delivery date, and a list of products with their name and quantity in kilograms.

This project will help you practice document persistence in MongoDB using embedded documents.

### User Stories and Acceptance Criteria

#### 1. Create a new order

As a client,  
I want to place an order indicating the fruits and quantities I need,  
so that I can receive the order on the specified date.

**Acceptance criteria:**

- The client must provide their name, the date, and at least one fruit.
- Each fruit must have a name and a positive quantity.
- If the date is earlier than tomorrow, HTTP 400 Bad Request is returned with an error message.
- HTTP 201 Created is returned with the saved order.

#### 2. View all orders

As an order manager,  
I want to see all registered orders,  
so that I can review recent activity.

**Acceptance criteria:**

- Returns HTTP 200 OK with all orders.
- If there are none, it returns an empty list.

#### 3. View an order by ID

As a manager,  
I want to check the details of a specific order,  
so that I can review its contents.

**Acceptance criteria:**

- If the ID exists, it returns HTTP 200 OK with the order.
- Otherwise, it returns HTTP 404 Not Found.

#### 4. Update an order

As a client,  
I want to modify an order I already placed if I made a mistake,  
so that I can make sure I receive what I asked for.

**Acceptance criteria:**

- It can only be modified if a valid ID is provided.
- If the data is valid, HTTP 200 OK is returned.
- If the ID does not exist, HTTP 404 is returned.

#### 5. Delete an order

As a manager,  
I want to delete an order if it has been canceled,  
so that I can keep the system up to date.

**Acceptance criteria:**

- If the ID exists, the order is deleted and HTTP 204 No Content is returned.
- If it does not exist, HTTP 404 Not Found is returned.

### Project Setup

Go to [start.spring.io](https://start.spring.io/) and generate a Spring Boot project with the following characteristics: [web:10][web:13]

| Parameter | Value |
|---|---|
| Project | Maven or Gradle |
| Language | Java |
| Spring Boot | 3.x.x (latest stable) |
| Group | `cat.itacademy.s04.t02.n03` |
| Artifact / Name | `fruit-order-api` |
| Description | `API to manage fruit orders` |
| Package name | `cat.itacademy.s04.t02.n03.fruit` |
| Packaging | Jar |
| Java | 21 (LTS) |

**Dependencies:**

- Spring Boot DevTools
- Spring Web
- Spring Data MongoDB
- Validation

### Technical Statement

You will work with a main entity called `Order`, which will represent a fruit order placed by a client. Each order will consist of:

- The client’s name.
- A delivery date (which must be at least tomorrow).
- A list of items, each with the fruit name and quantity in kilograms.

You will use MongoDB to store each order as a single document inside the `orders` collection.

```text
Order (main document)
String id;
String clientName;
LocalDate deliveryDate;
List<OrderItem> items;

OrderItem (embedded subdocument)
String fruitName;
int quantityInKilos;
```

### Minimum Expected Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/orders` | Create a new order |
| GET | `/orders` | List all orders |
| GET | `/orders/{id}` | Get an order by identifier |
| PUT | `/orders/{id}` | Update an existing order |
| DELETE | `/orders/{id}` | Delete an order |
