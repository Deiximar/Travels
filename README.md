# Happy Travel

## Overview

The Travels API is a backend application for managing travel experiences. It allows users to register, log in, and manage their travels, including adding, updating, deleting, and viewing travel experiences. The API is built using Spring Boot, JPA for ORM, and it follows the REST architectural style.

## Features 📋

User Management:

- #### User registration with unique email validation.

- #### User login with email and password authentication.

- #### Fetch user details and associated travels.

Travel Management:

- #### Update existing travel entries.

- #### Delete travel entries.

- #### View all travels with pagination.

- #### Search travels by title or location with pagination and prioritization of the user's travels.

Search Functionality:

- #### Search for travels based on title or location, prioritizing the current user's travels.

## Technologies Used 🛠️

- Java 17

- Junit con Mokito

- PostgreSQL

- Visual Studio Code

- CORS configuration for allowing requests from the frontend

## Installation Guide 🔧

- Acceda a la página del repositorio online.

- Copie la URL del repositorio que aparece al acceder al botón 'Code'

- Ingresar a Visual Studio Code y en la Terminal escribimos lo siguiente:

```bash

git clone (y el enlace copiado)

```

## Environment Configuration

- H2 Database: The project is configured with H2 in-memory database for development. No additional setup is required.

- MySQL/PostgreSQL: For production, you can replace the H2 configuration with MySQL or PostgreSQL in the application.properties file.

## Contribution

Contributions are welcome. Please follow these steps:

1. **Fork the repository:**

````

git fork https://github.com/Deiximar/travels

````

2. **Create a new branch:**

````

git checkout -b feature/new-feature

````

3. **Make your changes and commit them:**

````

git commit -am 'Add new feature'

````

4. **Push to the branch:**

````

git push origin feature/new-feature

````

5. **Open a Pull Request.**


## Frontend Integration

For the frontend setup and more details, refer to the Frontend README.

## Autoras ✒️

- [**Deiximar**](https://github.com/Deiximar)

- [**Carolina**](https://github.com/CarolBV)

- [**Rossemary**](https://github.com/castellanorn)

- [**Eugenia**](https://github.com/Euge-Saravia)

- [**Adriana**](https://github.com/Adrianaortiz00)