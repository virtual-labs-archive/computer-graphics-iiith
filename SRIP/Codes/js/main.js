//Setup Of canvas

var canvas=document.querySelector('#world');

canvas.width=$("#canvas_container").width();
canvas.height=$("#canvas_container").height();
var ctx=canvas.getContext('2d');
var width=canvas.width;
var height=canvas.height;
// console.log(width);
var block=0.02*width;
function xcoor(x)
{
    var f = width / 2 + x * block;
    return f;
}

function ycoor(y)
{
    y=-y;
    var f = height / 2 + y * block;
    return f;
}

function drawGrid () {
    ctx.beginPath();
    for (var i = 1; i < 30; i = i + 1) {
        ctx.strokeStyle = "#D3D3D3";
        ctx.moveTo(0, ycoor(i));
        ctx.lineTo(width, ycoor(i));
        ctx.moveTo(0, ycoor(-i));
        ctx.lineTo(width, ycoor(-i));
        ctx.moveTo(xcoor(i), 0);
        ctx.lineTo(xcoor(i), height);
        ctx.moveTo(xcoor(-i), 0);
        ctx.lineTo(xcoor(-i), height);
    }

    ctx.stroke();
    ctx.beginPath();
    ctx.strokeStyle = "#000000";
    ctx.moveTo(width / 2, 0);
    ctx.lineTo(width / 2, height);
    ctx.moveTo(0, height / 2);
    ctx.lineTo(width, height / 2);
    ctx.stroke();
}


//for the aesthetic appeal(do later)
// $('#add_shape').click(function () {
//     if()
//     $(this).fadeOut();
//     $(this).fadeIn();
// });

function animate() {
    ctx.clearRect(0,0,width,height);
    requestAnimationFrame(animate);
    drawGrid();
}
drawGrid();

// var canvas;
// var ctx;
// var widthCanvas;
// var heightCanvas;
//
// // View parameters
// var xleftView = 0;
// var ytopView = 0;
// var widthViewOriginal = 1.0;           //actual width and height of zoomed and panned display
// var heightViewOriginal = 1.0;
// var widthView = widthViewOriginal;           //actual width and height of zoomed and panned display
// var heightView = heightViewOriginal;
//
// window.addEventListener("load",setup,false);
//
// function setup() {
//     canvas = document.getElementById("world");
//     ctx = canvas.getContext("2d");
//
//     widthCanvas = canvas.width;
//     heightCanvas = canvas.height;
//
//     canvas.addEventListener("dblclick", handleDblClick, false);  // dblclick to zoom in at point, shift dblclick to zoom out.
//     canvas.addEventListener("mousedown", handleMouseDown, false); // click and hold to pan
//     canvas.addEventListener("mousemove", handleMouseMove, false);
//     canvas.addEventListener("mouseup", handleMouseUp, false);
//     canvas.addEventListener("mousewheel", handleMouseWheel, false); // mousewheel duplicates dblclick function
//     canvas.addEventListener("DOMMouseScroll", handleMouseWheel, false); // for Firefox
//
//     draw();
// }
//
// function draw() {
//     ctx.setTransform(1,0,0,1,0,0);
//     ctx.scale(widthCanvas/widthView, heightCanvas/heightView);
//     ctx.translate(-xleftView,-ytopView);
//
//     ctx.fillStyle = "yellow";
//     ctx.fillRect(xleftView,ytopView, widthView,heightView);
//     ctx.fillStyle = "blue";
//     ctx.fillRect(0.1,0.5,0.1,0.1);
//     ctx.fillStyle = "red";
//     ctx.fillRect(0.3,0.2,0.4,0.2);
//     ctx.fillStyle="green";
//     ctx.beginPath();
//     ctx.arc(widthView/2+xleftView,heightView/2+ytopView,0.05,0,360,false);
//     ctx.fill();
// }
//
// function handleDblClick(event) {
//     var X = event.clientX - this.offsetLeft - this.clientLeft + this.scrollLeft; //Canvas coordinates
//     var Y = event.clientY - this.offsetTop - this.clientTop + this.scrollTop;
//     var x = X/widthCanvas * widthView + xleftView;  // View coordinates
//     var y = Y/heightCanvas * heightView + ytopView;
//
//     var scale = event.shiftKey == 1 ? 1.5 : 0.5; // shrink (1.5) if shift key pressed
//     widthView *= scale;
//     heightView *= scale;
//
//     if (widthView > widthViewOriginal || heightView > heightViewOriginal) {
//         widthView = widthViewOriginal;
//         heightView = heightViewOriginal;
//         x = widthView/2;
//         y = heightView/2;
//     }
//
//     xleftView = x - widthView/2;
//     ytopView = y - heightView/2;
//
//     draw();
// }
//
// var mouseDown = false;
//
// function handleMouseDown(event) {
//     mouseDown = true;
// }
//
// function handleMouseUp(event) {
//     mouseDown = false;
// }
//
// var lastX = 0;
// var lastY = 0;
// function handleMouseMove(event) {
//
//     var X = event.clientX - this.offsetLeft - this.clientLeft + this.scrollLeft;
//     var Y = event.clientY - this.offsetTop - this.clientTop + this.scrollTop;
//
//     if (mouseDown) {
//         var dx = (X - lastX) / widthCanvas * widthView;
//         var dy = (Y - lastY)/ heightCanvas * heightView;
//         xleftView -= dx;
//         ytopView -= dy;
//     }
//     lastX = X;
//     lastY = Y;
//
//     draw();
// }
//
// function handleMouseWheel(event) {
//     var x = widthView/2 + xleftView;  // View coordinates
//     var y = heightView/2 + ytopView;
//
//     var scale = (event.wheelDelta < 0 || event.detail > 0) ? 1.1 : 0.9;
//     widthView *= scale;
//     heightView *= scale;
//
//     if (widthView > widthViewOriginal || heightView > heightViewOriginal) {
//         widthView = widthViewOriginal;
//         heightView = heightViewOriginal;
//         x = widthView/2;
//         y = heightView/2;
//     }
//
//     // scale about center of view, rather than mouse position. This is different than dblclick behavior.
//     xleftView = x - widthView/2;
//     ytopView = y - heightView/2;
//
//     draw();
// }