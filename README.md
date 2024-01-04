# SPREE BACKEND

# Your Project Name

## Description

This project is a Spring Boot and Java-based API that provides user authentication and note management functionality.

## Table of Contents

- [Frameworks and Tools Used](#frameworks-and-tools-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [API Endpoints](#api-endpoints)
  - [Authentication Endpoints](#authentication-endpoints)
  - [Note Endpoints](#note-endpoints)
- [Testing](#testing)
  - [Unit Tests](#unit-tests)
- [Additional Notes](#additional-notes)

## Frameworks and Tools Used

- **Spring Boot:** A powerful and convention-over-configuration-based framework for building Java-based applications.
- **Spring Security:** Provides authentication and authorization support.
- **Spring Data JPA:** Simplifies data access using the Java Persistence API.
- **H2 Database:** An in-memory database for development and testing.
- **Maven:** A build and dependency management tool.

## Getting Started

### Prerequisites

Make sure you have the following installed:

- Java Development Kit (JDK)
- Maven
- Git

### Installation

1. Clone the repository:

  ```bash
  git clone https://github.com/your-username/your-repository.git
  ```
2. Navigate to the project directory:

  ```bash
  cd spree
  ```
3. Build the project:

  ```bash
  mvn clean install
  ```
4. Run the application:
  ```bash
  java -jar target/spree.jar
  ```

5. The application will be accessible at [http://localhost:8080](http://localhost:8080).

## API Endpoints

### Authentication Endpoints

- `POST /api/auth/signup`: Create a new user account.
- `POST /api/auth/login`: Log in to an existing user account and receive an access token.

### Note Endpoints

- `GET /api/notes`: Get a list of all notes for the authenticated user.
- `GET /api/notes/{id}`: Get a note by ID for the authenticated user.
- `POST /api/notes`: Create a new note for the authenticated user.
- `PUT /api/notes/{id}`: Update an existing note by ID for the authenticated user.
- `DELETE /api/notes/{id}`: Delete a note by ID for the authenticated user.
- `POST /api/notes/{id}/share`: Share a note with another user for the authenticated user.
- `GET /api/search?q={query}`: Search for notes based on keywords for the authenticated user.



