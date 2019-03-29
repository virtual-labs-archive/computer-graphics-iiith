var number_of_attempts=0;

/* Initialise answers */
var a1 = "2"
var a2 = "1";
var a3 = "3";
// var a4 = "4";

function eval()
{
	number_of_attempts=number_of_attempts+1;
	var form = document.forms["quiz"];

	/* Extract answers */
	var q1, q2, q3, length;

	length = form["q1"].length;
	for(i=0; i<length; i++)
	if(form["q1"][i].checked)
	q1 = form["q1"][i].value;

	length = form["q2"].length;
	for(i=0; i<length; i++)
	if(form["q2"][i].checked)
	q2 = form["q2"][i].value;

	length = form["q3"].length;
	for(i=0; i<length; i++)
	if(form["q3"][i].checked)
	q3 = form["q3"][i].value;

	// length = form["q4"].length;
	// for(i=0; i<length; i++)
	// 	if(form["q4"][i].checked)
	// 		q2 = form["q4"][i].value;

	/* Evaluate answers */
	var score = 0;
	var result = "Your Correct answers: ";

	if(q1 == a1)
	{
		score++;
		result += "Q1, ";
	}

	if(q2 == a2)
	{
		score++;
		result += "Q2, ";
	}

	if(q3 == a3)
	{
		score++;
		result += "Q3, ";
	}

	// if(q4 == a4)
	// {
	// 	score++;
	// 	result += "Q4";
	// }

	/* Show result */
	var output = "Your score is " + score + "\n";
	output += result;

	alert(output);
}

function show_correct_ans() {
	var correct="Correct Answers:\n"
	correct=correct+"Q1. "+a1;
	correct=correct+"\nQ2. "+a2;
	correct=correct+"\nQ3. "+a3;
	if(number_of_attempts)
	{
		alert(correct);
	}
	else
	{
		alert("Please attempt atleast once before seeing the answers");
	}
}
