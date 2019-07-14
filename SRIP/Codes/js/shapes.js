numshapes=0;
var shapes = [];
var tr1 = new TMatrix("rt",-3,-3,45);
var tr2 = new TMatrix("tr", 2, 2);


var transformations = [tr1,tr2];
var transformations_framed = [];
var transformedshapes = {};


function getRandomColor() {
    color = "hsla(" + Math.random() * 360 + ", 100%, 75%, 0.75)";
    return color;
}

function drawShapes() {
    for (var id in shapes)
    {
        // console.log(shapes[id]);
        ctx.beginPath();
        var x = xcoor(shapes[id][0]['x']);
        var y = ycoor(shapes[id][0]['y']);
        ctx.moveTo(x,y);
        for (var point in shapes[id])
        {
            // console.log(shapes[id][point]);
            var x = xcoor(shapes[id][point]['x']);
            var y = ycoor(shapes[id][point]['y']);
            ctx.lineTo(x,y);
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
        shapes.push([]);
        for (var i = 1; i <= pointnum; i = i+1)
        {
            xc="cptx"+i;
            yc="cpty"+i;
            var x = parseInt($("#"+xc).val());
            var y = parseInt($("#"+yc).val());
            var pt = new Point(x,y);
            shapes[shapes.length-1].push(pt);
        }
        drawShapes();
    }
});

function drawTransformedShapes() {
    ctx.clearRect(0,0,width,height);
    drawGrid();
    for (var id in transformedshapes)
    {
        ctx.beginPath();
        var t1 = xcoor(transformedshapes[id][0]['x']);
        var t2 = ycoor(transformedshapes[id][0]['y']);
        ctx.moveTo(t1,t2);
        for (var point in transformedshapes[id])
        {
            var t3 = xcoor(transformedshapes[id][point]['x']);
            var t4 = ycoor(transformedshapes[id][point]['y']);
            ctx.lineTo(t3, t4);
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


$("#transform").click(function () {
    for(var ids in shapes)
    {
        transformedshapes[ids]=[];
        // console.log(shapes[id]);
        for (idp in shapes[ids])
        {
            var pt;
            for(idt in transformations)
            {
                var t;
                if(idt == 1)
                {
                    t = shapes[ids][idp].mat.multiply(transformations[idt].mat);
                }
                else
                {
                    t = pt.mat.multiply(transformations[idt].mat);
                }

                pt= new Point(t.mat[0][0],t.mat[0][1]);
            }
            transformedshapes[ids].push(pt);
        }
    }
    drawTransformedShapes();
});

$("#constructSampleSquare").click(function () {
    var x = parseInt($("#stopleftx").val());
    var y = parseInt($("#stoplefty").val());
    var len = parseInt($("#squarelength").val());
    numshapes = numshapes+1;
    shapes[numshapes]=[];
    var pt1 = new Point(x,y);
    var pt2 = new Point(x+len ,y);
    var pt3 = new Point(x+len, y-len);
    var pt4 = new Point(x, y-len);
    shapes[numshapes].push(pt1);
    shapes[numshapes].push(pt2);
    shapes[numshapes].push(pt3);
    shapes[numshapes].push(pt4);
    drawShapes();
});