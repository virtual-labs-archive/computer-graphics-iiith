function Matrix(row, column)
{
    this.rows = row;
    this.columns = column;

    this.mat = [];
    for(var i=0; i<row; i=i+1)
    {
        this.mat[i]=[];
        for(var j=0; j<column; j=j+1)
        {
            this.mat[i][j]=0;
        }
    }

    this.multiply = function(mat2)
    {
        if(this.columns === mat2.rows)
        {
            var fmat = new Matrix(this.rows, this.columns);
            for(var i=0; i< this.rows; i=i+1)
            {
                for(var j=0; j<mat2.columns; j=j+1)
                {
                    fmat.mat[i][j] = 0;
                    for (var k=0; k<mat2.rows; k=k+1)
                    {
                        fmat.mat[i][j] = fmat.mat[i][j]+ this.mat[i][k] * mat2.mat[k][j]
                    }
                }
            }
            return fmat;
        }
        else
        {
            throw new Error("Cannot multiply matrices");
        }
    }
}

function TMatrix(transform, x, y, deg)
{
    if(typeof transform !== "string")
    {
        throw new Error("First parameter should be a string from these options. \n\"tr\" for translation.\n\"rt\" for rotation.\n\"sk\" for skew or shear.\n\"sc\" for scaling.");
    }

    this.mat= new Matrix(3,3);

    if(transform === "tr")
    {
        if(!(typeof x == "number" && typeof y == "number"))
        {
            throw new Error("The 2nd and 3rd parameters should be numbers");
        }
        var a = new Matrix(3,3);
        a.mat[0]=[1,0,0];
        a.mat[1]=[0,1,0];
        a.mat[2]=[x,y,1];
        this.mat = a;
    }

    else if(transform === "rt")
    {
        if(!(typeof x == "number" && typeof y == "number"))
        {
            throw new Error("The 2nd and 3rd parameters should be numbers");
        }
        if(typeof deg !== "number")
        {
            throw new Error("The 4th parameter should be a number, representing theta in degrees");
        }
        var a= new Matrix(3,3);
        a.mat[0]=[1,0,0];
        a.mat[1]=[0,1,0];
        a.mat[2]=[-x,-y,1];

        const theta = deg * Math.PI / 180;
        const costheta = Math.cos(theta);
        const sintheta = Math.sin(theta);
        // console.log(theta, costheta, sintheta);
        var b = new Matrix(3,3);
        b.mat[0]=[costheta, sintheta, 0];
        b.mat[1]=[-sintheta, costheta, 0];
        b.mat[2]=[0,0,1];

        var c= new Matrix(3,3);
        c.mat[0]=[1,0,0];
        c.mat[1]=[0,1,0];
        c.mat[2]=[x,y,1];

        var t1 = a.multiply(b);
        var t2 = t1.multiply(c);

        this.mat = t2;
    }
    else if(transform === "sk")
    {
        if(!(typeof x == "number" && typeof y == "string"))
        {
            throw new Error("The 2nd parameter should be a number for skew and 3rd parameter should be a string saying which axis to skew");
        }
        if(y === "x")
        {
            var a= new Matrix(3,3);
            a.mat[0]=[1,x,0];
            a.mat[1]=[0,1,0];
            a.mat[2]=[0,0,1];

            this.mat = a;
        }
        else if(y === "y")
        {
            var a= new Matrix(3,3);
            a.mat[0]=[1,0,0];
            a.mat[1]=[x,1,0];
            a.mat[2]=[0,0,1];

            this.mat = a;
        }
        else{
            throw new Error("UnExpected Value in 3rd parameter");
        }
    }
    else if(transform === "sc")
    {
        var a= new Matrix(3,3);
        a.mat[0]=[x,0,0];
        a.mat[1]=[0,y,0];
        a.mat[2]=[0,0,1];

        this.mat = a;
    }
    else
    {
        throw new Error("UnExpected value in 1st parameter");
    }
}
