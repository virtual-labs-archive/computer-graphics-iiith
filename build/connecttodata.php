<?php
$servername = "localhost";
$username = "root";
$password = '';

// Create connection
$conn = new mysqli($servername, $username, $password);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
echo "Connected successfully";

// Create database
$sql = "CREATE DATABASE feedback";
if ($conn->query($sql) === TRUE) {
    echo "Database created successfully";
} else {
    echo "Error creating database: " . $conn->error;
}
$conn = new mysqli($servername, $username, $password, "feedback");
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
$sql="CREATE TABLE Feedback (
id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
Name VARCHAR(30) NOT NULL,
email VARCHAR(50) NOT NULL,
phone INT(10) NOT NULL,
rollno INT(10) NOT NULL,
faculty INT(2) NOT NULL,
student INT(2) NOT NULL,
institute_name VARCHAR(100) ,
institute_ip VARCHAR(100) NOT NULL,
discipline VARCHAR(150) NOT NULL,
labname VARCHAR(100) NOT NULL,
experiment_name VARCHAR(100),
issue VARCHAR(250),
comments VARCHAR(250),
question1 VARCHAR(500),
question2 VARCHAR(500),
question3 VARCHAR(500),
question4 VARCHAR(500)
)";
if ($conn->query($sql) === TRUE) {
    echo "Table feedback created successfully";
} else {
    echo "Error creating table: " . $conn->error;
}
$conn->close();
?>

