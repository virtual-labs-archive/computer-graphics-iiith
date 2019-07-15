var transformations = [];
var transformations_framed = [];
var transformedshapes = [];

$("#addtranslation").click(function () {
    var x = parseFloat($("#trx").val());
    var y = parseFloat($("#try").val());
    var f = parseInt($("#trf").val());

    var t = new TMatrix("tr", x, y);
    t.frames = f;
    if(transformations.length > 0)
    {
        var temp = transformations[transformations.length-1];
        var temp2 = temp.mat.multiply(t.mat);
        t.mat = temp2;
    }

    for(var i =1; i<=f; i=i+1)
    {
        var x1 = x*(i/f);
        var y1 = y*(i/f);
        if(transformations.length == 0)
        {
            transformations_framed.push(new TMatrix("tr",x1,y1));
        }
        else
        {
            var temp = new TMatrix("tr",x1,y1);
            var temp2 = transformations[transformations.length-1];
            var temp3 = temp2.mat.multiply(temp.mat);
            temp.mat = temp3;
            transformations_framed.push(temp);
        }

    }
    transformations.push(t);

});

$("#addrotation").click(function () {
    var x = parseFloat($("#rtx").val());
    var y = parseFloat($("#rty").val());
    var f = parseInt($("#rtf").val());
    var a = parseFloat($("#rta").val());

    var t = new TMatrix("rt", x, y, a);
    t.frames = f;
    if(transformations.length > 0)
    {
        var temp = transformations[transformations.length-1];
        var temp2 = temp.mat.multiply(t.mat);
        t.mat = temp2;
    }

    for(var i=1; i<=f; i = i+1)
    {
        var a1 = a * (i/f);
        var x1 = x*(i/f);
        var y1 = y*(i/f);
        if(transformations.length == 0)
        {
            transformations_framed.push(new TMatrix("rt",x1,y1,a1));
        }
        else
        {
            var temp = new TMatrix("rt",x1,y1,a1);
            var temp2 = transformations[transformations.length-1];
            var temp3 = temp2.mat.multiply(temp.mat);
            temp.mat = temp3;
            transformations_framed.push(temp);
        }

    }
    transformations.push(t);

});

$("#addscale").click(function () {
    var x = parseFloat($("#scx").val());
    var y = parseFloat($("#scy").val());
    var f = parseInt($("#scf").val());

    var t = new TMatrix("sc", x, y);
    t.frames = f;
    if(transformations.length > 0)
    {
        var temp = transformations[transformations.length-1];
        var temp2 = temp.mat.multiply(t.mat);
        t.mat = temp2;
    }

    for(var i =1; i<=f; i=i+1)
    {
        var x1 = (x-1)*(i/f);
        var x2 = 1 + x1;
        var y1 = (y-1)*(i/f);
        var y2 = 1 + y1;
        if(transformations.length == 0)
        {
            transformations_framed.push(new TMatrix("sc",x2,y2));
        }
        else
        {
            var temp = new TMatrix("sc",x2,y2);
            var temp2 = transformations[transformations.length-1];
            var temp3 = temp2.mat.multiply(temp.mat);
            temp.mat = temp3;
            transformations_framed.push(temp);
        }

    }
    transformations.push(t);

});

$("#addskew").click(function () {
    var val = parseFloat($("#skv").val());
    var axis = $('input[name=coordinate]:checked').val();
    var f = parseInt($("#skf").val());

    var t = new TMatrix("sk", val, axis);
    t.frames = f;
    if(transformations.length > 0)
    {
        var temp = transformations[transformations.length - 1];
        var temp2 = temp.mat.multiply((t.mat));
        t.mat = temp2;
    }

    for (var i = 1; i<=f; i=i+1)
    {
        var val1 = val * (i/f);

        if(transformations.length == 0)
        {
            transformations_framed.push(new TMatrix("sk",val1,axis));
        }
        else
        {
            var temp = new TMatrix("sk",val1,axis);
            var temp2 = transformations[transformations.length-1];
            var temp3 = temp2.mat.multiply(temp.mat);
            temp.mat = temp3;
            transformations_framed.push(temp);
        }
    }
    transformations.push(t);
});

$(document).on('input', '#slider', function() {
    transformedshapes = [];
    var v = $(this).val();
    for(var ids in shapes)
    {
        transformedshapes.push([]);
        for(var idp in shapes[ids])
        {
            var t = shapes[ids][idp].mat.multiply(transformations_framed[v].mat);
            var pt= new Point(t.mat[0][0],t.mat[0][1]);
            transformedshapes[transformedshapes.length - 1].push(pt);
        }

    }
    drawShapes(transformedshapes);
});