
# Employee Application

## Description

This is a RESTful API application developed using Spring Boot. The application manages employee information, allowing users to create, read, update and delete employee records.

## Features

1. Create a new employee.
2. Get details of all employees.
3. Get details of a single employee by ID.
4. Update details of an existing employee.
5. Delete an employee by ID.

## Technologies Used

* Java 17
* Spring Boot3.1.0
* Spring Data JPA
* H2 Database
* Maven 

## Installation and Running

1. Please Clone this repository to your local machine.
2. Import the project as a Maven project in your favorite IDE.
3. Run the application using the IDE's built-in Spring Boot support, or use the command line: `./mvnw spring-boot:run`
4. The application will be accessible at `http://localhost:8080/api/employees`

## API Endpoints

* `POST /api/employees` - Create a new employee.
* `GET /api/employees` - Get a list of all employees.
* `GET /api/employees/{id}` - Get details of a specific employee.
* `PUT /api/employees/{id}` - Update an existing employee.
* `DELETE /api/employees/{id}` - Delete an employee.

## Contributing

Please feel free to fork this repository and submit pull requests. All contributions are welcome!

## License

This project is open source, under the terms of the [MIT License](https://opensource.org/licenses/MIT).

