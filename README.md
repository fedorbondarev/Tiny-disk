# T-disk

T-disk is a resource storage service designed to efficiently manage file and text uploads and storage. It is built in
Scala leveraging the functional programming capabilities provided by Cats-Effect, the expressive HTTP endpoint
definitions of Tapir, and the database access with Doobie.

## Technologies

- **Scala:** The project is written in Scala, providing a strong type system and functional programming features.
- **Cats-Effect:** For managing side effects in a purely functional way.
- **Tapir:** Used to declare HTTP endpoints in a type-safe manner.
- **Doobie:** A pure functional JDBC layer for database access.

## Requirements

- Scala 2.13.x
- SBT (Scala Build Tool)
- Cats Effect 3.x
- Tapir 1.10.x
- Doobie 1.0.x
- PostgreSQL

## Running the project

1. Clone the repository:
   ```bash
   git clone https://github.com/fedorbondarev/t-disk.git
   cd t-disk
   ```
2. Set Up Database:
    - Install PostgreSQL.
    - Create a new database for T-disk.
    - Configure the database connection in application.conf.

3. Build and Run:
   ```bash
   sbt run
   ```

4. API Documentation:

   Once the server is running, you can access the API documentation by navigating to http://localhost:8080/docs.

## Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are greatly appreciated.

## Contact

Fedor Bondarev – [@fedorbondarev](https://t.me/fedorbondarev) – fedor.bondarev.314@yandex.ru
