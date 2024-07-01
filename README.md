# chatop

chatop is n application for managing vacation home reservations, offering users the ability to book accommodation, list their own property for rent and send message if you are interested by a rent.

## The Front-end uses:
Angular

## The Back-end uses:
Java Spring boot

## Spring Boot starter dependency:
Web, Security, DataJPA, Lombok, OAuth2Client, MySQLDriver

Getting started
---------------

[](https://github.com/guillaumebeysson/chatop)

### Clone the project

[](https://github.com/guillaumebeysson/chatop.git)

### Back-end

[](https://github.com/CodeByGaetan/ChaTop#back-end)

-   Open your IDE (VS Code, Eclipse, etc.) in the directory : `/ChaTop/ChaTopApi`
-   Run `mvn spring-boot:run` to launch the back-end in developpment mode
-   Or run `mvn package` to build the project and then run `java -jar target/api-0.0.1-SNAPSHOT.jar` to launch the built package.

### Front-end

[](https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring)

-   Open your IDE (VS Code, Eclipse, etc.) in the directory : `/ChaTop/ChaTopWeb`
-   Run `npm install` to install the dependencies
-   Run `ng serve` to start the front-end development server
-   To use the app, navigate to <http://localhost:4200/>

### Database

[](https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring)

Copy script SQL:

> /ressources/sql/script.sql

MySQL Database install :

-   Install mySQL on the localhost and enable the service
-   From this app root directory, launch and connect to mySQL
-   Run `CREATE DATABASE chatop;` to create the database
-   Run `USE chatop;` to use the newly created database
-   Run `SOURCE script_bdd.sql;` to create the database tables

Ressources
----------

### Swagger

HTML documentation : <http://localhost:3001/api/swagger-ui/index.html>

### Mockoon

[](https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring)

Download Mockoon here: <https://mockoon.com/download/>

After installing you could load the environement

> /ressources/mockoon/rental-oc.json

directly inside Mockoon

> File > Open environmement

For launching the Mockoon server click on play bouton

### Postman collection

[](https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring)

For Postman import the collection

> /ressources/postman/rental.postman_collection.json
