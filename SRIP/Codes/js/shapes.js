numshapes=0;
var shapes = [];
var shapescolor=[];


function getRandomColor() {
    color = "hsla(" + Math.random() * 360 + ", 100%, 75%, 0.75)";
    return color;
}

function drawShapes(arr) {
    ctx.clearRect(0,0,width,height);
    drawGrid();
    for (var id in arr)
    {
        // console.log(shapes[id]);
        ctx.beginPath();
        var x = xcoor(arr[id][0]['x']);
        var y = ycoor(arr[id][0]['y']);
        ctx.moveTo(x,y);
        for (var point in arr[id])
        {
            // console.log(shapes[id][point]);
            var x = xcoor(arr[id][point]['x']);
            var y = ycoor(arr[id][point]['y']);
            ctx.lineTo(x,y);
        }
        if(shapescolor[id])
        {
            ctx.strokeStyle = shapescolor[id];
        }
        else
        {
            ctx.strokeStyle=getRandomColor();
            shapescolor[id]=ctx.strokeStyle;
        }

        ctx.closePath();
        ctx.stroke();
        ctx.fillStyle=ctx.strokeStyle;
        ctx.fill();
    }
    displayShapesAndTransformations();
}
function validatecustominput()
{
    return true;
}

$("#customShapeSubmit").click(function () {
    if(validatecustominput())
    {
        shapes.push([]);
        for (var i = 1; i <= pointnum; i = i+1)
        {
            xc="cptx"+i;
            yc="cpty"+i;
            var x = parseInt($("#"+xc).val());
            $("#"+xc).val('');
            var y = parseInt($("#"+yc).val());
            $("#"+yc).val('');
            var pt = new Point(x,y,1);
            shapes[shapes.length-1].push(pt);
        }
        drawShapes(shapes);
    }
    $("#newpoints").empty();
    $(".modal").modal('hide');
});

$("#constructSampleSquare").click(function () {
    let x = parseInt($("#stopleftx").val());
    let y = parseInt($("#stoplefty").val());
    let len = parseInt($("#squarelength").val());
    $("#stopleftx").val('');
    $("#stoplefty").val('');
    $("#squarelength").val('');
    shapes.push([]);
    let pt1 = new Point(x,y,1);
    let pt2 = new Point(x+len ,y,1);
    let pt3 = new Point(x+len, y-len,1);
    let pt4 = new Point(x, y-len,1);
    shapes[shapes.length-1].push(pt1);
    shapes[shapes.length-1].push(pt2);
    shapes[shapes.length-1].push(pt3);
    shapes[shapes.length-1].push(pt4);
    drawShapes(shapes);
    $(".modal").modal('hide');
});

$("#constructSampleRect").click(function () {
    let x = parseFloat($("#rtopleftx").val());
    let y = parseFloat($("#rtoplefty").val());
    let len = parseFloat($("#rectlength").val());
    let br = parseFloat($("#rectbreadth").val());
    $("#rtopleftx").val('');
    $("#rtoplefty").val('');
    $("#rectlength").val('');
    $("#rectbreadth").val('');

    shapes.push([]);
    let pt1 = new Point(x,y,1);
    let pt2 = new Point(x+len ,y,1);
    let pt3 = new Point(x+len, y-br,1);
    let pt4 = new Point(x, y-br,1);

    shapes[shapes.length-1].push(pt1);
    shapes[shapes.length-1].push(pt2);
    shapes[shapes.length-1].push(pt3);
    shapes[shapes.length-1].push(pt4);

    drawShapes(shapes);
    $(".modal").modal('hide');

});

$("#constructSampleTriangle").click(function () {

    shapes.push([]);

    let x1 = $("#tptx1").val();
    let x2 = $("#tptx2").val();
    let x3 = $("#tptx3").val();
    let y1 = $("#tpty1").val();
    let y2 = $("#tpty2").val();
    let y3 = $("#tpty3").val();
    $("#tptx1").val('');
    $("#tptx2").val('');
    $("#tptx3").val('');
    $("#tpty1").val('');
    $("#tpty2").val('');
    $("#tpty3").val('');

    let pt1 = new Point(x1,y1,1);
    let pt2 = new Point(x2,y2,1);
    let pt3 = new Point(x3,y3,1);

    shapes[shapes.length-1].push(pt1);
    shapes[shapes.length-1].push(pt2);
    shapes[shapes.length-1].push(pt3);

    drawShapes(shapes);
    $(".modal").modal('hide');

});