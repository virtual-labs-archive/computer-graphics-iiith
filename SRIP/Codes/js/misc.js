
$("#world").mousemove( function (evt) {
    var mousePos = getMousePos(canvas, evt);
    $("#mousepos").html((mousePos.x - width/2)/block + ',' + -(mousePos.y - height/2)/block);
});

//Get Mouse Position
function getMousePos(canvas, evt) {
    var rect = canvas.getBoundingClientRect();
    return {
        x: evt.clientX - rect.left,
        y: evt.clientY - rect.top
    };
}