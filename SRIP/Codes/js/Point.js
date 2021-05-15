function Point(x,y,d) {
    if(d==0)
    {
        throw new Error("d cannot be zero");
    }
    this.x=x/d;
    this.y=y/d;
    this.mat = new Matrix(1,3);
    this.mat.mat[0] = [this.x,this.y,1];
}