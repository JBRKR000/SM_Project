# SM_Project

## Overview

**SM_Project** is a comprehensive Property Management System designed for administrators and residents of residential buildings and communities. The project enables efficient management of buildings, apartments, tenants, and billing, while supporting communication between administration and residents.

## Key Features

- **Tenant and Apartment Management:** Add, edit, and remove apartments, assign tenants to specific units.
- **Invoice Management:** Tenants have access to their own invoice list and payment history.
- **Issue Reporting:** Residents can report issues and track their resolution status.
- **Water Consumption Reporting:** Monthly water usage can be entered by tenants.
- **Admin Panel:** Advanced features for administrators, including management of buildings, entrances, apartments, and tenants.
- **Security:** Authentication and authorization mechanisms (JWT, user roles) are implemented.
- **Friendly API:** Available for frontend integration and external systems.

## Technologies

- **Java, Spring Boot** — backend and REST API layer.
- **Spring Security** — user authentication and authorization.
- **JPA/Hibernate** — database management.
- **Swagger** — API documentation (publicly available for development convenience).

## Example Endpoints

- `/api/user/invoices` — fetch user invoices
- `/api/user/accidents` — report and view issues
- `/api/user/send-consumption` — log water consumption
- `/api/admin/blocks` — manage buildings
- `/api/admin/flats` — manage apartments

## Getting Started

1. Clone the repository:
   ```
   git clone https://github.com/JBRKR000/SM_Project.git
   ```
2. Configure the database and set environment variables.
3. Run the Spring Boot application.
4. (Optional) Access Swagger documentation at `/swagger-ui/`.

## Use Cases

This project is ideal as a support system for property management, housing associations, or residential communities.

---

> **Author:** JBRKR000  
> This project was created for educational and development purposes
