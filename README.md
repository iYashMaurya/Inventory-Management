# Inventory Management System

A RESTful API-based inventory management system built with Spring Boot that allows you to manage products, track stock levels, and monitor low-stock items.

## Features

- **Product Management**: Create, read, update, and delete products
- **Stock Management**: Increase and decrease product quantities
- **Low Stock Alerts**: Automatically track products below threshold levels
- **Validation**: Built-in input validation for all operations
- **API Documentation**: Swagger/OpenAPI integration for easy API exploration
- **Dockerized**: Fully containerized application with MySQL database

## Tech Stack

- **Backend**: Spring Boot 3.5.6
- **Database**: MySQL 8.0
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose
- **API Documentation**: SpringDoc OpenAPI (Swagger)

## Prerequisites

The only thing you need is **Docker** and **Docker Compose**. No Java installation required!

### Installing Docker

#### Windows
1. Download [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/)
2. Run the installer and follow the installation wizard
3. Restart your computer if prompted
4. Launch Docker Desktop and wait for it to start

#### macOS
1. Download [Docker Desktop for Mac](https://www.docker.com/products/docker-desktop/)
2. Open the `.dmg` file and drag Docker to Applications
3. Launch Docker from Applications folder
4. Grant necessary permissions when prompted

#### Linux (Ubuntu/Debian)
```bash
# Update package index
sudo apt-get update

# Install dependencies
sudo apt-get install ca-certificates curl gnupg lsb-release

# Add Docker's official GPG key
sudo mkdir -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

# Set up repository
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Install Docker Engine
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin

# Verify installation
sudo docker --version
```

## Why Docker?

> **"It was working on my PC!"**

We've all heard this before. Docker eliminates the classic development problem of environment inconsistencies. With Docker:
-  Same environment for everyone
-  No Java installation needed
-  No MySQL setup required
-  Works on Windows, Mac, and Linux
-  Easy to deploy and scale

## Getting Started

### 1. Clone the Repository
```bash
git clone <your-repository-url>
cd inventory-management
```

### 2. Build and Run with Docker
```bash
# Build and start all services
docker-compose up --build

# Or run in detached mode (background)
docker-compose up --build -d
```

This single command will:
- Build your Spring Boot application
- Start MySQL database container
- Start the application container
- Set up networking between containers
- Initialize the database

### 3. Verify the Application
Once the containers are running, the application will be available at:
- **API Base URL**: `http://localhost:8080`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`

### 4. Stop the Application
```bash
# Stop and remove containers
docker-compose down

# Stop, remove containers, and delete volumes (removes all data)
docker-compose down -v
```

## API Endpoints

### Inventory Management (`/inventory`)

#### Create Product
- **POST** `/inventory`
- **Body**:
```json
{
  "productName": "Laptop",
  "description": "High-performance laptop",
  "stockQuantity": 50,
  "thresholdQuantity": 10
}
```

#### Get Product by ID
- **GET** `/inventory/{id}`

#### Update Product
- **PUT** `/inventory/{id}`
- **Body**:
```json
{
  "productName": "Laptop",
  "description": "High-performance laptop",
  "stockQuantity": 50,
  "thresholdQuantity": 10
}
```

#### Delete Product
- **DELETE** `/inventory/{id}`

### Product Operations (`/product`)

#### Increase Stock Quantity
- **PUT** `/product/increase-quantity/{id}`
- **Body**:
```json
{
  "quantity": 20
}
```

#### Decrease Stock Quantity
- **PUT** `/product/decrease-quantity/{id}`
- **Body**:
```json
{
  "quantity": 5
}
```

#### Get Low Stock Products
- **GET** `/product/low-threshold`
- Returns all products where `stockQuantity <= thresholdQuantity`

## Testing the API

### Option 1: Using cURL

#### Create a Product
```bash
curl -X POST http://localhost:8080/inventory \
  -H "Content-Type: application/json" \
  -d '{
    "productName": "Wireless Mouse",
    "description": "Ergonomic wireless mouse",
    "stockQuantity": 100,
    "thresholdQuantity": 20
  }'
```

#### Get Product by ID
```bash
curl -X GET http://localhost:8080/inventory/1
```

#### Update Product
```bash
curl -X PUT http://localhost:8080/inventory/1 \
  -H "Content-Type: application/json" \
  -d '{
    "productName": "Wireless Mouse Pro",
    "description": "Premium ergonomic wireless mouse",
    "stockQuantity": 150,
    "thresholdQuantity": 25
  }'
```

#### Increase Stock Quantity
```bash
curl -X PUT http://localhost:8080/product/increase-quantity/1 \
  -H "Content-Type: application/json" \
  -d '{
    "quantity": 50
  }'
```

#### Decrease Stock Quantity
```bash
curl -X PUT http://localhost:8080/product/decrease-quantity/1 \
  -H "Content-Type: application/json" \
  -d '{
    "quantity": 10
  }'
```

#### Get Low Stock Products
```bash
curl -X GET http://localhost:8080/product/low-threshold
```

#### Delete Product
```bash
curl -X DELETE http://localhost:8080/inventory/1
```

### Option 2: Using Postman

1. **Install Postman**
    - Download from [postman.com](https://www.postman.com/downloads/)
    - Or use the web version

2. **Import Collection** (Optional)
    - Create a new collection named "Inventory Management"
    - Add requests as described below

3. **Create Requests**

   **Create Product**
    - Method: POST
    - URL: `http://localhost:8080/inventory`
    - Headers: `Content-Type: application/json`
    - Body (raw JSON):
   ```json
   {
     "productName": "Wireless Mouse",
     "description": "Ergonomic wireless mouse",
     "stockQuantity": 100,
     "thresholdQuantity": 20
   }
   ```

   **Get Product**
    - Method: GET
    - URL: `http://localhost:8080/inventory/1`

   **Update Product**
    - Method: PUT
    - URL: `http://localhost:8080/inventory/1`
    - Headers: `Content-Type: application/json`
    - Body:
    ```json
   {
   "productName": "Wireless Mouse",
   "description": "Ergonomic wireless mouse",
   "stockQuantity": 100,
   "thresholdQuantity": 20
   }
   ```

   **Increase Quantity**
    - Method: PUT
    - URL: `http://localhost:8080/product/increase-quantity/1`
    - Headers: `Content-Type: application/json`
    - Body:
   ```json
   {
     "quantity": 50
   }
   ```

   **Decrease Quantity**
    - Method: PUT
    - URL: `http://localhost:8080/product/decrease-quantity/1`
    - Headers: `Content-Type: application/json`
    - Body:
   ```json
   {
     "quantity": 10
   }
   ```

   **Get Low Stock Products**
    - Method: GET
    - URL: `http://localhost:8080/product/low-threshold`

   **Delete Product**
    - Method: DELETE
    - URL: `http://localhost:8080/inventory/1`


## Project Structure

```
inventory-management/
├── src/
│   └── main/
│       └── java/com/maurya/inventorymanagement/
│           └── productmanagement/
│               ├── controller/
│               │   ├── InventoryController.java
│               │   └── ProductController.java
│               ├── dto/
│               │   ├── ProductRequestDTO.java
│               │   ├── ProductResponseDTO.java
│               │   └── QuantityUpdateDTO.java
│               ├── entity/
│               │   └── ProductEntity.java
│               ├── exception/
│               │   ├── GlobalExceptionHandler.java
│               │   ├── InvalidThresholdException.java
│               │   └── NoLowStockProductException.java
│               ├── repository/
│               │   └── ProductRepository.java
│               └── services/
│                   ├── InventoryServices.java
│                   └── ProductServices.java
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Validation Rules

- **Product Name**: Cannot be blank
- **Stock Quantity**: Must be at least 1
- **Threshold Quantity**: Must be at least 0
- **Decrease Operation**: Cannot decrease below 0

## Error Handling

The API includes comprehensive error handling:
- **404 Not Found**: Product doesn't exist
- **400 Bad Request**: Validation errors or insufficient stock
- **500 Internal Server Error**: Unexpected errors

All errors return a structured JSON response with status, error type, and message.

## Database Configuration

The application uses MySQL with the following default configuration:
- **Database**: `inventory_db`
- **Username**: `inventory_user`
- **Password**: `inventory_pass`
- **Port**: 3306 (internal), not exposed to host

Data is persisted in a Docker volume named `mysql-data`.

## Troubleshooting

### Port Already in Use
If port 8080 is already in use:
```bash
# Change the port in docker-compose.yml
ports:
  - "8081:8080"  # Use 8081 instead
```

### Containers Not Starting
```bash
# Check logs
docker-compose logs

# Check specific service logs
docker-compose logs app
docker-compose logs mysql
```

### Database Connection Issues
```bash
# Restart services
docker-compose restart

# Or rebuild completely
docker-compose down -v
docker-compose up --build
```

### Clear Everything and Start Fresh
```bash
# Stop all containers
docker-compose down -v

# Remove all images
docker rmi $(docker images -q inventory-*)

# Rebuild and start
docker-compose up --build
```


**Happy Coding! **
