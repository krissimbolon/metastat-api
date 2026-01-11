# Metastat API

Sistem Manajemen Metadata Statistik - Spring Boot REST API

## Tech Stack

- Spring Boot 3.4.1
- Java 21
- MySQL 8.0
- JWT Authentication
- MapStruct
- OpenAPI/Swagger

## Features

- JWT authentication
- Role-based access (ADMIN/USER)
- Statistical activity management
- Domain and subject classification
- Concept and variable metadata
- Publication tracking
- Pagination support

## Quick Start

1. Clone repository:
   git clone https://github.com/krissimbolon/metastat-api.git
   cd metastat-api

2. Create database:
   CREATE DATABASE metastat_db;
   CREATE USER 'metastat_user'@'localhost' IDENTIFIED BY 'metastat_pass';
   GRANT ALL PRIVILEGES ON metastat_db.* TO 'metastat_user'@'localhost';

3. Run application:
   ./gradlew bootRun

4. Access:
   - API: http://localhost:8082
   - Swagger: http://localhost:8082/swagger-ui.html
   - Docs: http://localhost:8082/v3/api-docs

## Default Users

- Admin: admin@bps.go.id / admin123
- User: user@bps.go.id / user123

## API Endpoints

Authentication:
  POST /auth/login
  POST /auth/register
  GET  /auth/me

Activities:
  GET    /api/activities
  GET    /api/activities/{id}
  POST   /api/activities
  PUT    /api/activities/{id}
  DELETE /api/activities/{id}
  PATCH  /api/activities/{id}/status

Domains & Subjects:
  GET /api/domains
  GET /api/subjects

Variables & Publications:
  GET  /api/variables
  POST /api/variables
  GET  /api/publications
  POST /api/publications

## Testing

Login:
  curl -X POST http://localhost:8082/auth/login -H "Content-Type: application/json" -d '{"email":"admin@bps.go.id","password":"admin123"}'

Get Activities:
  curl http://localhost:8082/api/activities -H "Authorization: Bearer YOUR_TOKEN"

## Project Structure

src/main/java/com/bps/metastat/
  - config/         Configuration classes
  - controller/     REST endpoints
  - domain/
    - entity/       JPA entities
    - repository/   Data access
  - dto/            Request/Response objects
  - service/        Business logic
  - security/       JWT authentication
  - exception/      Error handling

## Database

Tables: users, statistical_activities, domains, subject_categories, organizations, publications, variables, concepts, indicators

## Security

- JWT token authentication
- BCrypt password encryption
- Role-based authorization
- CORS enabled

## Mobile Integration

Designed for Android consumption with consistent JSON responses and proper error handling.

## Author

Teguh Christianto Simbolon - 222313403
UAS Pemrograman Platform Khusus (PPK)
Politeknik Statistika STIS - 2026
