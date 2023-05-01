[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=gusandchbh_Stock-trading-app&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=gusandchbh_Stock-trading-app) [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=gusandchbh_Stock-trading-app&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=gusandchbh_Stock-trading-app) [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=gusandchbh_Stock-trading-app&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=gusandchbh_Stock-trading-app) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=gusandchbh_Stock-trading-app&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=gusandchbh_Stock-trading-app)

# Stock Trading App
This application is a personal side project, developed in my spare time, that aims to simulate a stock trading platform, allowing users to invest fictional money and explore many of the standard features found in real trading applications. While the project is still under development and not yet complete, the ultimate goal is to incorporate various technologies such as Java 17, Spring Boot, MariaDB, and third-party APIs like Yahoo Finance. The application will also include user authentication using JWT, password policy enforcement with Passay, and user-to-user communication.

The primary objective of this project is to serve as a learning platform and an opportunity to experiment with a wide range of technologies in a hands-on manner, all while working towards creating a feature-rich stock trading simulation.

### Features
- User registration and login with JWT authentication ✔
- Real-time stock data fetched from Yahoo Finance API ✔
- Invest and manage a virtual stock portfolio with fictional money
- Search and view detailed stock information
- Buy and sell stocks with a user-friendly interface
- Communication between users for networking and sharing ideas
- Password policy enforcement using Passay ✔
- Use of Lombok for reducing boilerplate code ✔
- Utilization of Jakarta Persistence for object-relational mapping ✔
- Implementation of Data Transfer Objects (DTOs) for data handling ✔

### Prerequisites
To run this application, you need the following software installed on your system:

- Java 17
- MariaDB

### Installation
Clone the repository:

```
git clone https://github.com/yourusername/stock-trading-app.git
```
Navigate to the project directory:
```
cd stock-trading-app
```
Create a MariaDB database and update the application.properties file with your database credentials:
```
spring.datasource.url=jdbc:mariadb://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
```
Run the application with Spring Boot:
```
./mvnw spring-boot:run
```

### License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

### Contact
If you have any questions or would like to get in touch, please reach out to me at gusandchbh@student.gu.se
