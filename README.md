# Command-Based Application (Java & MySQL)

## Project Overview
This is a command-based application developed using Java and MySQL, designed to provide efficient data management and interaction through a text-based interface.

## Prerequisites
- Java Development Kit (JDK) 11 or higher
- MySQL Database Server
- MySQL Connector/J

## Setup Instructions

### Database Configuration
1. Create a MySQL database for the application
2. Update database connection parameters in `DatabaseConnection.java`:
   - Database URL
   - Username
   - Password

### Dependencies
- Add MySQL Connector/J to your project's classpath

## Building the Application
```bash
javac -cp .:mysql-connector-java-8.0.1.jar *.java
```

## Running the Application
```bash
java -cp .:mysql-connector-java-8.0.1.jar MainApplication
```

## Key Features
- Command-line interface
- CRUD operations with MySQL database
- Error handling and input validation
- Secure database connections

## Project Structure
```
project-root/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── DatabaseConnection.java
│   │   │   ├── Interface.java
│   │   │   └── BucketList.java
│   │   └── resources/
│   │       └── config.properties
│
├── lib/
│   └── mysql-connector-java-8.01.jar
│
└── README.md
```

## Troubleshooting
- Ensure MySQL service is running
- Verify database credentials
- Check network connectivity
- Confirm MySQL Connector/J is correctly added

## License
@akarshmi

## Contact
akarshmi.am@gmail.com
