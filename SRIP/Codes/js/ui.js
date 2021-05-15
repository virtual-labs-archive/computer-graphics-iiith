pointnum=3;
$("#addNewPoint").click(function () {
    pointnum += 1;
    var newpointheading='<p> Point '+ pointnum + '</p>';
    var newpointform='<form class="form-inline d-flex justify-content-between"><div class="input-group" style="width: 40%;"> <div class="input-group-prepend"><div class="input-group-text">X:</div></div><input type="number" class="form-control" id="cptx' + pointnum + '" placeholder="X-coordinate"></div>&nbsp;&nbsp;<div class="input-group" style="width: 40%;"><div class="input-group-prepend"><div class="input-group-text">Y:</div></div><input type="number" class="form-control" id="cpty'+ pointnum +'" placeholder="Y-coordinate"></div><br></form><br>';

    var newpoint = newpointheading + newpointform;

    $("#newpoints").append(newpoint);
})

function displayShapesAndTransformations() {
    $("#display_shapes").empty();
    for(let id in shapes) {
        let card = document.createElement("div");
        card.className = "card";
        let color = shapescolor[id];
        color = color.replace("0.749019607843137", "0.4");

        let button = document.createElement("button");
        button.className = "card-header btn collapsed custom-font";
        button.setAttribute("id","shapeheader"+id);
        button.setAttribute("type", "button");
        button.setAttribute("data-toggle", "collapse");
        button.setAttribute("data-target", "#shape"+id);
        button.setAttribute("style","text-align: left;");
        button.setAttribute("style", "background-color:"+color+";");
        let temp = parseInt(id) + 1;
        button.innerHTML = "Shape " + temp;

        let collapsible = document.createElement("div");
        collapsible.className = "collapse";
        collapsible.setAttribute("id", "shape"+id);
        collapsible.setAttribute("data-parent","#display_shapes");
        // collapsible.setAttribute("style", "background-color:"+shapescolor[id]+";");

        let card_body = document.createElement("div");
        card_body.className = "card-body unclickable";
        for (let point in shapes[id])
        {
            let div = document.createElement("div");
            div.setAttribute("id", "s"+id + "p" + point);
            let newpointform='<form class="form-inline d-flex justify-content-between"><div class="input-group" style="width: 40%;"> <div class="input-group-prepend"><div class="input-group-text">X:</div></div><input type="number" class="form-control" id="xs' + id + 'p' + point + '" placeholder="X-coordinate"></div>&nbsp;&nbsp;<div class="input-group" style="width: 40%;"><div class="input-group-prepend"><div class="input-group-text">Y:</div></div><input type="number" class="form-control" id="ys' + id + 'p' + point + '" placeholder="Y-coordinate"></div><br></form><br>';
            $(div).append(newpointform);
            $(card_body).append(div);
        }
        collapsible.append(card_body);
        card.append(button);
        card.append(collapsible);
        document.getElementById("display_shapes").append(card);
        for (let point in shapes[id])
        {
            $("#xs" + id + "p" + point).val(shapes[id][point].x);
            $("#ys" + id + "p" + point).val(shapes[id][point].y);
        }
    }
    $('.collapse').on('show.bs.collapse', function () {
        $(this).siblings('.card-header').addClass('active');
    });

    $('.collapse').on('hide.bs.collapse', function () {
        $(this).siblings('.card-header').removeClass('active');
    });
}
