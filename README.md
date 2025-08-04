# Appointment Management System

## 1. Table of Contents
1. [Project Title](#project-title)
2. [Introduction](#introduction)
3. [Technologies Used](#technologies-used)
4. [Launch](#launch)
5. [Illustrations](#illustrations)
6. [Scope of Functions](#scope-of-functions)
7. [Use Examples](#use-examples)
8. [Project Status](#project-status)
9. [Sources](#sources)

---

## 1. Project Title
**Appointment Management System**

---

## 2. Introduction
The aim of this project is to develop a backend system for managing doctor appointment scheduling.  
It allows patients to book appointments, doctors to view and manage their schedules, and ensures no time conflicts occur between appointments.  
The project is designed as a learning exercise for internship purposes, focusing on clean architecture and Spring Boot development.

---

## 3. Technologies Used
- **Java 8 (1.8_251)**  
- **Spring Boot 2.7.18**  
- **PostgreSQL 17**  
- **Maven**  
- **Swagger (OpenAPI)** for API documentation

---

## 4. Launch

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

## 5. Illustrations

- API endpoints are documented with Swagger UI, accessible at:  
`http://localhost:8080/swagger-ui.html`
- Example Postman collection is available in the repository.

---

## 6. Scope of Functions

- User management (registration, retrieval)
- Doctor management (creation, retrieval)
- Specialty management (creation, retrieval)
- Appointment booking, updating, cancelling
- Filtering appointments by user, doctor, specialty, date ranges, status, and results
- Marking appointments as completed, successful, or unsuccessful
- Checking doctor availability
- Retrieving daily available appointment slots

---

## 7. Use Examples

- Create a new appointment: POST `/api/appointments/create`  
- Get all appointments: GET `/api/appointments/all`  
- Mark appointment as completed: PUT `/api/appointments/complete` with body `{ "id": 1 }`  
- Filter appointments by user and status: POST `/api/appointments/by-user-status` with filter JSON  
- Check doctor availability: POST `/api/appointments/check-availability` with doctorId and dateTime  

(Full examples and payloads can be found in the Postman collection.)

---

## 8. Project Status

- The project is under active development.
- Core CRUD operations and filtering are implemented.
- Exception handling and validations are in place.
- Future improvements include adding authentication, improving UI, and expanding reporting features.

---

## 9. Sources

- Spring Boot official documentation: https://spring.io/projects/spring-boot  
- PostgreSQL official website: https://www.postgresql.org/  
- Swagger UI for API documentation  
- Java 8 features and best practices  
- Mentorship and tutorials during the internship period

---
