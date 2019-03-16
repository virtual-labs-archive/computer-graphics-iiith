<?php
$total=0;
$q1 = $_POST['q1'];
$q2 = $_POST['q2'];
$q3 = $_POST['q3'];
echo "You answered the following questions correctly : ";
if ($q1==1)
{
$total=$total+1;
echo "1 ";
}
if ($q2==2)
{
$total=$total+1;
echo "2 ";
}
if($q3==0){
	$total=$total+1;
echo "3 ";
}

echo "\n\n\n\n";
echo "<html>
<head></head>";
echo "<body class=\"page_bg\">";
echo "<br>Total number of correct answers : ".$total."/3";
echo '	<h2>Correct Answers</h2>
<br>
<b>Q1.</b>
                <b>Which of the following conditions should hold for a line to completely be inside the clip window (code for end point 1 is c1 and for end point 2 is c2) </b>:<br/>
                c1 OR c2 = 0<br>
                <br>
<b>Q2.</b>                
                <b>Which of the following conditions should hold for a line to be trivially rejected (code for end point 1 is c1 and for end point 2 is c2) </b><br/>
                c1 AND c2 !=0<br>
                <br>
<b>Q3.</b>                
                Do you think the same algorithm will work if the clip window is concave? <br>
                NO<br>
                <br>

</ol>';
echo "</body></html>";
?>
