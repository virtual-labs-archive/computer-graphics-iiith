
var Answers= new Array()
Answers[1]='d'  //Concave with convex clip window poses a problem
Answers[2]='a'  //Which ever order we perform the algorithm(Clock-wise or Anti-clockwise), the result is same
Answers[3]='a'  //The x coordinate is min(x1,x2) depending on whether the line goes out or comes in.
                //The y coordinate can be calulated with the point-slope formula,given that the slope of line is m.

var questions=3;
var actualchoices=new Array();
var correctchoices=new Array();
var incorrect=new Array()
function Evaluate()
{

var nooptionselected=0,crcnt=0,incrcnt=0
for (var ques = 1;ques <= questions; ques ++)
{
	var currq=eval("document.quiz.question"+ques)
	var checkflag=0
	for (c=0;c<currq.length;c++)
  {
		  if (currq[c].checked==true)         //if the radio button is checked store that choice
		    {
          actualchoices[ques]=currq[c].value
			    checkflag=1
		    }
	}

	if(checkflag==0)
		{
			nooptionselected++;      //If the user submits the quiz without attempting even a single question
		}

else {            //Some option has been selected
	if (actualchoices[ques] != Answers[ques])
  {                                         //incorrect answer stored
		incorrect.push(ques)
    incrcnt++
    // alert("Answer for question "+ques+" is incorrect")
  }

  else
  {
		correctchoices.push(ques)
    crcnt++
    //alert("Answer for question"+ques+"is correct")
  }
}

}

if(nooptionselected >=1)
alert("Please enter answers for all questions before submitting !!!")
else
{
  if(incrcnt == 0)
  incorrect[0]='NIL'
  if(crcnt == 0)
  correctchoices[0]='NIL'
  alert("The correct Answers are \n"+correctchoices.join(',')+"\n\n The incorrect answers are \n" + incorrect.join(',') +"\n\n ANSWER KEY:\n 1)d    2)a   3)a \n")
}
actualchoices=[]
incorrect=[]
correctchoices=[]
}


function Reset()
{
  var res=document.quiz.B2
  // alert("resetting the quiz")
  document.quiz.reset()
}
