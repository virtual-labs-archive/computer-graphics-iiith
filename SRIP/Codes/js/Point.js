function Point(x,y) {
    this.x=x;
    this.y=y;
    this.mat = new Matrix(1,3);
    this.mat.mat[0] = [x,y,1];
}