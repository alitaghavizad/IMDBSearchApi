# IMDB Search API

A **Spring Boot REST API** for querying locally imported IMDb datasets with Java, JPA, and an embedded H2 database.

The project imports IMDb TSV data into relational tables and exposes higher-level queries such as finding movies shared by two actors, titles where the director and writer are the same person, and top-rated titles by genre.

## What it demonstrates

- Java 17 and Spring Boot
- REST API design
- Spring Data JPA
- H2 file-backed persistence
- IMDb TSV ingestion
- Relational modelling for movies, actors, crews, and ratings
- Pagination for large result sets
- Swagger / OpenAPI documentation
- Database indexing for analytical queries

## Technology stack

- Java 17
- Spring Boot 3.1
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok
- springdoc-openapi
- Maven

## API

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `GET` | `/sameMovies?actor1=...&actor2=...` | Find movies featuring both actors |
| `GET` | `/sameDirectorWriter?page=0&size=10` | Find titles whose director and writer match |
| `GET` | `/bestTitlesByGenre?genre=...` | Find top titles for a genre |
| `GET` | `/initializeDataBase` | Import the IMDb dataset into the local database |
| `GET` | `/apiCount` | Return the in-memory API call counter |

## Data model

The application models core IMDb entities including:

```text
Movie
Actor
MovieCrew
TitleRatings
```

Repositories and services keep persistence logic separate from the REST layer.

## Local database

The application uses a file-backed H2 database:

```text
jdbc:h2:file:./data/imdb;AUTO_SERVER=TRUE
```

The H2 console is enabled at:

```text
http://localhost:9090/h2-console
```

## Import IMDb data

Place the required IMDb TSV files under the project's IMDb resource directory, start the application, then call:

```text
GET /initializeDataBase
```

The importer populates the local tables and prepares the data for the search endpoints.

## Run

```bash
./mvnw spring-boot:run
```

The service starts on port `9090`.

## API documentation

With the application running, Swagger UI is available through the springdoc OpenAPI integration.

## Project structure

```text
src/main/java/com/taghavi/imdbsearchapi/
├── controller/      REST endpoints
├── service/         application/query logic
├── da/model/        JPA entities
├── da/repository/   persistence repositories
├── da/converter/    IMDb null/value conversion
└── utility/         dataset import logic
```

## Notes

Some analytical queries operate over a relatively large local IMDb dataset and can take noticeable time. Pagination is used where appropriate to keep responses manageable.

## Purpose

This project demonstrates conventional Java backend engineering around data ingestion, relational persistence, API design, and query-oriented service logic using a real public dataset.
