# SQL Injection in Hibernate & Spring JDBC - Security Demo

A comprehensive demonstration of SQL injection vulnerabilities and prevention techniques using Hibernate 6 and Spring JDBC with practical examples.

📘 Blog Post: [SQL Injection in Hibernate | Spring JDBC and Prevent it](https://jarmx.blogspot.com/2023/02/sql-injection-in-hibernate-spring-jdbc.html)

## 🚨 Disclaimer

This project is for educational purposes only. The code demonstrates security vulnerabilities to help developers understand and prevent SQL injection attacks. Do not use vulnerable code patterns in production applications.

## 📋 Table of Contents

- [Introduction](#introduction)
- [Technology Stack](#technology-stack)
- [Environment Setup](#environment-setup)
- [Database Setup](#database-setup)
- [SQL Injection Attack Examples](#sql-injection-attack-examples)
- [Prevention Techniques](#prevention-techniques)
- [Running the Demo](#running-the-demo)
- [References](#references)

## 🎯 Introduction

SQL injection is a critical security vulnerability that allows attackers to execute malicious SQL code through web applications. This occurs when user-supplied data is not properly sanitized or validated before being used in database queries.

This project demonstrates:
- 3 common SQL injection attack techniques
- How these attacks work in Hibernate and Spring JDBC
- Effective prevention methods
- Best practices for secure database interactions

## 🛠 Technology Stack

- **Spring Boot**: 3.0.2
- **Java**: 17
- **Spring JDBC**: 6
- **Hibernate**: 6
- **Jakarta EE**: 3.1
- **MySQL**: Latest
- **Docker**: For containerized MySQL
- **Maven**: Dependency management
- **IDE**: IntelliJ IDEA

## 🚀 Environment Setup

### Prerequisites

- Java 17+
- Docker
- Maven
- IDE (IntelliJ IDEA recommended)

### MySQL Database with Docker

```bash
# Pull MySQL Docker image
docker pull mysql/mysql-server:latest

# Run MySQL container
docker run -d -p 3306:3306 \
  --name mysql-docker-container \
  -e MYSQL_ROOT_PASSWORD=mypass \
  -e MYSQL_DATABASE=test_db \
  -e MYSQL_USER=test \
  -e MYSQL_PASSWORD=test_pass \
  mysql/mysql-server:latest
```

## 🗄 Database Setup

Execute the following SQL script to create the test database:

```sql
-- Create customers table
CREATE TABLE customers (
    id BIGINT PRIMARY KEY,
    name VARCHAR(128),
    password VARCHAR(128),
    lastname VARCHAR(128)
);

-- Insert sample data
INSERT INTO customers (id, name, password, lastname) VALUES 
    (1, 'Henry', '$2a$10$iWlXVjsSU9.X0oxEjUwyYe3EOc5X2hqacWY7uuyV0BwonTt5SapSu', 'test1'),
    (2, 'Henry', '$2a$10$iWlXVjsSU9.X0oxEjUwyYe3EOc5X2hqacWY7uuyV0BwonTt5SapSu', 'test2'),
    (3, 'Henry', '$2a$10$iWlXVjsSU9.X0oxEjUwyYe3EOc5X2hqacWY7uuyV0BwonTt5SapSu', 'test3'),
    (4, 'admin', '81dc9bdb52d04dc20036dbd8313ed055', 'test4'),
    (5, 'User5', '$2a$10$iWlXVjsSU9.X0oxEjUwyYe3EOc5X2hqacWY7uuyV0BwonTt5SapSu', 'test5'),
    (6, 'User6', '$2a$10$iWlXVjsSU9.X0oxEjUwyYe3EOc5X2hqacWY7uuyV0BwonTt5SapSu', 'test6'),
    (7, 'User7', '$2a$10$iWlXVjsSU9.X0oxEjUwyYe3EOc5X2hqacWY7uuyV0BwonTt5SapSu', 'test7'),
    (8, 'User8', '$2a$10$iWlXVjsSU9.X0oxEjUwyYe3EOc5X2hqacWY7uuyV0BwonTt5SapSu', 'test8'),
    (9, 'User9', '$2a$10$iWlXVjsSU9.X0oxEjUwyYe3EOc5X2hqacWY7uuyV0BwonTt5SapSu', 'test9'),
    (10, 'User10', '$2a$10$iWlXVjsSU9.X0oxEjUwyYe3EOc5X2hqacWY7uuyV0BwonTt5SapSu', 'test10');
```

## ⚠️ SQL Injection Attack Examples

### 1. Line Comments Attack (Spring JDBC)

**Vulnerable Code:**
```java
String sql = "SELECT * from customers where name ='" + name + "' AND id=" + id;
var list = jdbcTemplate.query(sql, (rs, rowNum) -> 
    Customer.builder()
        .id(rs.getInt("id"))
        .name(rs.getString("name"))
        .build()
);
```

**Attack Payload:**
```java
var name = "Henry' -- '";
var id = 1;
```

**Result:** Returns all customers with name 'Henry' instead of specific record.

### 2. Bypassing Login Screens

**Vulnerable Code:**
```java
String sql = "SELECT * from customers where name ='" + name + "' AND password='" + password + "'";
```

**Attack Payloads:**
```java
// Successful attacks
var name = "Henry' or '1'='1 --";
var name = "Henry' # ";
var name = "Henry' or 1=1 -- ";
```

### 3. Boolean SQL Injection

**Vulnerable Code:**
```java
String sql = "SELECT * from customers where name ='" + name + "'";
```

**Attack Payload:**
```java
var name = "abc' or '1'='1";
```

**Result:** Returns all records instead of none.

## 🛡️ Prevention Techniques

### 1. Parameterized Queries (Spring JDBC)

**Using Positional Parameters:**
```java
String sql = "SELECT * from customers where name = ? AND id = ?";
var list = jdbcTemplate.query(sql, (rs, rowNum) ->
    Customer.builder()
        .id(rs.getInt("id"))
        .name(rs.getString("name"))
        .build()
    , new Object[]{name, id});
```

**Using Named Parameters:**
```java
String sql = "SELECT * from customers where name = :name AND id = :id";
MapSqlParameterSource parameters = new MapSqlParameterSource();
parameters.addValue("name", name);
parameters.addValue("id", id);

var list = namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) ->
    Customer.builder()
        .id(rs.getInt("id"))
        .name(rs.getString("name"))
        .build()
);
```

### 2. Parameterized Queries (Hibernate)

```java
var list = entityManager
    .createQuery("SELECT e FROM customers e where name = :name AND password = :password", Customer.class)
    .setParameter("name", name)
    .setParameter("password", password)
    .getResultList();
```

### 3. Additional Security Measures

- **Keep Software Updated**: Regularly update frameworks and dependencies
- **Web Application Firewall (WAF)**: Filter and monitor HTTP traffic
- **Stored Procedures**: Use properly constructed stored procedures
- **Input Validation**: Implement allow-list input validation
- **Principle of Least Privilege**: Limit database user permissions

## 🧪 Running the Demo

### Test Classes Structure

**Spring JDBC Test Classes:**
- `BooleanAttacksJDBCTemplate`
- `BypassingLoginAttacksJDBCTemplate`
- `LineCommentsAttacksJDBCTemplate`

**Hibernate 6 Test Classes:**
- `BooleanAttacksHibernate`
- `BypassingLoginAttacksHibernate`
- `LineCommentsAttacksHibernate`

### Execution Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/HenryXiloj/demo-sql-injection.git
   cd demo-sql-injection
   ```

2. **Start the MySQL container:**
   ```bash
   docker run -d -p 3306:3306 --name mysql-docker-container \
     -e MYSQL_ROOT_PASSWORD=mypass \
     -e MYSQL_DATABASE=test_db \
     -e MYSQL_USER=test \
     -e MYSQL_PASSWORD=test_pass \
     mysql/mysql-server:latest
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Execute test classes** to see vulnerable vs secure implementations

## 📚 Key Learning Points

- **Never concatenate user input directly into SQL queries**
- **Always use parameterized queries/prepared statements**
- **Implement proper input validation and sanitization**
- **Use ORM frameworks correctly with parameterized queries**
- **Regular security testing and code reviews are essential**

## 🔗 References

- [OWASP SQL Injection Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/SQL_Injection_Prevention_Cheat_Sheet.html)
- [SQL Injection Cheat Sheet](https://www.invicti.com/blog/web-security/sql-injection-cheat-sheet/)
- [Web Application Firewall (WAF)](https://www.cloudflare.com/learning/ddos/glossary/web-application-firewall-waf/)

---

**⚠️ Remember: With great power comes great responsibility. Use this knowledge to build more secure applications, not to exploit vulnerabilities.**
