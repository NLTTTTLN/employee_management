-- Create database
CREATE DATABASE employee_management;
-- Use the database
USE employee_management;

-- Create the account table
CREATE TABLE account (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role ENUM('a0', 'manager', 'employee') NOT NULL
);

-- Create the employee table
CREATE TABLE employee (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    gender ENUM('Male', 'Female') NOT NULL,
    dob DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_num VARCHAR(15),
    address TEXT,
    department VARCHAR(50),
    salary DECIMAL(10, 2),
    FOREIGN KEY (username) REFERENCES account(username) ON DELETE CASCADE
);

-- Create the manager table
CREATE TABLE manager (
    manager_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    gender ENUM('Male', 'Female') NOT NULL,
    dob DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_num VARCHAR(15),
    address TEXT,
    FOREIGN KEY (username) REFERENCES account(username) ON DELETE CASCADE
);

-- Create the admin table
CREATE TABLE admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES account(username) ON DELETE CASCADE
);

-- Create the leave table
CREATE TABLE employee_leave (
    leave_id INT AUTO_INCREMENT PRIMARY KEY,
    leaver_id INT NOT NULL,
    date DATE NOT NULL,
    reason TEXT NOT NULL,
    FOREIGN KEY (leaver_id) REFERENCES employee(employee_id) ON DELETE CASCADE
);
CREATE TABLE manager_leave (
    leave_id INT AUTO_INCREMENT PRIMARY KEY,
    leaver_id INT NOT NULL,
    date DATE NOT NULL,
    reason TEXT NOT NULL,
    FOREIGN KEY (leaver_id) REFERENCES manager(manager_id) ON DELETE CASCADE
);-- ádasdadasd
