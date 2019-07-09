numshapes=0;
var shapes = {};
numshapes=1;

function getRandomColor() {
    color = "hsla(" + Math.random() * 360 + ", 100%, 75%, 0.75)";
    return color;
}

function drawShapes() {
    for (var id in shapes)
    {
        // console.log(shapes[id]);
        ctx.beginPath();
        ctx.moveTo(shapes[id][0]['x'], shapes[id][0]['y']);
        for ( var point in shapes[id])
        {
            // console.log(shapes[id][point]);
            ctx.lineTo(shapes[id][point]['x'], shapes[id][point]['y']);
        }
        ctx.strokeStyle=getRandomColor();
        console.log(ctx.strokeStyle);
        ctx.closePath();
        ctx.stroke();
        ctx.fillStyle=ctx.strokeStyle;
        ctx.fill();
        console.log(ctx);
    }
}
function validatecustominput()
{
    return true;
}

$("#customShapeSubmit").click(function () {
    if(validatecustominput())
    {
        numshapes = numshapes+1;
        shapes[numshapes]=[];
        for (var i = 1; i <= pointnum; i = i+1)
        {
            xc="cptx"+i;
            yc="cpty"+i;
            shapes[numshapes].push({x: xcoor(parseInt($("#"+xc).val())), y: ycoor(parseInt($("#"+yc).val()))});
        }
        drawShapes();
    }
})

drawShapes();