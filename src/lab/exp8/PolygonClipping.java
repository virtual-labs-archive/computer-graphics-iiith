package clipping;
import java.util.*;

import library.Point;
import library.Polygon;
public class PolygonClipping {

	private static int ObjectLimit_x=500;
	private static int ObjectLimit_y=10;
	private enum Edge {Left, Right, Bottom, Top}; 
	int[][] RenderPolygon=new int[ObjectLimit_x][ObjectLimit_y];
	private static int counter;
	Point[] pOut=new Point[ObjectLimit_x];
	
	int cnt;

	PolygonClipping()
	{
	        counter=0;
	        for(int i=0;i<ObjectLimit_x;i++)
	                for(int j=0;j<ObjectLimit_y;j++)
	                {
	                        RenderPolygon[i][j]=0;
	                }
	        for (int i=0;i<ObjectLimit_x;i++)
	    	{
	    		pOut[i]=new Point();
	    	}
	}
	
	private Point intersect(Point p1, Point p2, Edge b, int xmin,int ymin, int xmax, int ymax)
	{
	        int x1,y1,x2,y2,x = 0,y = 0;
	        float m = 0;
	        int[] coordinates=new int[3];
	        p1.getCoordinates(coordinates);
	        x1=coordinates[0]; y1=coordinates[1];
	        p2.getCoordinates(coordinates);
	        x2=coordinates[0]; y2=coordinates[1];
	        
	        if(x1!=x2)
	        {
	                m=(float) (((y1-y2)*1.0)/(x1-x2));
	        }
	        switch (b)
	        {
	                case Left:
	                        x=xmin;
	                        y=(int) (y2+((xmin - x2)*m));
	                        //                      cout<<"in intersection "<<x<<y;
	                        break;
	                case Right:
	                        x=xmax;
	                        y=(int) (y2+((xmax - x2)*m));
	                        break;
	                case Bottom:
	                        if(x1!=x2)
	                                x=(int) (x2+((ymin - y2)/m));
	                        else
	                                x=x2;
	                        y=ymin;
	                        break;
	                case Top:
	                        if(x1!=x2)
	                                x=(int) (x2+((ymax - y2)/m));
	                        else
	                                x=x2;
	                        y=ymax;
	                        break;

	        }
	
    return (new Point(x,y));
}

	//Inside determines if a point is inside or not wrt to a line. Return -1 if outside else returns 1
	private int inside (Point p,Edge b,int xmin, int ymin, int xmax, int ymax )
	{
	        int x,y;
	        int[] coordinates=new int[3];
	        p.getCoordinates(coordinates);
	        x=coordinates[0]; y=coordinates[1];
	        
	        switch(b)
	        {
	                case Left: if(x<xmin)   return -1;      break;
	                case Right:if(x>xmax)   return -1;      break;
	                case Bottom:if(y<ymin)  return -1;      break;
	                case Top:if(y>ymax)     return -1;      break;
	        }
	        return 1;
	}
	private int cross(Point p1, Point p2, Edge eb, int xmin,int ymin, int xmax, int ymax)
	{
	        //Edge from p1 tp p2;
	        int a=inside(p1,eb,xmin,ymin,xmax,ymax);
	        int b=inside(p2,eb,xmin,ymin,xmax,ymax);
	        if((a==-1)&&(b==-1))
	        {
	                return 4;
	        }
	        else if((a==-1)&&(b==1))
	        {
	                return 1;
	        }
	        else if((a==1)&&(b==1))
	        {
	                return 2;
	        }
	        else if((a==1)&&(b==-1))
	        {
	                return 3;
	        }
	        return 0;

	}
	


	public ReturnClipPoly ClipPoint(Point p, Edge b, int xmin, int ymin, int xmax, int ymax, Point s )
	{
	        Point iPt;
	        int retValue=0;
	        retValue= cross(s,p,b,xmin,ymin,xmax,ymax); //*s -> p is the direction of the edge
	        if(retValue==1)
	        {
	                //From out to in
	                iPt=intersect(s,p,b,xmin,ymin,xmax,ymax);
	                pOut[cnt]=iPt;
	                (cnt)++;
	                pOut[cnt]=p;
	                (cnt)++;
	        }
	        if(retValue==2)
	        {
	                //From in to in
	                pOut[cnt]=p;
	                (cnt)++;
	        }
	        if(retValue==3)
	        {
	                //From in to out
	                iPt=intersect(s,p,b,xmin,ymin,xmax,ymax);
	                pOut[cnt]=iPt;
	                (cnt)++;
	        }
	        if(retValue==4)
	        {
	                //From out to out
	        }
	        s=p;
	        ReturnClipPoly RCP = null;
	        RCP = new ReturnClipPoly();
	       
	        RCP.scopy=s;
	        RCP.retCopy=retValue;
	        return RCP;
	}
	void savePoint(int a, int b,int c ,int d, int e, int f, int g)
	{
	                RenderPolygon[counter][0]=a;RenderPolygon[counter][1]=b;RenderPolygon[counter][2]=c;RenderPolygon[counter][3]=d;
	                RenderPolygon[counter][4]=e;RenderPolygon[counter][5]=f;RenderPolygon[counter][6]=g;counter++;
	}

	void  saveData(int choice, Point p,int offset)
	{
	        int x1 = 0,y1 = 0,x2 = 0,y2 = 0;
	        int[] coordinates=new int[3];
	        if(choice==1)
	        {
	 	        pOut[cnt-1].getCoordinates(coordinates);
	 	        x1=coordinates[0]; y1=coordinates[1];
	 	        pOut[cnt-2].getCoordinates(coordinates);
	 	        x2=coordinates[0]; y2=coordinates[1];
	        	    
	        }
	        if(choice==2 || choice ==3)
	        {
	        		p.getCoordinates(coordinates);
		 	        x1=coordinates[0]; y1=coordinates[1];
		 	        pOut[cnt-1].getCoordinates(coordinates);
		 	        x2=coordinates[0]; y2=coordinates[1];
	                
	        }
	        savePoint(1+offset,2,x1,y1,x2,y2,0);
	}

	 private Edge lookUpEdge (int valueEdge) {  
	        return Edge.values()[valueEdge];  
	    }  
	
	public int[][] Clip(Polygon Cp ,Polygon P)
	{
	        int i = 0,n;
	        int xmin,xmax,ymin,ymax;
	        int x1,y1,x2,y2;
	        //Get the number of Vertices in Polygon to be clipped.
	        n= Cp.getVertexCount();
	        int[] coordinates=new int[3];
	        ReturnClipPoly RCP = null;
	        RCP = new ReturnClipPoly();
	        
	        
	        Point first, s,temp;
	        first=new Point(0,0);
	        s=new Point(0,0);
	        temp=new Point(0,0);
	        // First processed point and s has recent points
	        
	        int[] Boundary=new int [4];
			P.getRectangleBoundary(Boundary);
			xmin=Boundary[0];xmax=Boundary[1];ymin=Boundary[2];ymax=Boundary[3];
			
	        Point[] pIn=new Point[ObjectLimit_x];
	        for (i=0;i<ObjectLimit_x;i++)
	    	{
	    		pIn[i]=new Point();
	    	}
	        // Get the vertices and store it in pIn array 
	        Vector <Point> vertex=Cp.getVerticesInOrder();
	        ListIterator<Point> iter= vertex.listIterator();
	        int retValue;
	        i=0;
	        while (iter.hasNext()) 
	        {
	                pIn[i]=(Point) iter.next();  
	                i++;
	        }
	        int run=0;
	        for(run=0;run<=4;run++)
	        {
	                cnt=0;
	                //Print the points entered by user.
	                for (i=0;i<n;i++)
	                {

	        	        pIn[i].getCoordinates(coordinates);
	        	        x1=coordinates[0]; y1=coordinates[1];
	                        
	                        savePoint(0,x1,y1, 0, 0, 0, 0);
	                        System.out.println(i+" "+n+" "+x1+" "+y1);

	                }
	                if(run==4) break;
	                first=pIn[0];
	                s=pIn[0];
	                for(i=1;i<=n;i++)
	                {
	                        temp=s;
	                        s.getCoordinates(coordinates);
		        	        x1=coordinates[0]; y1=coordinates[1];
		                      
	                        
	                        if(i!=n)
	                        {
	                        	pIn[i].getCoordinates(coordinates);
	    	        	        x2=coordinates[0]; y2=coordinates[1];
	    	        	        
	    	        	        
	                                RCP=ClipPoint(pIn[i],lookUpEdge(run),xmin,ymin,xmax,ymax,s);
	                                retValue=RCP.retCopy;
	                               // pOut=RCP.pOutcopy;
	                                s=RCP.scopy;
	                                //cnt=RCP.cntcopy;
	                        }
	                        else
	                        {
	                        	 first.getCoordinates(coordinates);
	 		        	        x2=coordinates[0]; y2=coordinates[1];
	 		                      
	                                RCP=ClipPoint(first,lookUpEdge(run),xmin,ymin,xmax,ymax,s);
	                                retValue=RCP.retCopy;
	                               // pOut=RCP.pOutcopy;
	                                s=RCP.scopy;
	                                //cnt=RCP.cntcopy;
	                        }
	                        savePoint(1+run,1,x1,y1,x2,y2, 0);
	                        saveData(retValue,temp,run);
	                }
	                for (i=0;i<cnt;i++)
	                {
	                        //pOut[i].getPoint(x1,y1);
	                        //savePoint(3,1,x1,y1);
	                        pIn[i]=pOut[i];
	                }
	                n=cnt;
	        }
	        savePoint(-2, 0, 0, 0, 0, 0, 0);
	       System.out.println();
	        //Point pt;
	        System.out.println("Polycounter object");
	                for (i=0;i<counter;i++)
	                {
	                        System.out.println(i+" "+RenderPolygon[i][0]+" "+RenderPolygon[i][1]+" "+RenderPolygon[i][2]+" "+RenderPolygon[i][3]+" "+RenderPolygon[i][4]+" "+RenderPolygon[i][5]+" "+RenderPolygon[i][6]);
	                }
	        //drawclippoly(xmin,xmax,ymin,ymax,RenderPolygon,counter);
	        //free(pIn);
	                return RenderPolygon;
	        }
	   
}

	class ReturnClipPoly
    {
    	Point scopy;
    	int retCopy;
    }

