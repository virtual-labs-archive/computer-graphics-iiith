<?php

$myfile = "countlog.txt";
$count= 0 ;

/* counter */
//opens countlog.txt to read the user list
$datei = fopen($myfile,'r');

while(!feof($datei)){
  $line = fgets($datei);
  $count++;
}

fclose($datei);

// Function to get the client IP address
function get_client_ip() {
    $ipaddress = '';
    if ($_SERVER['HTTP_CLIENT_IP'])
        $ipaddress = $_SERVER['HTTP_CLIENT_IP'];
    else if($_SERVER['HTTP_X_FORWARDED_FOR'])
        $ipaddress = $_SERVER['HTTP_X_FORWARDED_FOR'];
    else if($_SERVER['HTTP_X_FORWARDED'])
        $ipaddress = $_SERVER['HTTP_X_FORWARDED'];
    else if($_SERVER['HTTP_FORWARDED_FOR'])
        $ipaddress = $_SERVER['HTTP_FORWARDED_FOR'];
    else if($_SERVER['HTTP_FORWARDED'])
        $ipaddress = $_SERVER['HTTP_FORWARDED'];
    else if($_SERVER['REMOTE_ADDR'])
        $ipaddress = $_SERVER['REMOTE_ADDR'];
    else
        $ipaddress = 'UNKNOWN';
    return $ipaddress;
}

$ip = get_client_ip();
$dat = date("Y-m-d h:i:sa");

$new1= $count . ". IP : " . $ip . " Date : " . $dat ."\n" ;

// opens countlog.txt to append the new user
$datei = fopen($myfile,'a') or die("Can't open file");
fwrite($datei, $new1);
fclose($datei);

?>
