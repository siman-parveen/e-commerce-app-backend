# E-Commerce Backend API

A Spring Boot REST API for a simple e-commerce backend. It manages users,
addresses, products, carts, orders, payments, refunds, and product reviews.

The application uses an in-memory H2 database by default, exposes OpenAPI
documentation through Swagger UI, and wraps successful API responses in a common
response object.

## Features

- User signup, login, profile lookup, profile updates, and user deletion
- Address book management for each user
- Product catalog browsing and admin product management
- Cart lookup, add-to-cart, quantity updates, and cart item removal
- Order creation, cancellation, user/admin order lookup, and admin status updates
- Payment creation, payment lookup, admin payment listing, and refund operations
- Product reviews by users, including review lookup, update, and deletion

## Tech Stack

- Java 17
- Spring Boot 3.2.5
- Spring Web
- Spring Data JPA
- Spring Boot Actuator
- H2 Database
- Springdoc OpenAPI / Swagger UI
- Maven Wrapper

## Project Structure

```text
src/main/java/com/api/ecommerce
|-- common            # Shared constants and common response helpers
|-- dto               # Request and response DTOs
|-- entity            # JPA entities and enums
|-- exception         # Global exception handling
|-- repo              # Spring Data repositories
|-- response          # Common API response wrapper
|-- restcontroller    # REST API controllers
`-- service           # Service interfaces and implementations
```

## Getting Started

### Prerequisites

- JDK 17 or newer
- No separate Maven installation is required because the Maven Wrapper is
  included in the project

### Run the Application

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS or Linux:

```bash
./mvnw spring-boot:run
```

The API starts at:

```text
http://localhost:8080
```

### Run Tests

On Windows:

```powershell
.\mvnw.cmd test
```

On macOS or Linux:

```bash
./mvnw test
```

## Useful Local URLs

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- H2 Console: `http://localhost:8080/h2-console`

Default H2 connection details:

```text
JDBC URL: jdbc:h2:mem:ecommercedb
Username: sa
Password: <empty>
```

## Configuration

The main configuration lives in:

```text
src/main/resources/application.properties
```

By default the app uses H2:

```properties
spring.datasource.url=jdbc:h2:mem:ecommercedb
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

A commented MySQL configuration is also present in `application.properties` for
local customization. Keep real database credentials out of source control before
using this project beyond local development.

The Spring Security dependency is currently commented out in `pom.xml`, so this
README documents the API as a local development backend without an authentication
flow.

## Response Format

Most successful responses use the shared `Response<T>` wrapper:

```json
{
  "success": true,
  "message": "Operation completed successfully",
  "responseData": {}
}
```

Validation and application errors are returned by the global exception handler:

```json
{
  "timestamp": "2026-08-27T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "details": {
    "fieldName": "Validation message"
  }
}
```

## API Endpoints

### Products

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `GET` | `/api/public/product` | List all products |
| `GET` | `/api/public/product/{productExternalId}` | Get a product by external ID |
| `POST` | `/api/admin/product` | Add a product |
| `PATCH` | `/api/admin/product/{productExternalId}` | Update a product |
| `DELETE` | `/api/admin/product/{productExternalId}` | Remove a product |
| `GET` | `/api/user/{userExternalId}/products` | List products bought by a user |

### Users

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `GET` | `/api/user/all` | List all users |
| `GET` | `/api/user/{userExternalId}` | Get a user by external ID |
| `POST` | `/api/user/signup` | Register a new user |
| `POST` | `/api/user/login` | Log in with email and password |
| `PATCH` | `/api/user/{userExternalId}` | Update user information |
| `DELETE` | `/api/user/{userId}` | Remove a user |

### Addresses

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `GET` | `/api/user/{userExternalId}/address` | List addresses for a user |
| `POST` | `/api/user/address` | Add an address |
| `PATCH` | `/api/user/address/{addressExternalId}` | Update an address |
| `DELETE` | `/api/user/address/{addressExternalId}` | Delete an address |

### Cart

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `GET` | `/api/user/{userExternalId}/cart` | Get a user's cart |
| `POST` | `/api/user/{userExternalId}/cart` | Add a product to a user's cart |
| `PATCH` | `/api/user/cart/items/{cartItemExternalId}/add` | Increase item quantity |
| `PATCH` | `/api/user/cart/items/{cartItemExternalId}/remove` | Decrease item quantity |
| `DELETE` | `/api/user/cart/items/{cartItemExternalId}` | Remove an item from the cart |

### Orders

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `POST` | `/api/user/{userExternalId}/order` | Create an order for a user |
| `GET` | `/api/user/{userExternalId}/order` | List orders for a user |
| `GET` | `/api/user/order/{orderExternalId}` | Get an order as a user |
| `PATCH` | `/api/user/order/{orderExternalId}` | Cancel an order |
| `GET` | `/api/admin/order/{orderExternalId}` | Get an order as an admin |
| `GET` | `/api/admin/orders` | List all orders |
| `PATCH` | `/api/admin/order/{orderExternalId}/update/status?status=SHIPPED` | Update order status |
| `GET` | `/api/user/order/{orderExternalId}/items` | List items in an order |

Supported order statuses:

```text
NEW, PROCESSING, SHIPPED, DELIVERED, CANCELLED, RETURNED
```

### Payments

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `POST` | `/api/user/order/{orderExternalId}/payment` | Create payment for an order |
| `GET` | `/api/user/order/{orderExternalId}/payment` | Get payment for an order |
| `GET` | `/api/admin/payments` | List all payments |
| `PATCH` | `/api/user/order/{orderExternalId}/refund/payment` | Refund payment by order external ID |
| `PATCH` | `/api/user/order/payment/{transactionId}` | Refund payment by transaction ID |

Supported payment modes:

```text
CARD, UPI, NET_BANKING, CASH_ON_DELIVERY, WALLET
```

Supported payment statuses:

```text
START, PENDING, SUCCESS, FAILED, REFUNDED
```

### Reviews

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `GET` | `/api/admin/reviews` | List all reviews |
| `GET` | `/api/user/{userExternalId}/reviews` | List reviews written by a user |
| `GET` | `/api/product/{productExternalId}/reviews` | List reviews for a product |
| `POST` | `/api/user/product/review` | Add a review to a product |
| `PATCH` | `/api/user/product/review/{reviewExternalId}` | Update a review |
| `DELETE` | `/api/user/product/review/{reviewExternalId}` | Delete a review |
| `GET` | `/api/public/product/review/{reviewExternalId}` | Get a review by external ID |

## Example Request Bodies

### Create a User

```json
{
  "userName": "Alex Morgan",
  "emailId": "alex@example.com",
  "phoneNumber": "+14155552671",
  "password": "secret"
}
```

### Add a Product

```json
{
  "productName": "Wireless Mouse",
  "description": "Ergonomic wireless mouse with USB receiver",
  "stock": 25,
  "price": 999.00,
  "discountPercentage": 10
}
```

### Add an Address

```json
{
  "addressName": "Home",
  "addressLine": "221B Baker Street",
  "city": "London",
  "state": "London",
  "pinCode": "123456",
  "country": "United Kingdom",
  "userExternalId": "user-external-id"
}
```

### Add an Item to Cart

```json
{
  "productExternalId": "product-external-id",
  "quantity": 2
}
```

### Update Cart Quantity

```json
{
  "quantity": 1
}
```

### Create an Order

```json
{
  "totalAmount": 1998.00,
  "deductions": 0,
  "phoneNumber": "+14155552671",
  "deliveryAddressExternalId": "address-external-id",
  "userExternalId": "user-external-id",
  "orderReceiverName": "Alex Morgan",
  "orderItems": [
    {
      "productExternalId": "product-external-id",
      "quantity": 2
    }
  ]
}
```

### Make a Payment

```json
{
  "transactionId": "TXN-1001",
  "totalAmount": 1998.00,
  "paymentMode": "UPI",
  "orderExternalId": "order-external-id"
}
```

### Add a Review

```json
{
  "userExternalId": "user-external-id",
  "productExternalId": "product-external-id",
  "rating": 5,
  "comment": "Great value for the price"
}
```

## Notes for Development

- The H2 database is in memory, so data is reset when the application restarts.
- External IDs are used in the public API instead of internal database IDs.
- Swagger UI is the easiest way to explore request and response schemas while
  the application is running.
- Add authentication and authorization before exposing admin routes outside a
  local development environment.
