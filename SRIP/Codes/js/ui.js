pointnum=3;
$("#addNewPoint").click(function () {
    pointnum = pointnum + 1;
    var newpointheading='<p> Point '+ pointnum + '</p>';
    var newpointform='<form class="form-inline d-flex justify-content-between"><div class="input-group" style="width: 40%;"> <div class="input-group-prepend"><div class="input-group-text">X:</div></div><input type="number" class="form-control" id="cptx' + pointnum + '" placeholder="X-coordinate"></div>&nbsp;&nbsp;<div class="input-group" style="width: 40%;"><div class="input-group-prepend"><div class="input-group-text">Y:</div></div><input type="number" class="form-control" id="cpty'+ pointnum +'" placeholder="Y-coordinate"></div><br></form><br>';

    var newpoint = newpointheading + newpointform;

    $("#newpoints").append(newpoint);
})
