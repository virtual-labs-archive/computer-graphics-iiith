function eval()
{
	var form = document.forms["quiz"];
	var win = window.open("","win","width=500,height=500,scrollbars");
	win.focus();
	win.document.open();
	win.document.write('<title>Solution</title>');
	win.document.write('<body bgcolor="#FFFFFF">');
        win.document.write('<center><h3>Score</h3></center>');


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
	var output = "Your score is " + score + "<br>";
	output += result;
	
	//alert(output);
	win.document.write(output+"<br>");
        win.document.write('<center><h3>Solution to Quiz</h3></center>');
        win.document.write("Ans 1)Option 2"+"<br>"+"Ans 2)Option 1"+"<br>"+"Ans 3)Option 3");

}
