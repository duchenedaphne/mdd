# Monde-de-dév by ORION (MVP)

🤝 Boost your career : meet with developpers with common interests, to find your next dev job or to get your work done.

## 🛠 Software tools

- [Angular CLI](https://github.com/angular/angular-cli) 14.1.3
- [Java](https://www.oracle.com/java/technologies/downloads/) 11
- Spring Boot
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL](https://www.mysql.com/fr/downloads/)

## Start the project

Clone this repository :
> git clone https://github.com/duchenedaphne/mdd

### MySQL :

Create a database with the name `mdd`.

### Back-end :  

Go inside folder :
> cd back

In your IDE :  
Add 2 environment variables with your database credentials, for the `application.properties` file :

>spring.datasource.username=${DB_USER}

>spring.datasource.password=${DB_PASSWORD}

Install the dependencies :
> mvn clean install

Launch the backend server with Spring Boot and Maven :  
> mvn spring-boot:run

### Front-end :

Go inside folder:
> cd front

Install the dependencies :
> npm install

Launch the dev server :
> ng serve  

or

> npm run start

Navigate to http://localhost:4200/

Build the project :
> ng build

The build artifacts will be stored in the `dist/` directory.

## ✍ Author
Daphné Duchêne
