# Car Sharing System
This is a simple car sharing system implemented in Java, utilizing an H2 database for storing company, car, and customer data. The system allows managers to manage companies and cars, and customers to rent and return cars.

## Table of Contents
- [Requirements](#requirements)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Commands](#commands)
- [Project Structure](#project-structure)
- [License](#license)

## Requirements
* Java Development Kit (JDK)
* H2 Database

## Getting Started
1. Clone the repository:
```shell
git clone https://github.com/your-username/car-sharing-system.git
cd car-sharing-system
```
2. Compile the project:
```shell
javac -d out -sourcepath src src/carsharing/Main.java
```
3. Run the program:
```shell
java -classpath out carsharing.Main -databaseFileName <database_name>
```
Replace `<database_name>` with the desired name for your H2 database.

## Usage
Follow the on-screen instructions to navigate through the car sharing system. You can log in as a manager or a customer, create entities (companies, cars, customers), list entities, and perform various operations.

## Commands
* `Log in as a manager:` Allows managers to manage companies and cars.
* `Log in as a customer:` Allows customers to rent, return, and view rented cars.
* `Create a customer:` Create a new customer in the system.
* `Exit:` Exit the program.

## Project Structure
* `src/` 
  * `carsharing/` 
    * `Car.java:` Car class definition. 
    * `Company.java:` Company class definition.
    * `Customer.java:` Customer class definition.
    * `App.java:` Main application class responsible for user interaction.
    * `Database.java:` Database class for interacting with the H2 database.
    * `Main.java:` Entry point of the program.
* `out/:` Compiled classes directory.
* `data/:` Directory for H2 database files.
* `README.md:` Project documentation.