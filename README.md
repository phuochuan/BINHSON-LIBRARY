# BINHSON-LIBRARY Backend Project (Microservices Architecture)
BINHSON-LIBRARY is a simulated microservices-based project developed as a library website for backend development. Leveraging the principles of microservices architecture, the project aims to provide a scalable, modular, and resilient backend solution for managing library resources and services.# Table of Contents
# Technologies Used:
**Databases**: MySQL, PostgreSQL \
**Authentication**: JWT, Keycloak \
**Communication**: Kafka \
**Caching**: Redis Cache \
**Framework**: Spring Framework (Spring Boot, Spring Data, Spring Security, Spring Cloud)\
**API Design**: HATEOAS\
**Payment Integration**: PayPal API\
**Other**: Websocket
# Main Microservices:
**User Service**: 
Responsible for user-related functions such as registration, login, logout, password management, and other user data management functionalities. \
**Document Service**:
Provides endpoints for storing and managing document data, facilitating operations related to library resources.\
**Borrowing Service**:
Manages borrowing activities, including borrowing and returning library items, tracking due dates, and handling associated notifications.\
**Payment and Notification Service**:
Responsible for handling payment processing and managing notifications related to library activities. This service ensures seamless transactional operations and timely communication with users.
# Features:
Modular microservices architecture for scalability and maintainability.\
Secure authentication and authorization using JWT and Keycloak.\
Reliable messaging system with Kafka for inter-service communication.\
Efficient data management with MySQL and PostgreSQL databases.\
Caching mechanism using Redis Cache for improved performance.\
Integration with PayPal API for seamless payment processing.\
WebSocket for real-time notification.\
This project serves as a comprehensive example of implementing microservices architecture in a real-world scenario, demonstrating best practices in backend development and integration with various technologies to achieve a robust and scalable solution for managing library operations.

