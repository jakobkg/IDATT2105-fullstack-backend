# IDATT2105-fullstack-backend
[Frontend here](https://github.com/jakobkg/IDATT2105-fullstack-frontend/)

## Requirements
* JDK 17 or a compatible version
* Maven 3

### Note: This application relies on a MySQL server
A simple Docker image pre-configured with a default user is provided for convenience.
To build this image, simply navigate to the root of this project in your terminal emulator of choice and run

```sh
docker build -t fullstack-backend database
```
Then, launch a container with this image with
```sh
docker run -p 3306:3306 fullstack-backend
```

This application can also use a remote database by setting the environment variable `MYSQL_HOST` before launch


### Run the application
Once the database is available, the application can be started using
```sh
mvn spring-boot:run
```
The application will seed the database with some default data on first launch, see the `startup` package for details.

### Run tests:
To run tests, simply run the tests in your preferred IDE.
