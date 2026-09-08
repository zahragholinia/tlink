# tlink

A URL shortening service built with **Java** and **Spring Boot**.

The project provides a backend for creating, managing, and redirecting shortened URLs, with authentication, link expiration, view tracking, and PostgreSQL persistence.

## What This Project Demonstrates:

* RESTful backend API design
* URL shortening using **Base62 encoding**
* JWT-based authentication and authorization
* PostgreSQL persistence with Spring Data JPA
* Pagination for user links
* Link expiration and cleanup
* View tracking
* Input validation
* Global exception handling
* API documentation with OpenAPI / Swagger
* Automated testing with Maven

## Architecture

The application follows a layered backend architecture:

```text
                         ┌─────────────────────┐
                         │      REST API       │
                         │   Link / Auth API   │
                         └──────────┬──────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │    Service Layer    │
                         │                     │
                         │ Link Service        │
                         │ JWT Service         │
                         │ User Service        │
                         └──────────┬──────────┘
                                    │
                   ┌────────────────┼────────────────┐
                   │                │                │
                   ▼                ▼                ▼
             ┌───────────┐   ┌────────────┐   ┌────────────┐
             │ Validation│   │  Base62    │   │  Security  │
             │ & Errors  │   │  Encoding  │   │  JWT/Auth  │
             └───────────┘   └────────────┘   └────────────┘
                   │                │                │
                   └────────────────┼────────────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │     Repository      │
                         │   Spring Data JPA   │
                         └──────────┬──────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │     PostgreSQL      │
                         └─────────────────────┘
```

## URL Shortening

When a user creates a shortened URL, the application generates a short identifier using **Base62 encoding**.

The identifier is derived from the database-generated link ID.

```text
Original URL
     │
     ▼
Create Link
     │
     ▼
PostgreSQL generates ID
     │
     ▼
Base62 Encoding
     │
     ▼
Short Code
     │
     ▼
https://host/{shortCode}
```

Using the database ID as the source for the short code keeps the encoding logic simple and avoids maintaining a separate short-code generation mechanism.

## Redirect Flow

When a client requests a shortened URL, the application decodes the short code, retrieves the corresponding link, validates its state, and redirects the client.

```text
Client
  │
  │ GET /{shortCode}
  ▼
Decode Base62
  │
  ▼
Find Link
  │
  ├── Not Found ──────► 404
  │
  ├── Expired ────────► Reject
  │
  └── Active
        │
        ▼
   Increment Views
        │
        ▼
 Redirect to Original URL
```

## Link Lifecycle

A shortened link can move through the following lifecycle:

```text
                    ┌──────────────┐
                    │    Created   │
                    └──────┬───────┘
                           │
                           ▼
                    ┌──────────────┐
                    │    Active    │
                    └──────┬───────┘
                           │
                ┌──────────┴──────────┐
                ▼                     ▼
             Expired                Deleted
                │                     │
                ▼                     ▼
          No Redirect             No Redirect
```

## Authentication & Authorization

The application uses **Spring Security** with **JWT-based authentication**.

The authentication flow is:

```text
Client
  │
  │ Login
  ▼
Authentication API
  │
  ▼
Validate Credentials
  │
  ▼
Generate JWT
  │
  ▼
Client
  │
  │ Authorization: Bearer <token>
  ▼
Protected API
```

Users can manage their own shortened links through authenticated endpoints.

## Link Management

Authenticated users can:

* Create shortened URLs
* Retrieve their links
* Retrieve links with pagination
* Redirect through shortened URLs
* Track view counts
* Delete links
* Work with links that have an expiration time

## Expiration Management

Links can have an expiration time.

Expired links are detected during access and are excluded from normal redirect behavior.

The application also contains scheduled cleanup logic for expired links.

## Validation & Error Handling

The application validates incoming requests and URLs before processing them.

It also uses centralized exception handling to provide consistent API error responses.

## API Documentation

The project uses **OpenAPI / Swagger** for API documentation.

Once the application is running, the available REST endpoints can be explored through the generated Swagger UI.

## Technology Stack

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Data JPA

### Database

* PostgreSQL

### Security

* JWT
* BCrypt

### API

* REST
* OpenAPI / Swagger

### Build & Testing

* Maven
* JUnit
* Spring Boot Test

## Running the Application

Clone the repository and run:

```bash
./mvnw clean test
```

Then start the Spring Boot application using Maven or your preferred IDE.

## Example

A long URL such as:

```text
https://example.com/a/very/long/url
```

can be represented by a short URL such as:

```text
https://host/abc123
```

The short code is generated using the Base62 representation of the link identifier.

## Why This Project

The project was built to explore the backend design of a URL shortening service, including:

* Identifier encoding
* REST API design
* Authentication and authorization
* Persistence
* Link lifecycle management
* Expiration handling
* View tracking
* Validation and error handling

The focus is on building a small but complete backend system rather than implementing only a URL shortening algorithm.
