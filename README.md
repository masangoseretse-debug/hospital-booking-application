# Ubuntu Health Hospital Booking System

A traditional Java web application for managing hospital appointments. It uses Java Servlets and JSP for server-side logic, JDBC for MySQL access, and HTML, CSS and JavaScript for the user interface. Spring Boot is not used.

## Main features

- Patient registration and role-based sign-in
- Patient, doctor, administrator and system-administrator roles
- Create, read, update, search, filter and delete appointments
- Confirmation before deletion and colour-coded success/error feedback
- Doctor-specific and patient-specific appointment views
- Administrator user search and account activation/deactivation
- Patient symptom checker with saved database results
- Responsive layout and basic keyboard-accessible controls
- MySQL primary keys, foreign keys, unique rules and indexes
- 15 meaningful user records and 10 appointment records
- JUnit validation tests

## Technology

- Java 17
- Jakarta Servlets 6 and JSP
- JDBC
- MySQL 8+
- Maven
- Apache Tomcat 10.1+
- HTML5, CSS3 and JavaScript
- JUnit 5

## Project architecture

```text
Browser (HTML/CSS/JavaScript)
        |
        v
JSP views <- Servlets and filters
                  |
                  v
              DAO classes
                  |
                  v
             JDBC / MySQL
```

`model` holds the data objects, `dao` contains SQL and CRUD operations, `web` handles HTTP requests and permissions, `views` renders the pages, and `database/schema.sql` creates and seeds the database. This follows a simple Model-View-Controller structure.

## First-time setup

1. Install JDK 17, Maven, MySQL 8 and Apache Tomcat 10.1.
2. Open MySQL Workbench and run `database/schema.sql` in full.
3. Copy `src/main/resources/db.properties.example` to `src/main/resources/db.properties`.
4. Edit the new file and enter your own MySQL username and password.
5. Never commit `db.properties`; it is excluded by `.gitignore`.
6. In the project directory, run:

```bash
mvn clean test package
```

7. Deploy `target/hospital-booking.war` to Tomcat's `webapps` folder.
8. Start Tomcat and open `http://localhost:8080/hospital-booking/`.

## IntelliJ IDEA

Open the folder containing `pom.xml` and select **Load Maven Project** when prompted. With IntelliJ IDEA Ultimate, add a local Tomcat 10 run configuration and deploy the `hospital-booking:war exploded` artifact. With Community Edition, run Maven to create the WAR and copy it to Tomcat manually.

## VS Code

Install **Extension Pack for Java** and **Community Server Connectors**. Open the folder containing `pom.xml`, wait for Maven import to finish, run `Maven: package`, then add Tomcat and deploy the generated WAR. This project does not use Node.js or `npm install`.

## Demo accounts

All demo passwords are `Password1`.

| Role | Email |
|---|---|
| Patient | patient@ubuntuhealth.co.za |
| Doctor | doctor@ubuntuhealth.co.za |
| Administrator | admin@ubuntuhealth.co.za |
| System administrator | sysadmin@ubuntuhealth.co.za |

The seeded passwords are suitable only for the classroom demonstration. A production application should use BCrypt, Argon2 or PBKDF2 with an individual random salt and stronger privacy controls.

## Testing

Run unit tests with `mvn test`. For integration testing, start MySQL and Tomcat, then verify login and appointment CRUD with the test matrix in `docs/TESTING.md`.

## GitHub workflow

Create an empty repository on GitHub without adding a README. Then open this project in a terminal and run:

```bash
git init
git add .
git commit -m "Initial Java hospital booking application"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/hospital-booking.git
git push -u origin main
```

Each group member should work on a feature branch:

```bash
git switch -c feature/database
git add .
git commit -m "Add appointment database indexes"
git push -u origin feature/database
```

Open a pull request on GitHub, let another member review it, and merge it into `main`. Do not upload a ZIP, `target`, IDE folders, passwords, database exports containing private patient data, or dependency folders.

## References

- Jakarta Servlet specification: https://jakarta.ee/specifications/servlet/
- Apache Tomcat documentation: https://tomcat.apache.org/tomcat-10.1-doc/
- MySQL Connector/J documentation: https://dev.mysql.com/doc/connector-j/en/
- Maven documentation: https://maven.apache.org/guides/
- JUnit 5 user guide: https://junit.org/junit5/docs/current/user-guide/
