function eval()
{
	var form = document.forms["quiz"];

	/* Initialise answers */
	var a1 = "2"
	var a2 = "1";
	var a3 = "3";
	// var a4 = "4";

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
	var result = "Correct answers: ";

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
