# Streaming Service Backend App

This is a backend application based on a microservices architecture designed to provide subscription-based video streaming services. The subscription management system is integrated with the Stripe payment gateway, allowing for payment processing and subscription status management. Every day at midnight, the subscription service checks for subscriptions that are about to expire and automatically renews or cancels them based on user settings and payment status.

## Technology Stack
The project employs the following technologies:
- Java JDK 22
- JWT
- Spring Boot: A framework for building microservices.
- Spring Security: Manages authentication and authorization.
- Feign: A declarative HTTP client for microservices.
- Spring Data JPA: Facilitates interaction with relational databases.
- PostgreSQL: A relational database for storing core information.
- MongoDB: A NoSQL database for managing schema-less data.
- Eureka: A service registry for managing microservices.
- Spring Cloud Config: Enables centralized configuration management.
- Flyway Migration: Handles database versioning.
- Zookeeper: Coordinates distributed systems.
- Gateway: An API gateway for routing requests.
- Quartz: A task scheduler for managing periodic operations.
- Stripe: Integrates with a payment system for handling subscriptions and payments.

## Diagrams
All use case and architecture diagrams related to the project can be found in the diagrams folder. This folder contains visualizations that aid in understanding the structure and interactions of the system components.
## License

This project is distributed under the MIT License. For more details, please refer to the LICENSE file located in the root directory of the project.
