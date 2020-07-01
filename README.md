# Simple Social Network 
![GitHub](https://img.shields.io/github/license/agateownz/simple-social-network?style=flat-square)
![Codecov](https://img.shields.io/codecov/c/github/agateownz/simple-social-network?style=flat-square)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/agateownz/simple-social-network/API%20Deploy%20to%20Heroku?label=api%20build&style=flat-square)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/agateownz/simple-social-network/Front%20Deploy%20to%20Heroku?style=flat-square)

This project was created to consolidate my knowledge of React and Spring Boot, while building a simple - but complete - social network.

## Getting Started

The project is divided in two parts: `backend` and `frontend`.

### Prerequisites

To get things up and running you need to have [Docker](https://www.docker.com/) and [docker-compose](https://docs.docker.com/compose/) installed and running on your machine. You will also need `Java 11 or greater` installed along with `Maven` build automation tool. I recommend using something like [SDKMAN](https://sdkman.io/usage) to install theses libs on your system, but it's up to you.

Unfortunately you need to have access to an Amazon S3 Bucket in order to run the application. If you don't have an Amazon Web Services account yet, you can use many things for free during 12 month period after you register.

To set everything up we will use an `.env` file, which will have all the necessary external configuration for our applications. To get started quickly you can create a copy of the `.env.example` file and rename it to just `.env`.

Here is the list the environment variables that you need to setup before you can start the application.

| Environment Variable | Description                 |
|----------------------|-----------------------------|
| AWS_ACCESS_KEY       | Your AWS Account Access Key |
| AWS_SECRET_KEY       | Your AWS Account Secret Key |
| AWS_REGION           | Your AWS S3 Bucket Region   |
| STORAGE_BUCKET_NAME  | Your AWS S3 Bucket Name     |

### Building backend

To build the backend follow the steps below:

```bash
cd backend
mvn package
```

After that you should have a `.jar` file in the `backend/target/` directory.


### Running

If you've followed along, you just need to enter the following in your console:

```bash
docker-compose up
```