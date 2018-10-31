package clipping;
import library.Line;
import library.Point;
import library.Polygon;

public class LineClipping {
	private static int LEFT=1;
	private static int RIGHT=2;
	private static int BOTTOM=4;
	private static int TOP=8;
	private static int ObjectLimit_x=500;
	private static int ObjectLimit_y=10;
	private int[][] RenderObject;
	private static int counter;
	LineClipping()
	{
		RenderObject =new int [ObjectLimit_x][ObjectLimit_y];
		for(int i=0;i<ObjectLimit_x;i++)
			for(int j=0;j<ObjectLimit_y;j++)
			{
				RenderObject[i][j]=0;
			}
		counter=0;

	}
	private boolean REJECT(int a,int b) 
	{
		int r= a & b;
		if(r==0)
			return false;
		else
			return true;
	
	}
	private boolean ACCEPT(int a,int b) { 
		int r=a|b;
		if(r==0)
			return true;
		else 
			return false;
		
	}
	

	public int encode(int x,int y,int xmin,int xmax,int ymin,int ymax)
	{
		int code = 0;
		if (x < xmin) 
			code |= LEFT;
		if (x > xmax)   
			code |= RIGHT;
		if (y < ymin)        
			code |= BOTTOM;
		if (y > ymax)
			code |= TOP;
		return code;

	}
	public void saveLineData(int b,int c ,int d, int e, int f, int g, int h)
	{
		counter++;
		RenderObject[counter][0]=b;
		RenderObject[counter][1]=c;
		RenderObject[counter][2]=d;
		RenderObject[counter][3]=e;
		RenderObject[counter][4]=f;
		RenderObject[counter][5]=g;
		RenderObject[counter][6]=h;
		
	}
	public void saveLines(int x1,int y1,int x2,int y2 ,int code1, int code2)
	{
		saveLineData(1,x1,y1,x2,y2,code1,code2);
		saveLineData(2,x1,y1,x2,y2,code1,code2);
		saveLineData(3,x1,y1,x2,y2,code1,code2);
		saveLineData(4,x1,y1,x2,y2,code1,code2);
		
	}
	public ReturnObject Clip(Line L ,Polygon P)
	{
		int code1,code2,codeOut;
		int x1,y1,x2,y2;
		int x = 0,y=0;
		int xmin,xmax,ymin,ymax;
		counter=-1;
		int flag=0;
		int clippedonce=0;
		int leftInside=0;
		Boolean accept=false,done=false;

		// Get the end points of Line and save in x1, y1, x2, y2
		Point[] parr=new Point[2];
		L.getEndPoints(parr);
		int [] p1=new int[3];
		int [] p2=new int[3];
		parr[0].getCoordinates(p1);
		parr[1].getCoordinates(p2);
		x1=p1[0];y1=p1[1];x2=p2[0];y2=p2[1];
		// Get the end points of Rectangle and save in xmin, ymin, xmax, ymax
		int[] Boundary=new int [4];
		P.getRectangleBoundary(Boundary);
		xmin=Boundary[0];xmax=Boundary[1];ymin=Boundary[2];ymax=Boundary[3];
		
		code1=encode(x1,y1,xmin,xmax,ymin,ymax);
		code2=encode(x2,y2,xmin,xmax,ymin,ymax);
		saveLineData(0,x1,y1,x2,y2,code1,code2);
		System.out.println("Line coordinates"+x1+" "+y1+"\n"+x2+" "+y2);
		
		int pointOp=0;
		do
		{
			code1=encode(x1,y1,xmin,xmax,ymin,ymax);
			code2=encode(x2,y2,xmin,xmax,ymin,ymax);
			if(ACCEPT(code1,code2 ))
			{
				
				if(clippedonce==1)
				{
					saveLines(x2,y2,x1,y1,code2,code1);
				}
				if(clippedonce==0)
				{
					saveLines(x1,y1,x2,y2,code1,code2);
					saveLines(x2,y2,x1,y1,code2,code1);
				}
				accept=true;
				done=true;
				saveLineData(-2,x1,y1,x2,y2,code1,code2);
			}
			else if(REJECT(code1,code2))
			{
				System.out.println("cupltrit");
				saveLineData(-2,x1,y1,x2,y2,code1,code2);
				done=true;
			}
			else
			{
				codeOut = (code1 != 0 )? code1: code2;
				if(codeOut==code1 )
				{
					pointOp=1;
				}
				else
				{
					if(leftInside==0)
					{	
					saveLines(x1,y1,x2,y2,code1,code2);
					leftInside=1;
					}
					pointOp=2;
				}	
				clippedonce=1;
				//int i=0;
				flag=0;
				//Check against left edge
				if ((!((codeOut & LEFT )==0)) && ( flag==0))
				{
					y = y1 + (y2 - y1) * (xmin - x1)/(x2 - x1);
					x = xmin;
					flag=1;
				}
				saveLineData(1,(pointOp==1)?x1:x2,(pointOp==1)?y1:y2,(pointOp==1)?x2:x1,(pointOp==1)?y2:y1,(pointOp==1)?code1:code2,(pointOp==1)?code2:code1);
				if(flag==1)
				{
					if (codeOut == code1)
					{
						x1 = x;
						y1 = y;
						code1=encode(x1,y1,xmin,xmax,ymin,ymax);
					}
					else 
					{
						x2 = x;
						y2 = y;
						code2=encode(x2,y2,xmin,xmax,ymin,ymax);
					}
					saveLineData(0,x1,y1,x2,y2,code1,code2);
					continue;
				}

				//Check against right edge
				if ( (!((codeOut & RIGHT)==0)) && flag==0 )
				{
					y = y1 + (y2 - y1) * (xmax - x1)/(x2 - x1);
					x = xmax;
					flag=1;
				}
				saveLineData(2,(pointOp==1)?x1:x2,(pointOp==1)?y1:y2,(pointOp==1)?x2:x1,(pointOp==1)?y2:y1,(pointOp==1)?code1:code2,(pointOp==1)?code2:code1);
				if(flag==1)
				{
					if (codeOut == code1)
					{
						x1 = x;
						y1 = y;
						code1=encode(x1,y1,xmin,xmax,ymin,ymax);
					}
					else 
					{
						x2 = x;
						y2 = y;
						code2=encode(x2,y2,xmin,xmax,ymin,ymax);
					}
					saveLineData(0,x1,y1,x2,y2,code1,code2);
					continue;
				}
				//Check against bottom edge
				if ((!((codeOut & BOTTOM )==0)) &&flag==0)
				{
					x=(x2==x1)?x2:(x1 + (x2 - x1) * (ymin - y1)/(y2 - y1));
					y = ymin;
					flag=1;
				}
				saveLineData(3,(pointOp==1)?x1:x2,(pointOp==1)?y1:y2,(pointOp==1)?x2:x1,(pointOp==1)?y2:y1,(pointOp==1)?code1:code2,(pointOp==1)?code2:code1);
				if(flag==1)
				{
					if (codeOut == code1)
					{
						x1 = x;
						y1 = y;
						code1=encode(x1,y1,xmin,xmax,ymin,ymax);
					}
					else 
					{
						x2 = x;
						y2 = y;
						code2=encode(x2,y2,xmin,xmax,ymin,ymax);
					}
					saveLineData(0,x1,y1,x2,y2,code1,code2);
					continue;
				}
				//Check against top edge
				if ((!((codeOut & TOP) ==0))&& (flag==0)) 
				{
					x=(x2==x1)?x2:x1 + (x2 - x1) * (ymax - y1)/(y2 - y1);
					y = ymax;
					flag=1;
				}
				saveLineData(4,(pointOp==1)?x1:x2,(pointOp==1)?y1:y2,(pointOp==1)?x2:x1,(pointOp==1)?y2:y1,(pointOp==1)?code1:code2,(pointOp==1)?code2:code1);
				
				if(flag==1)
				{
					if (codeOut == code1)
					{
						x1 = x;
						y1 = y;
						code1=encode(x1,y1,xmin,xmax,ymin,ymax);
					}
					else 
					{
						x2 = x;
						y2 = y;
						code2=encode(x2,y2,xmin,xmax,ymin,ymax);
					}
					saveLineData(0,x1,y1,x2,y2,code1,code2);
					continue;
				}

			}

		}while(done!=true);
		System.out.println("here" + " "+counter);
	/*	cout<<x1<<endl<<y1<<endl<<x2<<endl<<y2<<endl;
	 */
		ReturnObject RO =new ReturnObject();
		RO.put(RenderObject);
		RO.put(counter+1);
		RO.put(accept);
		return RO;
		//Render R;
		//R.drawclipline(xmin,xmax,ymin,ymax,RenderObject,counter+1,accept);

	}

}
