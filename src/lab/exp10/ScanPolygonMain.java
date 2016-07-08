package rasterization;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
//import java.io.DataInputStream;
//import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.ListIterator;
//import java.util.Timer;
//import java.util.TimerTask;
import java.util.Vector;

import library.DrawToolkit;
import library.FrameBuffer;
//import library.Line;
import library.Point;
import library.Polygon;

public class ScanPolygonMain extends Applet implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_GAP_1 = 60;
	private static final int WINDOW_GAP_2 = 20;
	
	private static final int OFFSET = 10;

	public static final int WINDOW_HEIGHT = 1000;
	public static final int WINDOW_WIDTH = 1200;
	
	private static final int PROD_RASTER_FRAME = 525;

	private static final int FRAME_BUFFER = -2;
	private static final int VERTICES = -1;
	private final int CHECK = 0;
	private final int FOUND = 1;
	private final int DRAW_OVER = 2;
	private final int END = 3;

	private FrameBuffer fb1, fb2;

	private Vector<Boolean> checked;
	private Vector<Vector<Edge> > activeList;

	private int state;
	private int counter;

	private int scan;

	private int frameWidth, frameHeight, rasterSize;

	Point p;
	Polygon points;

	private boolean foundStateDone;

	private boolean active;

	ScanConversion s;
	private DrawToolkit draw;

	private static final String NextButtonName = "Next Iteration";
	private static final String PreviousButtonName = "Previous Iteration";
	private static final String DefaultParamsName = "Start Experiment with Default Values";
	private static final String FrameBufferName = "Enter";
	private static final String StartEndName = "Start Experiment";
	private static final String BackName = "Back";

	private Button next_button, previous_button, default_params, frame_buffer, start_end, back_button;
	
	private static final int next_button_width = 100;
	private static final int previous_button_width = 150;
	private static final int default_button_width = 250;
	private static final int frame_start_button_width = 250;
	private static final int back_button_width = 100;
	private static final int button_height = 20;
	
	private static TextField frame_buffer_height_text, frame_buffer_width_text;
	private static TextField vertices_text;
	private static final int TEXT_COLUMNS = 250;

	private int TRANSLATE_X = 50;
	private int TRANSLATE_Y = 50;

	public void init()
	{
		String str = new String();
		String[] str1 = new String[10];
		s = new ScanConversion();
		int x, y;

		try
		{
			URL url = new URL(this.getCodeBase(), this.getParameter("file_name"));
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			if((str = br.readLine()) == null)
			{
				//====System.out.println("No entries in the file.");
				System.exit(0);
			}
			str1 = str.split("[ \t\n\f\r]");
			frameWidth = Integer.parseInt(str1[0]);
			frameHeight = Integer.parseInt(str1[1]);
			rasterSize = Integer.parseInt(str1[2]);
			fb1 = new FrameBuffer(frameWidth + 1, frameHeight + 1, rasterSize);
			fb2 = new FrameBuffer(frameWidth + 1, frameHeight + 1, rasterSize);

			points = new Polygon();
			while((str = br.readLine()) != null)
			{
				str1 = str.split("[ \t\n\f\r]");
				x = Integer.parseInt(str1[0]);
				y = Integer.parseInt(str1[1]);

				if((x < 0 || x >= frameWidth) || (y < 0 || y >= frameHeight))
				{
					//================System.out.println("Values of starting
					//===============and ending points of the line cannot be greater 
					//===============than the size of the Frame Buffer or negative");
					System.exit(0);
				}
				p = new Point(x, y);
				points.addVertex(p);
			}

			/*
			fb2 = s.ScanConvert(points, fb2);
			fb2.draw();

			checked = s.getChecked();
			activeList = s.getActiveList();
			*/
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			System.exit(0);
		}

		fb1 = new FrameBuffer(frameWidth + 1, frameHeight + 1, rasterSize);
		fb2 = new FrameBuffer(frameWidth + 1, frameHeight + 1, rasterSize);

		state = FRAME_BUFFER;
		counter = 0;
		scan = 0;

		active = false;

		foundStateDone = false;

		draw = new DrawToolkit();
		setBackground(Color.BLACK);
		resize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		setBackground(Color.BLACK);

		setLayout(null);
		
		next_button = new Button(NextButtonName);
		previous_button = new Button(PreviousButtonName);
		default_params = new Button(DefaultParamsName);
		frame_buffer = new Button(FrameBufferName);
		start_end = new Button(StartEndName);
		back_button = new Button(BackName);

		frame_buffer_width_text = new TextField(TEXT_COLUMNS);
		frame_buffer_height_text = new TextField(TEXT_COLUMNS);
		vertices_text = new TextField(TEXT_COLUMNS);
				
		add(next_button);
		add(previous_button);
		add(default_params);
		add(frame_buffer);
		add(start_end);
		add(back_button);
		
		add(frame_buffer_height_text);
		add(frame_buffer_width_text);
		add(vertices_text);
				
		next_button.setBackground(Color.WHITE);
		previous_button.setBackground(Color.WHITE);
		default_params.setBackground(Color.WHITE);
		frame_buffer.setBackground(Color.WHITE);
		start_end.setBackground(Color.WHITE);
		back_button.setBackground(Color.WHITE);
		frame_buffer_width_text.setBackground(Color.WHITE);
		frame_buffer_height_text.setBackground(Color.WHITE);
		vertices_text.setBackground(Color.WHITE);
		
		next_button.addActionListener(this);
		previous_button.addActionListener(this);
		default_params.addActionListener(this);
		frame_buffer.addActionListener(this);
		start_end.addActionListener(this);
		back_button.addActionListener(this);

		frame_buffer_width_text.addActionListener(this);
		frame_buffer_height_text.addActionListener(this);
		vertices_text.addActionListener(this);
		
		next_button.setVisible(false);
		previous_button.setVisible(false);
		default_params.setVisible(true);
		frame_buffer.setVisible(true);
		start_end.setVisible(false);
		back_button.setVisible(false);

		frame_buffer_width_text.setVisible(true);
		frame_buffer_height_text.setVisible(true);
		vertices_text.setVisible(false);
		
		next_button.setBounds(TRANSLATE_X + frameWidth * rasterSize + 3*WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + 4*WINDOW_GAP_2, next_button_width, button_height);
		previous_button.setBounds(TRANSLATE_X + frameWidth * rasterSize + 3*WINDOW_GAP_1
				+ 2 * WINDOW_GAP_2 + next_button_width,
				TRANSLATE_Y + WINDOW_GAP_1 + 4*WINDOW_GAP_2, previous_button_width, button_height);
		default_params.setBounds(TRANSLATE_X + frameWidth * rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + 6*WINDOW_GAP_2, default_button_width, button_height);
		frame_buffer.setBounds(TRANSLATE_X + frameWidth * rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + 4*WINDOW_GAP_2 + OFFSET, frame_start_button_width, button_height);
		start_end.setBounds(TRANSLATE_X + frameWidth * rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + 4*WINDOW_GAP_2 + OFFSET, frame_start_button_width, button_height);
		back_button.setBounds(TRANSLATE_X + (frameWidth + 1) * rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + 8*WINDOW_GAP_2, back_button_width, button_height);

		frame_buffer_width_text.setBounds(TRANSLATE_X + frameWidth * rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + WINDOW_GAP_2, next_button_width, button_height);
		frame_buffer_height_text.setBounds(TRANSLATE_X + frameWidth * rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + 3*WINDOW_GAP_2,	next_button_width, button_height);
		vertices_text.setBounds(TRANSLATE_X + frameWidth * rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + WINDOW_GAP_2, TEXT_COLUMNS, button_height);

		frame_buffer_width_text.setText(frameWidth+"");
		frame_buffer_height_text.setText(frameHeight+"");

		String vertices_temp = "";
		ListIterator<Point> ptr = points.getVerticesInOrder().listIterator();
		Point ptemp;
		ptemp = new Point();
		while(ptr.hasNext())
		{
			ptemp = new Point(ptr.next());
			vertices_temp += ptemp.getX() + " " + ptemp.getY() + ",";
		}
		vertices_text.setText(vertices_temp);
	}

	void toPrevious()
	{
		active = true;
		counter = 0;

		if(state == CHECK)
		{
			scan--;
			if(scan < 0)
			{
				scan++;
			}
			if(checked.get(scan))
			{
				state = DRAW_OVER;
			}
			else
			{
				state = CHECK;
			}
		}
		else if(state == DRAW_OVER)
		{
			state = CHECK;

			//System.out.println("``````````````` " + scan);
			Vector<Edge> temp = activeList.get(scan);
			ListIterator<Edge> i = temp.listIterator();
			int x;

			while(i.hasNext())
			{
				Edge temp_curr = i.next();
				Edge temp_next = i.next();
				for(x = (int) (Math.round(temp_curr.xIntersect)) + 1; 
								x < (int) (Math.round(temp_next.xIntersect)); x++)
				{
					fb1.unsetPixel(x, scan);
					//System.out.println("Pixels unset ---- " + x + "\t" + scan);
				}
			}
			//System.out.println("-------------------------------------------");
		}
		if(state == END)
		{
			scan--;
			state = DRAW_OVER;
		}

		repaint();
	}

	void toNext()
	{
		active = true;
		counter = 0;
		if(state == CHECK)
		{
			if(checked.get(scan))
				state = DRAW_OVER;
			else
			{
				scan++;
				state = CHECK;
				if(scan >= frameHeight)
				{
					state = END;
				}
			}
		}
		else if(state == DRAW_OVER)
		{
			state = CHECK;
			scan++;
			if(scan >= frameHeight)
				state = END;
		}
		repaint();
	}

	public void paint(Graphics g)
	{
		g.translate(TRANSLATE_X, TRANSLATE_Y);

		counter ++;

		if(state == FRAME_BUFFER)
		{
			drawDecideFrameSize(g);
		}
		else if(state == VERTICES)
		{
			drawDecideStartEnd(g);
		}
		else
		{
			if(!foundStateDone)
			{
				for(int k = 0; k < frameHeight; k++)
				{
					Vector<Edge> temp = activeList.get(k);
					ListIterator<Edge> i = temp.listIterator();

					while(i.hasNext())
					{
						Edge temp_edge = i.next();
						fb1.setPixel((int)((int)Math.round(temp_edge.xIntersect)), k);
						//System.out.println("Pixels set ---- " + (int)Math.round(temp_edge.xIntersect) + "\t" + k);
					}
				}

				foundStateDone = true;
			}
			if(active)
			{
				if(state == CHECK)
					drawCheckState(g);
				else if(state == FOUND)
					drawFoundState(g);
				else if(state == DRAW_OVER)
					drawDrawOverState(g);
				else if(state == END)
					drawFrameBufferPol(g);
				active = false;
			}
			else
				drawFrameBufferPol(g);
		}		
	}

	private void drawDecideFrameSize(Graphics g)
	{
		draw.makeLine(g, 0, 0, frameWidth * rasterSize, 0, new Color(0.3f, 0.3f, 0.3f), false);
		draw.makeLine(g, frameWidth * rasterSize, frameHeight * rasterSize,
				frameWidth * rasterSize, 0, new Color(0.3f, 0.3f, 0.3f), false);

		int i, j;

		for(i = 0; i < frameWidth; i++)
		{
			for (j = 0; j < frameHeight; j++)
			{
				draw.makeLine(g, 0, (frameHeight - j) * rasterSize, frameWidth * rasterSize, (frameHeight - j) * rasterSize,
						new Color(0.3f, 0.3f, 0.3f), false);
				draw.writeCoordinates(g, 0 - (int)(rasterSize/3), (frameHeight - j) * rasterSize - (int)(rasterSize/2),
						j, new Color(1.0f, 1.0f, 1.0f));
				draw.writeCoordinates(g, frameWidth * rasterSize + (int)(rasterSize/3),
						(frameHeight - j) * rasterSize - (int)(rasterSize/2), j, new Color(1.0f, 1.0f, 1.0f));
			}
			draw.makeLine(g, i * rasterSize, frameHeight * rasterSize,
					i * rasterSize, 0, new Color(0.3f, 0.3f, 0.3f), false);
			draw.writeCoordinates(g, i * rasterSize + (int)(rasterSize/2),
					frameHeight * rasterSize + (int)(rasterSize/3),	i, new Color(1.0f, 1.0f, 1.0f));
			draw.writeCoordinates(g, i * rasterSize + (int)(rasterSize/2), 0 - (int)(rasterSize/3),
					i, new Color(1.0f, 1.0f, 1.0f));
		}
		g.setColor(Color.WHITE);
		g.drawString("Enter Frame Width (max - 25)",
				TRANSLATE_X + frameWidth * rasterSize, TRANSLATE_Y + WINDOW_GAP_1 - 2*WINDOW_GAP_2);
		g.drawString("Enter Frame Height (max - 25)",
				TRANSLATE_X + frameWidth * rasterSize, TRANSLATE_Y + WINDOW_GAP_1);
	}
	
	private void drawDecideStartEnd(Graphics g)
	{
		draw.makeLine(g, 0, 0, frameWidth * rasterSize, 0, new Color(0.3f, 0.3f, 0.3f), false);
		draw.makeLine(g, frameWidth * rasterSize, frameHeight * rasterSize,
				frameWidth * rasterSize, 0, new Color(0.3f, 0.3f, 0.3f), false);
		
		int i, j;

		for(i = 0; i < frameWidth; i++)
		{
			for (j = 0; j < frameHeight; j++)
			{
				draw.makeLine(g, 0, (frameHeight - j) * rasterSize,	frameWidth * rasterSize,
						(frameHeight - j) * rasterSize, new Color(0.3f, 0.3f, 0.3f), false);
				draw.writeCoordinates(g, 0 - (int)(rasterSize/3),
						(frameHeight - j) * rasterSize - (int)(rasterSize/2), j, new Color(1.0f, 1.0f, 1.0f));
				draw.writeCoordinates(g, frameWidth * rasterSize + (int)(rasterSize/3),
						(frameHeight - j) * rasterSize - (int)(rasterSize/2), j, new Color(1.0f, 1.0f, 1.0f));
			}
			draw.makeLine(g, i * rasterSize, frameHeight * rasterSize,
					i * rasterSize, 0, new Color(0.3f, 0.3f, 0.3f), false);
			draw.writeCoordinates(g, i * rasterSize + (int)(rasterSize/2), frameHeight * rasterSize + (int)(rasterSize/3),
					i, new Color(1.0f, 1.0f, 1.0f));
			draw.writeCoordinates(g, i * rasterSize + (int)(rasterSize/2), 0 - (int)(rasterSize/3),
					i, new Color(1.0f, 1.0f, 1.0f));
		}
		
		g.setColor(new Color(1.0f, 1.0f, 1.0f));
		ListIterator<Point> ptr = points.getVerticesInOrder().listIterator();

		boolean begin = false;
		Point start, ptemp, ntemp;
		start = new Point();
		ptemp = new Point();
		while(ptr.hasNext())
		{
			if(begin == false)
			{
				begin = true;
				start = new Point(ptr.next());
				ptemp = start;
				draw.makeRoundPoint(g, (int)((ptemp.getX() + 0.5)*rasterSize), (int)((frameHeight - ptemp.getY() - 0.5)*rasterSize),
						(float)((1.0 * rasterSize)/10.0), new Color(1.0f, 1.0f, 0.0f));
			}
			else
			{
				ntemp = ptr.next();
				draw.makeLine(g, (int)((ptemp.getX() + 0.5) * rasterSize), (int)((frameHeight - ptemp.getY() - 0.5) * rasterSize),
						(int)((ntemp.getX() + 0.5) * rasterSize), (int)((frameHeight - ntemp.getY() - 0.5) * rasterSize), new Color(1.0f, 1.0f, 0.0f), false);
				draw.makeFilledRoundPoint(g, (int)((ntemp.getX() + 0.5)*rasterSize), (int)((frameHeight - ntemp.getY() - 0.5)*rasterSize),
						(float)((1.0 * rasterSize)/10.0), new Color(1.0f, 1.0f, 0.0f));
				ptemp = ntemp;
			}
		}
		draw.makeLine(g, (int)((ptemp.getX() + 0.5) * rasterSize), (int)((frameHeight - ptemp.getY() - 0.5) * rasterSize),
				(int)((start.getX() + 0.5) * rasterSize), (int)((frameHeight - start.getY() - 0.5) * rasterSize), new Color(1.0f, 1.0f, 0.0f), false);
		
		g.setColor(Color.WHITE);
		g.drawString("Enter coordinates x1 y1,x2 y2,x3 y3,... of the vertices (max 10 vertices)",
				TRANSLATE_X + frameWidth * rasterSize, TRANSLATE_Y + WINDOW_GAP_1 - 2*WINDOW_GAP_2);
	}

	void drawCheckState(Graphics g)
	{
		drawFrameBufferPol(g);
	}

	void drawFoundState(Graphics g)
	{
		drawFrameBufferPol(g);
	}

	void drawDrawOverState(Graphics g)
	{
		int x;

		//System.out.println("*************** " + scan);
		Vector<Edge> temp = activeList.get(scan);
		ListIterator<Edge> i = temp.listIterator();

		while(i.hasNext())
		{
			Edge curr_edge = i.next();
			Edge next_edge = i.next();
			for(x = (int)Math.round(curr_edge.xIntersect); x <= (int)Math.round(next_edge.xIntersect); x++)
			{
				fb1.setPixel((int)x, scan);
				//System.out.println("Pixels set ---- " + (int)(x) + "\t" + scan);
			}
		}
		//System.out.println("-------------------------------------------");
		repaint();
	}

	void drawFrameBufferPol(Graphics g)
	{
		int i, j;

		draw.makeLine(g, 0, 0, frameWidth * rasterSize, 0, new Color(0.3f, 0.3f, 0.3f), false);
		draw.makeLine(g, frameWidth * rasterSize, frameHeight * rasterSize,
				frameWidth * rasterSize, 0, new Color(0.3f, 0.3f, 0.3f), false);

		for(i = 0; i < frameWidth; i++)
		{
			for (j = 0; j < frameHeight; j++)
			{
				if(j <= scan && !fb1.pixelActive(i, j) && state != END)
				{
					draw.makeRectangle(g, (i) * rasterSize, (i + 1) * rasterSize, (frameHeight - j - 1) * rasterSize, (frameHeight - j - 2) * rasterSize,
							new Color(1.0f, 1.0f, 1.0f), true, false);
				}
				if(fb1.pixelActive(i, j))
				{
					if(j <= scan)
						draw.makeRectangle(g, (i) * rasterSize, (i + 1) * rasterSize, (frameHeight - j - 1) * rasterSize, (frameHeight - j - 2) * rasterSize,
								new Color(1.0f, 0.0f, 0.0f), true, false);
				}

				draw.makeLine(g, 0, (frameHeight - j) * rasterSize,
						frameWidth * rasterSize, (frameHeight - j) * rasterSize, new Color(0.3f, 0.3f, 0.3f), false);
				draw.writeCoordinates(g, 0 - (int)(rasterSize/3), (frameHeight - j) * rasterSize - (int)(rasterSize/2),
						j, new Color(1.0f, 1.0f, 1.0f));
				draw.writeCoordinates(g, frameWidth * rasterSize + (int)(rasterSize/3),
						(frameHeight - j) * rasterSize - (int)(rasterSize/2), j, new Color(1.0f, 1.0f, 1.0f));

			}

			draw.makeLine(g, i * rasterSize, frameHeight * rasterSize,
					i * rasterSize, 0, new Color(0.3f, 0.3f, 0.3f), false);
			draw.writeCoordinates(g, i * rasterSize + (int)(rasterSize/2), frameHeight * rasterSize + (int)(rasterSize/3),
					i, new Color(1.0f, 1.0f, 1.0f));
			draw.writeCoordinates(g, i * rasterSize + (int)(rasterSize/2), 0 - (int)(rasterSize/3),
					i, new Color(1.0f, 1.0f, 1.0f));
		}
		
		g.setColor(new Color(1.0f, 1.0f, 0.0f));
		ListIterator<Point> ptr = points.getVerticesInOrder().listIterator();

		boolean begin = false;
		Point start, ptemp, ntemp;
		start = new Point();
		ptemp = new Point();
		while(ptr.hasNext())
		{
			if(begin == false)
			{
				begin = true;
				start = new Point(ptr.next());
				ptemp = start;
				draw.makeRoundPoint(g, (int)((ptemp.getX() + 0.5)*rasterSize), (int)((frameHeight - ptemp.getY() - 0.5)*rasterSize),
						(float)((1.0 * rasterSize)/10.0), new Color(1.0f, 1.0f, 0.0f));
			}
			else
			{
				ntemp = ptr.next();
				draw.makeLine(g, (int)((ptemp.getX() + 0.5) * rasterSize), (int)((frameHeight - ptemp.getY() - 0.5) * rasterSize),
						(int)((ntemp.getX() + 0.5) * rasterSize), (int)((frameHeight - ntemp.getY() - 0.5) * rasterSize), new Color(1.0f, 1.0f, 0.0f), false);
				draw.makeFilledRoundPoint(g, (int)((ntemp.getX() + 0.5)*rasterSize), (int)((frameHeight - ntemp.getY() - 0.5)*rasterSize),
						(float)((1.0 * rasterSize)/10.0), new Color(1.0f, 1.0f, 0.0f));
				ptemp = ntemp;
			}
		}
		draw.makeLine(g, (int)((ptemp.getX() + 0.5) * rasterSize), (int)((frameHeight - ptemp.getY() - 0.5) * rasterSize),
				(int)((start.getX() + 0.5) * rasterSize), (int)((frameHeight - start.getY() - 0.5) * rasterSize), new Color(1.0f, 1.0f, 0.0f), false);
		
		g.setColor(new Color(1.0f, 1.0f, 1.0f));
		
		g.setFont(new Font("Helective", Font.BOLD, 20));
		g.drawString("Present Scan Line : " + scan, frameWidth * rasterSize + WINDOW_GAP_1, 0);
		
		if(state == CHECK)
		{
			g.setFont(new Font("Helective", Font.BOLD, 16));
			g.drawString("Entries of AET : ", frameWidth * rasterSize + WINDOW_GAP_1, 2 * WINDOW_GAP_2);

			g.setColor(new Color(1.0f, 0.0f, 0.0f));
			Vector<Edge> temp = activeList.get(scan);
			ListIterator<Edge> edge_ptr = temp.listIterator();
			int count_point = 1;

			g.setFont(new Font("Helective", Font.BOLD, 12));

			if(temp.size() == 0)
				g.drawString("None", frameWidth * rasterSize + WINDOW_GAP_1, (2 + count_point) * WINDOW_GAP_2);
			while(edge_ptr.hasNext())
			{
				Edge curr_edge = edge_ptr.next();
				Edge next_edge = edge_ptr.next();
				
				g.drawString("(" + (int)Math.round(curr_edge.xIntersect) + ", " + scan + ") ("
						+ (int)Math.round(next_edge.xIntersect) + ", " + scan + ")",
						frameWidth * rasterSize + WINDOW_GAP_1, (2 + count_point) * WINDOW_GAP_2);
				count_point++;
			}
		}
		else if(state == DRAW_OVER)
		{
			g.setFont(new Font("Helective", Font.BOLD, 16));
			g.drawString("Filled points of the polygon along this scan line: ", frameWidth * rasterSize + WINDOW_GAP_1, 2 * WINDOW_GAP_2);

			g.setColor(new Color(1.0f, 0.0f, 0.0f));
			Vector<Edge> temp = activeList.get(scan);
			ListIterator<Edge> edge_ptr = temp.listIterator();
			int count_point = 1, tmp_point;

			g.setFont(new Font("Helective", Font.BOLD, 12));

			int[] a;
			int counter = 0, counter_length = 0;
			a = new int[2*frameWidth];
			while(edge_ptr.hasNext())
			{
				Edge curr_edge = edge_ptr.next();
				Edge next_edge = edge_ptr.next();
				
				for(tmp_point = (int)Math.round(curr_edge.xIntersect);
								tmp_point <= (int)Math.round(next_edge.xIntersect); tmp_point++)
				{
					a[counter++] = tmp_point;
				}		
			}
			counter_length = counter;
			if(a.length > 0)
			{
				Arrays.sort(a, 0, counter_length);

				g.drawString("(" + a[0] + ", " + scan + ")",
						frameWidth * rasterSize + WINDOW_GAP_1, (2 + count_point) * WINDOW_GAP_2);
				count_point++;
				for(counter = 1; counter < counter_length; counter++)
				{
					if(a[counter] != a[counter - 1])
					{
						g.drawString("(" + a[counter] + ", " + scan + ")",
								frameWidth * rasterSize + WINDOW_GAP_1, (2 + count_point) * WINDOW_GAP_2);
						count_point++;
					}				
				}
			}
		}
		else if(state == END)
		{
			g.setColor(new Color(1.0f, 1.0f, 1.0f));
			g.setFont(new Font("Helectiva", Font.BOLD, 18));
			g.drawString("POLYGON FILLED!!!", TRANSLATE_X + (frameWidth + 1) * rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + 4*WINDOW_GAP_2);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		if(e.getActionCommand() == BackName)
		{
			fb1 = new FrameBuffer(frameWidth + 1, frameHeight + 1, rasterSize);
			fb2 = new FrameBuffer(frameWidth + 1, frameHeight + 1, rasterSize);
			state = FRAME_BUFFER;
			counter = 0;
			scan = 0;
			active = false;
			foundStateDone = false;
			draw = new DrawToolkit();
			s = new ScanConversion();
		
			next_button.setVisible(false);
			previous_button.setVisible(false);
			default_params.setVisible(true);
			frame_buffer.setVisible(true);
			start_end.setVisible(false);
			back_button.setVisible(false);

			frame_buffer_width_text.setVisible(true);
			frame_buffer_height_text.setVisible(true);
			vertices_text.setVisible(false);

			frame_buffer_width_text.setText(frameWidth+"");
			frame_buffer_height_text.setText(frameHeight+"");

			String vertices_temp = "";
			ListIterator<Point> ptr = points.getVerticesInOrder().listIterator();
			Point ptemp;
			ptemp = new Point();
			while(ptr.hasNext())
			{
				ptemp = new Point(ptr.next());
				vertices_temp += ptemp.getX() + " " + ptemp.getY() + ",";
			}
			vertices_text.setText(vertices_temp);
			repaint();
			
			return;
		}
		if(state == FRAME_BUFFER)
		{
			if(e.getActionCommand() == DefaultParamsName)
			{
				state = CHECK;
				default_params.setVisible(false);
				next_button.setVisible(true);
				previous_button.setVisible(true);
				back_button.setVisible(true);
				
				frame_buffer_height_text.setVisible(false);
				frame_buffer_width_text.setVisible(false);

				fb1 = new FrameBuffer(frameWidth + 1, frameHeight + 1, rasterSize);
				fb2 = new FrameBuffer(frameWidth + 1, frameHeight + 1, rasterSize);

				fb2 = s.ScanConvert(points, fb2);
				fb2.draw();

				checked = s.getChecked();
				activeList = s.getActiveList();				
				repaint();
			}
			else if(e.getActionCommand() == FrameBufferName)
			{
				try
				{
					frameHeight = Integer.parseInt(frame_buffer_height_text.getText());
					frameWidth = Integer.parseInt(frame_buffer_width_text.getText());
					if(frameHeight > 25 || frameHeight <= 0 || frameWidth > 25 || frameWidth <= 0)
					{
						if(frameHeight > 25 || frameHeight <= 0)
						{
							frameHeight = 15;
							frame_buffer_height_text.setText("" + frameHeight);
						}
						if(frameWidth > 25 || frameWidth <= 0)
						{
							frameWidth = 15;
							frame_buffer_width_text.setText("" + frameWidth);
						}
					}
					else
					{
						state = VERTICES;
						int raster_height, raster_width;
						raster_height = Math.round(PROD_RASTER_FRAME/frameHeight);
						raster_width = Math.round(PROD_RASTER_FRAME/frameWidth);
						rasterSize = Math.min(raster_height, raster_width);
						
						String vertices_temp = "";
						ListIterator<Point> ptr = points.getVerticesInOrder().listIterator();
						Point ptemp;
						ptemp = new Point();
						int count = 0;
						while(ptr.hasNext())
						{
							ptemp = new Point(ptr.next());
							if(ptemp.getX() >= frameWidth)
								points.setVertexAt(count, new Point(frameWidth - 1, ptemp.getY()));
							if(ptemp.getY() >= frameHeight)
								points.setVertexAt(count, new Point(ptemp.getX(), frameHeight - 1));
										
							vertices_temp += points.getVertexAt(count).getX() + " "
												+ points.getVertexAt(count).getY() + ",";
							count ++;
						}

						fb1 = new FrameBuffer(frameWidth + 1, frameHeight + 1, rasterSize);
						fb2 = new FrameBuffer(frameWidth + 1, frameHeight + 1, rasterSize);
						
						vertices_text.setText(vertices_temp);
						
						frame_buffer.setVisible(false);
						start_end.setVisible(true);
						back_button.setVisible(true);
						
						frame_buffer_height_text.setVisible(false);
						frame_buffer_width_text.setVisible(false);
						default_params.setVisible(false);
						vertices_text.setVisible(true);
					}
				}
				catch(NumberFormatException error)
				{
					frameHeight = 15;
					frameWidth = 15;
					rasterSize = 35;
					frame_buffer_height_text.setText("" + frameHeight);
					frame_buffer_width_text.setText("" + frameWidth);
				}
				repaint();
			}
		}
		else if(state == VERTICES)
		{
			boolean state_continue = false;
			if(e.getActionCommand() == StartEndName)
			{
				String str;
				String[] str1 = new String[10];
				String[] str2 = new String[10];
				int str1_count, str2_count, coord_length;
				int coord[] = new int[2];
				Point p_temp;
				
				try
				{
					str = vertices_text.getText();
					str1 = str.split(",");
					points = new Polygon();
					str1_count = 0;
					
					while(str1_count < str1.length)
					{
						str2 = str1[str1_count].split("[ \t\n\f\r]");
						str2_count = coord_length = 0;
						
						while(str2_count < str2.length)
						{
							try
							{
								coord[coord_length] = Integer.parseInt(str2[str2_count]);
								coord_length++;
							}
							catch(Exception error)
							{
								// TODO: handle exception
								if(str2[str2_count].length() > 0)
								{
									state_continue = true;
									break;
								}
							}
							str2_count++;
						}
						if(coord_length == 2 && state_continue == false)
						{
							if((coord[0] < 0 || coord[0] >= frameWidth) || (coord[1] < 0 || coord[1] >= frameHeight))
							{
								state_continue = true;
								break;								
							}
							else
							{
								p_temp = new Point(coord[0], coord[1]);
								points.addVertex(p_temp);
							}
						}
						else
						{
							state_continue = true;
							break;
						}
						
						str1_count++;
					}
					if(state_continue == false)
					{
						fb2 = s.ScanConvert(points, fb2);
						fb2.draw();

						checked = null;
						activeList = null;
						checked = s.getChecked();
						activeList = s.getActiveList();

						/*
						for(int i = 0; i < activeList.size(); i++)
						{
							Vector<Edge> temp = activeList.get(i);
							ListIterator<Edge> krust = temp.listIterator();

							while(krust.hasNext())
							{
								Edge temp_edge = krust.next();
								System.out.println("Pixels set ---- " + (int)Math.round(temp_edge.xIntersect) + "\t" + i);
							}
						}
						*/
						
						state = CHECK;
						
						start_end.setVisible(false);
						default_params.setVisible(false);
						next_button.setVisible(true);
						previous_button.setVisible(true);
						vertices_text.setVisible(false);
					}
				}
				catch(NumberFormatException error)
				{
					state_continue = true;
				}
			}
			//System.out.println("---------- " + state_continue);
			if(state_continue)
			{
				String str = new String();
				String[] str1 = new String[10];
				s = new ScanConversion();
				int x, y;

				try
				{
					URL url = new URL(this.getCodeBase(), this.getParameter("file_name"));
					BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
					if((str = br.readLine()) == null)
					{
						//====System.out.println("No entries in the file.");
						System.exit(0);
					}
					str1 = str.split("[ \t\n\f\r]");

					points = new Polygon();
					while((str = br.readLine()) != null)
					{
						str1 = str.split("[ \t\n\f\r]");
						x = Integer.parseInt(str1[0]);
						y = Integer.parseInt(str1[1]);

						if((x < 0 || x >= frameWidth) || (y < 0 || y >= frameHeight))
						{
							//================System.out.println("Values of starting and ending points of the line cannot be greater than the size of the Frame Buffer or negative");
							System.exit(0);
						}
						p = new Point(x, y);
						points.addVertex(p);
					}
				}
				catch (FileNotFoundException error)
				{
					// TODO Auto-generated catch block
					System.exit(0);
				}
				catch (IOException error)
				{
					// TODO Auto-generated catch block
					System.exit(0);
				}
				String vertices_temp = "";
				ListIterator<Point> ptr = points.getVerticesInOrder().listIterator();
				Point ptemp;
				ptemp = new Point();
				while(ptr.hasNext())
				{
					ptemp = new Point(ptr.next());
					vertices_temp += ptemp.getX() + " " + ptemp.getY() + ",";
				}
				vertices_text.setText(vertices_temp);
			}
			repaint();	
		}
		else if(e.getActionCommand() == NextButtonName)
			toNext();
		else if(e.getActionCommand() == PreviousButtonName)
			toPrevious();
	}
}
