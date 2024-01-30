function eval()
{
	var form = document.forms["quiz"];

	/* Initialise answers */
	var a1 = "2";
	var a2 = "1";
	var a3 = "3";
	var a4 = "4";

	/* Extract answers */
	var q1, q2, q3, q4, length1;
	
	length1 = form["q1"].length;
	for(var i=0; i<length1; i++){
		if(form["q1"][i].checked){
			q1 = form["q1"][i].value;
		}
	}

	length1 = form["q2"].length;
	for(var j=0; j<length1; j++){
		if(form["q2"][j].checked){
			q2 = form["q2"][j].value;
		}
	}
	
	length1 = form["q3"].length;
	for(var k=0; k<length1; k++){
		if(form["q3"][k].checked){
			q3 = form["q3"][k].value;
		}
	}

	length1 = form["q4"].length;
	for(var l=0; l<length1; l++){
		if(form["q4"][l].checked){
			q4 = form["q4"][l].value;
		}
	}
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

	if(q4 == a4)
	{
		score++;
		result += "Q4";
	}

	/* Show result */
	if(score == 0){
		var output= "Your score is 0 \n";
	}
	else
	var output = "Your score is " + score + "\n";
	output += result;

	alert(output);
}
