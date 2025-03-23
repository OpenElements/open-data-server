# Open Elements Backend Template

http://localhost:8080 <- Show all available endpoints as JSON
http://localhost:8080/swagger-ui/index.html <- Swagger UI
http://localhost:8080/v3/api-docs.yaml <- OpenAPI 3.0 doc

 

## Start the application

The project is a Spring Boot based webservice. To run it, you need to have Java 21 installed.

To run the project, you can use the following command:

```shell
./mvnw spring-boot:run
```

The application will be available at `http://localhost:8080`.

## Build the application

To build the application, you can use the following command:

```shell
./mvnw clean verify
```

This will build the application and run the tests.

## Run the application in a Docker container

To run the application in a Docker container, you need to build the Docker image and run the container.
The Docker image is built by the following command that need to be executed in the root directory of the project:

```shell
docker build -t backend-template .
```

This will build the Docker image with the name `backend-template`.
To run the Docker container, execute the following command:

```shell
docker run -d -p 8080:8080 backend-template
```

This will start the Docker container and the application will be available at `http://localhost:8080`.

## Deployment

Today Open Elements uses [koyeb](https://www.koyeb.com) to deploy the frontend and backend services.