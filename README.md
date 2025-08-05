# Appointment Management System

## 1. Table of Contents
1. [Project Title](#project-title)
2. [Introduction](#introduction)
3. [Technologies Used](#technologies-used)
4. [Launch](#launch)
5. [Dockerized Setup](#dockerized-setup)
6. [Illustrations](#illustrations)
7. [Scope of Functions](#scope-of-functions)
8. [Use Examples](#use-examples)
9. [Project Status](#project-status)
10. [Sources](#sources)

---

## Project Title
**Appointment Management System**

---

## Introduction
The aim of this project is to develop a backend system for managing doctor appointment scheduling.  
It allows patients to book appointments, doctors to view and manage their schedules, and ensures no time conflicts occur between appointments.  
The project is designed as a learning exercise for internship purposes, focusing on clean architecture and Spring Boot development.

---

## Technologies Used
- **Java 8 (1.8_251)**  
- **Spring Boot 2.7.18**  
- **PostgreSQL 17**  
- **Maven**  
- **Swagger (OpenAPI)** for API documentation

---

## Launch

To run the project locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/deimooos/Appointment-App.git
   ```
2. Navigate to the project directory:
   ```bash
   cd appointmentapp
   ```
3. Build and run the application with Maven:
   ```bash
   mvn spring-boot:run
   ```
4. Open your browser and go to:
   ```bash
   http://localhost:8080
   ```
The API documentation (Swagger UI) is available at:
   ```bash
   http://localhost:8080/swagger-ui.html
   ```
---

## Dockerized Setup

- Alternatively, you can run the project using Docker:

1. Make sure Docker Desktop is installed and running on your system.

2. Navigate to the project root folder where `docker-compose.yml` is located.

3. Build and start the containers:
   ```bash
   docker-compose up --build

4. Access the application:
   - API Base URL: http://localhost:8080

   - Swagger UI: http://localhost:8080/swagger-ui.html

- This setup automatically starts both PostgreSQL and the Spring Boot application inside Docker containers.


## Illustrations

- API endpoints are documented with Swagger UI, accessible at:  
`http://localhost:8080/swagger-ui.html`
- Example Postman collection is available in the repository.

---

## Scope of Functions

- User management (registration, retrieval)
- Doctor management (creation, retrieval)
- Specialty management (creation, retrieval)
- Appointment booking, updating, cancelling
- Filtering appointments by user, doctor, specialty, date ranges, status, and results
- Marking appointments as completed, successful, or unsuccessful
- Checking doctor availability
- Retrieving daily available appointment slots

---

## Use Examples

- Create a new appointment: POST `/api/appointments/create`  
- Get all appointments: GET `/api/appointments/all`  
- Mark appointment as completed: PUT `/api/appointments/complete` with body `{ "id": 1 }`  
- Filter appointments by user and status: POST `/api/appointments/by-user-status` with filter JSON  
- Check doctor availability: POST `/api/appointments/check-availability` with doctorId and dateTime  

(Full examples and payloads can be found in the Postman collection.)

---

## Project Status

- The project is under active development.
- Core CRUD operations and filtering are implemented.
- Exception handling and validations are in place.
- Future improvements include adding authentication, improving UI, and expanding reporting features.

---

## Sources

- Spring Boot official documentation: https://spring.io/projects/spring-boot
- Another Spring Boot documentation: https://www.ibm.com/think/topics/java-spring-boot
- PostgreSQL official website: https://www.postgresql.org/  
- Java 8 documentation: https://docs.oracle.com/javase/8/docs/
- Maven: https://maven.apache.org/
- Spring Data JPA: https://spring.io/projects/spring-data-jpa
- Hibernate Validator: https://hibernate.org/validator/
- Stack Overflow: https://stackoverflow.com/
- Swagger OpenAPI documentation: https://swagger.io/docs/
- Mentorship and tutorials during the internship period

---
