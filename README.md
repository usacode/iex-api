# Documentation for Stock Demo API

## Overview
The Stock Demo API service is designed to manage stock-related operations within a RESTful service environment. It exposes endpoints for calculating daily returns, alpha values, and volatility of stock symbols over specified date ranges. This App implements components to handle specific calculations and validations, ensuring robust and accurate data handling.
## Main Services
- **IAlphaService:** Provides methods for calculating alpha values for stock symbols against benchmarks.
- **IReturnService:** Offers functionality to calculate daily returns for stock symbols.
- **IVolatilityService:** Handles the calculation of volatility for stock symbols.
- **IValidationService:** Validates text and date inputs to prevent errors in data processing.

## API Endpoints
### 1. Get Daily Returns
   - **Endpoint:** GET /api/v1/returns
   - **Description:** Retrieves the daily return for a given stock symbol within a specified date range.
   - **Parameters:**
     - **symbol:** The ticker symbol of the stock.
     - **from (optional):** The start date of the period (defaults to the start of the current year if not specified).
     - **to (optional):** The end date of the period (defaults to the current date if not specified).
### 2. Get Alpha
   - **Endpoint:** GET /api/v1/alpha
   - **Description:** Calculates the alpha of a stock symbol relative to a benchmark symbol over a given date range.
   - **Parameters:**
      - **symbol:** The ticker symbol of the stock.
      - **benchmark:** The ticker symbol of the benchmark.
      - **from (optional):** The start date of the date range (defaults to the start of the current year if not specified).
      - **to (optional):** The end date of the date range (defaults to the current date if not specified).
### 3. Get Volatility
   - **Endpoint:** GET /api/v1/volatility
   - **Description:** Returns volatility data points for a given stock symbol within a specified date range.
   - **Parameters:**
      - **symbol:** The ticker symbol of the stock.
      - **from (optional):** The start date (defaults to the current date if not specified).
      - **to (optional):** The end date (defaults to the current date if not specified).

## Validation
The **Validation** component ensures that user inputs meet the expected format and logical constraints, enhancing the reliability of the API.
### Methods
- **textValidation:** Checks if the text parameter is neither blank nor null. Throws an error with 400 status code if the validation fails.
- **dateValidation:** Validates the format and logical consistency of date inputs. It ensures the dates are in correct format and the end date is not before the start date. Throws an error with 400 status code if any condition is not met.
## Exception Handling
The classes employ robust exception handling to manage errors effectively. Errors such as incorrect data formats, logical inconsistencies in date ranges trigger specific exceptions and unexpected server errors which are communicated back to the client with appropriate HTTP status codes and error messages.

## Application Setup and Testing
### Development Environment
The application is developed using Java 17 and Spring Boot, ensuring a modern, robust backend service. The source code is available on GitHub, allowing for version control and collaborative development.
### Running the Application
The application can be executed in two ways:
1. ***From Source Code:***
   - Clone the repository from GitHub.
   - Navigate to the project directory and execute the application using the command: ***java -jar run.***
2. ***Using a Pre-built Jar:***
   - Download the JAR file from the project's GitHub repository.
   - Run the application by executing: ***java -jar demo-0.0.1.jar***  in your terminal or command prompt, where ***[demo-0.0.1.jar]*** is the name of the downloaded JAR file.
### Testing the Application
The application provides a Swagger UI, which is a user-friendly interface for testing API endpoints interactively. This can be accessed once the application is running, usually via the URL path ***http://localhost:8080/demo/swagger-ui.html***.

Alternatively, API endpoints can be tested using Postman:

  - Set up Postman with the base URL of the deployed application.
  - Configure the HTTP requests according to the API specifications provided in the Swagger documentation to test different functionalities.
## Frontend Integration and Testing
### React JS Application
The frontend of this application is developed using React JS, providing a dynamic user interface that interacts with the backend services. 

The React application is also hosted on GitHub, enabling easy access and collaborative development.
Testing the Backend with the React App
The React application is designed to communicate with the backend, serving as a practical tool for real-world testing of the backend functionalities.
  
To test the backend using the React app:
1. ***Clone the Frontend Repository:***
    - Obtain the repository URL: https://github.com/usacode/react-iex from GitHub and use ***git clone*** to download the React application to your local machine.
2. ***Setup and Run:***
    - Navigate to the project directory.
    - Run ***npm install*** to install all required dependencies.
    - Start the application using ***npm start***, which will typically host the frontend app on ***localhost:3000***.
3. ***Interact with the Backend:***
    - Ensure that the backend application is running locally at ***localhost:8080***.
    - Use the React app's interface to perform actions that trigger API calls to the backend, verifying the interactions and responses directly through the frontend.

