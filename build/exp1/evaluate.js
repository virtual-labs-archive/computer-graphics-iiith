function eval()
{
	var form = document.forms["quiz"];
        var win=window.open("","win","width=200,height=350,scrollbars");
	win.focus();
	win.document.open();
	win.document.write('<title>Solution</title>');
	win.document.write('<body bgcolor="#FFFFFF">');
        win.document.write('<center><h3>Solution to Quiz</h3></center>');
	win.document.write('<center><font face="Arial">');

	/* Initialise answers */
	var a1a = "(2,2)";
	var a1b = "(-2,3)";
	var a1c = "(-4,0)";
	var a1d = "(-3,-3)";
	var a1e = "(4,-3)";
	var a2  = "1";
	var a3  = "3";

	/* Extract answers */
	var q1a = form["q1_a"].value;
	var q1b = form["q1_b"].value;
	var q1c = form["q1_c"].value;
	var q1d = form["q1_d"].value;
	var q1e = form["q1_e"].value;
	var q2, q3, length;
	
	length = form["q2"].length;
	for(i=0; i<length; i++)
		if(form["q2"][i].checked)
			q2 = form["q2"][i].value;
	
	length = form["q3"].length;
	for(i=0; i<length; i++)
		if(form["q3"][i].checked)
			q3 = form["q3"][i].value;

	/* Evaluate answers */
	var score = 0;
	var result = "Correct answers: ";

	if(q1a == a1a)
	{
		score++;
		correct += "Q1a, ";
	}

	if(q1b == a1b)
	{
		score++;
		result += "Q1b, ";
	}

	if(q1c == a1c)
	{
		score++;
		result += "Q1c, ";
	}

	if(q1d == a1d)
	{
		score++;
		result += "Q1d, ";
	}

	if(q1e == a1e)
	{
		score++;
		result += "Q1e, ";
	}

	if(q2 == a2)
	{
		score++;
		result += "Q2, ";
	}

	if(q3 == a3)
	{
		score++;
		result += "Q3";
	}

	/* Show result */
	var output = "Your score is " + score + "\n";
	output += result;
	
	win.document.write(output+"\n");
	//alert(output);
	win.document.write('<p><pre>Ans 1)Point A : (2,2)
			                  Point B : (-2,3)
                                          Point C : (-4,0)
                                          Point D : (-3,-3)
                                          Point E : (4,-3)
                                    Ans 2)(3,0,-4)
                                    Ans 3)(2,4,7)</pre><p>');
}