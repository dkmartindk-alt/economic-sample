# e-conomic Showcase Application

A simple Java web application that showcases integration with the e-conomic API, demonstrating how to retrieve and display customer and invoice information from e-conomic's demo API.

## Overview

This application provides a basic example of consuming the **[e-conomic REST API](https://restdocs.e-conomic.com/)**. It shows a simple overview of customers and allows users to drill down into individual customers to view their total sales and invoices, with the ability to download invoice PDFs.

You can view the live demo here: **[Live Demo](https://p01--economic-sample--vw2y8lk4rdww.code.run/)** *Note: The demo is hosted on Northflank's free tier, so the initial load might take a few moments if the container needs to wake up.*

**Note**: This showcase is intended for demonstration purposes only and does not include production-ready error handling, security features, or optimizations for large datasets.

**Database:** I decided not to use a database for the app since it is so small, but I mainly have used JPA / Hibernate.

## Features

- Browse a list of customers from e-conomic API 
- View customer-specific details including total sales information
- See customer's invoice history with dates and amounts
- Download invoice PDF documents directly from e-conomic API
- Real-time API integration with e-conomic's demo environment 

## Technologies Used

- **Java**: Core programming language
- **Spring Boot**: Web framework and dependency injection  
- **Thymeleaf**: Server-side Java template engine
- **HTML & CSS**: Front-end presentation and styling
- **e-conomic API**: Third-party API for business management data
- **Maven**: Build automation and dependency management

## Architecture

### Backend Components

- **Controller (`Root.java`)**: Handles HTTP requests and routes them to appropriate service methods
- **Service (`EconomicApiService.java`)**: Manages API communication with e-conomic
- **DTOs**: Plain Java objects that represent API data structures
- **Configuration**: Properties configured in `application.properties`

### Frontend Components

- **Templates**: Thymeleaf HTML templates in `/templates`
- **Layout**: Shared layout template for consistent UI
- **CSS**: Basic styling in `/static/css/style.css`

## Configuration

The application uses e-conomic's demo credentials:

```
economic.api.base-url=https://restapi.e-conomic.com
economic.api.app-secret-token=demo
economic.api.agreement-grant-token=demo
```

Port: 10000

## API Endpoints

- `GET /`: Displays all customers from e-conomic API
- `GET /customers/{customerNumber}`: Displays customer details and invoices
- `GET /customers/{customerNumber}/invoices/{invoiceNumber}/pdf`: Downloads a specific invoice PDF

## Limitations

- No error handling implemented
- No support for large datasets (no pagination)
- Uses demo tokens which have rate limits
- Not secured for production use
- No input validation performed

## Getting Started

1. Clone the repository
2. Run `mvn spring-boot:run`
3. Access the application at `http://localhost:10000`

## Files Structure

```
src/
├── main/
│   ├── java/com/example/economic/
│   │   ├── controller/
│   │   │   └── Root.java          # Main controller 
│   │   ├── service/
│   │   │   └── EconomicApiService.java  # API integration logic
│   │   ├── dto/                   # Data transfer objects
│   │   │   ├── Customer.java
│   │   │   ├── InvoiceBooked.java 
│   │   │   └── Other DTOs...
│   │   └── EconomicApplication.java # Main application class
│   ├── resources/
│   │   ├── templates/             # Thymeleaf templates
│   │   │   ├── index.html         # Customer listing page
│   │   │   ├── customers/index.html # Customer detail page
│   │   │   └── layout/
│   │   └── static/
│   │       └── css/
│   │           └── style.css      # Styling for the application
└── test/                          # JUnit tests
```

## Example Usage Flow

1. Navigate to the home page to see a list of customers from e-conomic
2. Click on a customer's number/name to view their details and invoices
3. On the customer details page, see summary stats and a list of their invoices
4. Click "PDF" link next to an invoice to download the invoice document

This application demonstrates basic API consumption, MVC architecture with Spring Boot, and client-server interaction with Thymeleaf templating.