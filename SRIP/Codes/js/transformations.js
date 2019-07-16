var original_transformations = [];
var transformations = [];
var transformations_framed = [];
var transformedshapes = [];

$("#addtranslation").click(function () {
    let temp;
    var x = parseFloat($("#trx").val());
    var y = parseFloat($("#try").val());
    var f = parseInt($("#trf").val());

    var t = new TMatrix("tr", x, y);
    t.frames = f;
    original_transformations.push(new TMatrix("tr",x,y));
    if(transformations.length > 0)
    {
        temp = transformations[transformations.length - 1];
        var temp2 = temp.mat.multiply(t.mat);
        t.mat = temp2;
    }

    for(let i =1; i<=f; i=i+1)
    {
        let x1 = x * (i / f);
        let y1 = y * (i / f);
        if(transformations.length == 0)
        {
transformations_framed.push(new TMatrix("tr",x1,y1));
}
else
{
    let temp = new TMatrix("tr",x1,y1);
    let temp2 = transformations[transformations.length-1];
    let temp3 = temp2.mat.multiply(temp.mat);
    temp.mat = temp3;
    transformations_framed.push(temp);
}

}
transformations.push(t);
$(".modal").modal('hide');

});

$("#addrotation").click(function () {
    let x = parseFloat($("#rtx").val());
    let y = parseFloat($("#rty").val());
    let f = parseInt($("#rtf").val());
    let a = parseFloat($("#rta").val());

    var t = new TMatrix("rt", x, y, a);
    t.frames = f;
    original_transformations.push(new TMatrix("rt", x, y, a));
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
    $(".modal").modal('hide');
});

$("#addscale").click(function () {
    var x = parseFloat($("#scx").val());
    var y = parseFloat($("#scy").val());
    var f = parseInt($("#scf").val());

    var t = new TMatrix("sc", x, y);
    t.frames = f;
    original_transformations.push(new TMatrix("sc", x, y));
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
    $(".modal").modal('hide');
});

$("#addskew").click(function () {
    var val = parseFloat($("#skv").val());
    var axis = $('input[name=coordinate]:checked').val();
    var f = parseInt($("#skf").val());

    var t = new TMatrix("sk", val, axis);
    t.frames = f;
    original_transformations.push(new TMatrix("sk", val, axis));
    if(transformations.length > 0)
    {
        var temp = transformations[transformations.length - 1];
        var temp2 = temp.mat.multiply((t.mat));
        t.mat = temp2;
    }

    for (var i = 1; i<=f; i=i+1)
    {
        var val1 = val * (i/f);

        if(transformations.length === 0)
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
    $(".modal").modal('hide');
});

$("#addcustom").click(function() {
    let ct1 = parseFloat($("#ct1").val());
    let ct2 = parseFloat($("#ct2").val());
    let ct3 = parseFloat($("#ct3").val());
    let ct4 = parseFloat($("#ct4").val());
    let ct5 = parseFloat($("#ct5").val());
    let ct6 = parseFloat($("#ct6").val());
    let ct7 = parseFloat($("#ct7").val());
    let ct8 = parseFloat($("#ct8").val());
    let ct9 = parseFloat($("#ct9").val());
    let f =  $("#customf").val();


    let t = new TMatrix("custom");
    t.mat.mat[0] = [ct1,ct2,ct3];
    t.mat.mat[1] = [ct4,ct5,ct6];
    t.mat.mat[2] = [ct7,ct8,ct9];
    t.frames =f;
    original_transformations.push(t);
    if(transformations.length > 0)
    {
        let temp = transformations[transformations.length - 1];
        let temp2 = temp.mat.multiply((t.mat));
        t.mat = temp2;
    }

    for (let i = 1; i<=f; i=i+1)
    {
        let ct11 = (ct1-1) * (i/f);
        ct11 = ct11 + 1;
        let ct21 = ct2 * (i/f);
        let ct31 = ct3 * (i/f);
        let ct41 = ct4 * (i/f);
        let ct51 = (ct5-1) * (i/f);
        ct51 = ct51 + 1;
        let ct61 = ct6 * (i/f);
        let ct71 = ct7 * (i/f);
        let ct81 = ct8 * (i/f);
        let ct91 = (ct9-1) * (i/f);
        ct91 = ct91 + 1;

        if(transformations.length === 0)
        {
            let t1 = new TMatrix("custom");
            t1.mat.mat[0] = [ct11,ct21,ct31];
            t1.mat.mat[1] = [ct41,ct51,ct61];
            t1.mat.mat[2] = [ct71,ct81,ct91];
            transformations_framed.push(t1);
        }
        else
        {
            let t1 = new TMatrix("custom");
            t1.mat.mat[0] = [ct11,ct21,ct31];
            t1.mat.mat[1] = [ct41,ct51,ct61];
            t1.mat.mat[2] = [ct71,ct81,ct91];
            let temp2 = transformations[transformations.length-1];
            let temp3 = temp2.mat.multiply(t1.mat);
            t1.mat = temp3;
            transformations_framed.push(t1);
        }
    }
    transformations.push(t);
    $(".modal").modal('hide');
});

$(document).on('input', '#slider', function() {
    transformedshapes = [];
    let v = $(this).val();
    for(let ids in shapes)
    {
        transformedshapes.push([]);
        for(let idp in shapes[ids])
        {
            let t = shapes[ids][idp].mat.multiply(transformations_framed[v].mat);
            let pt= new Point(t.mat[0][0],t.mat[0][1],1);
            transformedshapes[transformedshapes.length - 1].push(pt);
        }

    }
    drawShapes(transformedshapes);
});