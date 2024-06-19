# TODO App

This is a simple TODO application built with Java and Spring Boot. It allows users to manage their tasks through a RESTful API.
 User Passwords are protected by BCryptPasswordEncoder. The unit tests coverege is above 95%. All of the api curls are 
mentioned below.

## Table of Contents

- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
  - [Get All Users](#get-all-users)
  - [Fetch User](#fetch-user)
  - [Register User](#register-user)
  - [Update User](#update-user)
  - [Partially Update User](#partially-update-user)
  - [Delete User](#delete-user)
  
- [Swagger UI](https://app.swaggerhub.com/apis-docs/URVIKLODHE007/Todo/1.0.0)
  https://app.swaggerhub.com/apis-docs/URVIKLODHE007/Todo/1.0.0

## Installation

1. **Clone the repository**
   ```sh
   git clone https://github.com/Urvik07/TODO_APP_JAVA.git
   cd TODO_APP_JAVA
2. **Build the project** Make sure you have Maven installed. Run the following command to build the project:

    ```sh
    mvn clean install

3. **Run the Application**
   ```sh
    mvn spring-boot:run
   
4. **API Endpoints** copy these and paste it in postman

   1. GET API -- Fetch a specific user by username.
  ```sh 
   curl -X GET "http://localhost:8080/api/user/all" -H "accept: application/json"
  ```
  2.POST API -- Register a new user. in postman body do change requestBody
  ```sh 
curl -X POST "http://localhost:8080/api/user/register" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"username\": \"string\", \"email\": \"string\", \"password\": \"string\"}"
  ```
3.PUT API -- Update user details.
  ```sh 
curl -X PUT "http://localhost:8080/api/user/update?username={username}" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"username\": \"string\", \"email\": \"string\", \"password\": \"string\"}"
  ```
4.PATCH API --  Partially update user details. Can change a specific field such as email or password..
  ```sh 
curl -X PATCH "http://localhost:8080/api/user/updatePartial?username={username}" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"email\": \"newemail@example.com\"}"
  ```

5.GET API -- Retrieves a list of all users.
  ```sh 
curl -X GET "http://localhost:8080/api/user/all" -H "accept: application/json"
  ```

6.DELETE API -- Delete a user by username.
  ```sh 
curl -X DELETE "http://localhost:8080/api/user/delete?username={username}" -H "accept: application/json"
  ```

7.Unit test are included in src/test folder 
