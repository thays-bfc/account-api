# Account API

This is a bank account management API, developed according to the rules:

- To open an account CPF document and name are needed (Customer)
- Only one account per Customer
- Transactions between accounts are valid
- Should not accept account's negative balance
- Each deposit transaction must be less than R$2001
- There are no limits for transactions between accounts, also those are free

## 🚀 About the project

### 📋 Prerequisites

You need to have Docker and docker-compose installed

- [Install docker](https://docs.docker.com/engine/install/)
- [Install docker-compose](https://docs.docker.com/compose/install/)

### 🔧 Installing

After downloading the project, open it at the project root path **.../api.account**.
You should execute the commands from start.sh file.

Linux distribution:

```
sh ./start.sh
```

or run:

```
docker run --rm -u gradle -v "$PWD":/home/gradle/project -w /home/gradle/project gradle:jdk11 gradle build
docker-compose up
```

Now you should be able to access the API's documentation at:

- [Swagger Documentation](http://localhost:8000/swagger-ui.html)

### ✅ Usage

#### Create account:

POST /account

Body:
```
{
    "name": "John Doe",
    "cpf": "093.240.550-97"
}
```

#### Deposit money:

POST /transaction/deposit

Body:
```
{
    "accountCode": 1,
    "depositValue": 2000
}
```

#### Transfer money:

POST /transaction/transfer

Body:
```
{
    "fromAccountCode": 1,
    "toAccountCode": 2,
    "transferValue": 60
}
```

#### Retrieve account:

GET /account/{id}

```
PathVariable: id
```

#### Retrieve account transactions:

GET /transaction/account/{code}

```
PathVariable: account code
```

## 🛠️ Built with
* [Java 11](https://www.oracle.com/br/java/technologies/javase-jdk11-downloads.html) - Development platform
* [Spring-Boot](https://spring.io/projects/spring-boot) - Framework for java based applications
* [Gradle](https://gradle.org/) - Build tool
* [Postgresql](https://www.postgresql.org/) - SQL database
* [Docker](https://www.docker.com/company) - Used to create application image
* [SpringFox-Swagger](https://springfox.github.io/springfox/docs/current/) - Create API documentation
* [JUnit5](https://junit.org/junit5/) - Testing framework
---