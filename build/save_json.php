<?php
        
    $filetxt = 'data.txt';

        // check if all form data are submited, else output error message
       
        if(empty($_POST['element_1_1']) || empty($_POST['element_2']) || empty($_POST['element_3_1']) || empty($_POST['element_3_2']) || empty($_POST['element_3_3'])) {
            echo 'All fields are required';
        }
        else {

	$servername = "127.0.0.1:3306";
	$username = "root";
	$password = "";
	$dbname = "feedback";

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
$name=$_POST['element_1_1'];
$email=$_POST['element_2'];
$phone=$_POST['element_3_1']+$_POST['element_3_2']+$_POST['element_3_1'];
$rollno=$_POST['element_4'];
$faculty=1;
$student=0;
$institute_name=$_POST['element_6'];
$institute_ip=$_POST['element_7'];
$discipline=$_POST['element_8'];
$labname=$_POST['element_9'];
$experiment_name='ss';
$issue=$_POST['element_10'];
$comments=$_POST['element_11'];
$question1=$_POST['element_12'];
$question2=$_POST['element_13'];
$question3=$_POST['element_14'];
$question4=$_POST['element_15']; 
     
$sql = "INSERT INTO Feedback(Name,email,phone,rollno,faculty,student,institute_name,institute_ip,discipline,labname,experiment_name,issue,comments,question1,question2,question3,question4) 
VALUES ($name, $email, $phone,$rollno,$faculty,$student,$institute_name,$institute_ip,$discipline,$labname,$experiment_name,$issue,$comments,$question1,$question2,$question3,$question4)";

if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}
}
?>
