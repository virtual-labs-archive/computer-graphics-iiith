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

import java.util.ListIterator;
import java.util.Vector;

import library.DrawToolkit;
import library.FrameBuffer;
import library.Line;
import library.Point;

public class ScanLineMain extends Applet implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int WINDOW_GAP_1 = 60;
	private static final int WINDOW_GAP_2 = 20;

	private static final int OFFSET = 10;

	public static final int WINDOW_HEIGHT = 1000;
	public static final int WINDOW_WIDTH = 1200;

	private static final int PROD_RASTER_FRAME = 525;

	private static final int FRAME_BUFFER = -2;
	private static final int START_END_POINT = -1;
	private static final int NEW_POINT = 0;
	private static final int POINT_CONSIDERATION = 1;
	private static final int POINT_DECIDED = 2;
	//private static final int NO_OF_STATES = 3;
	private static final int STATE_OVER = 3;

	private static final String NextButtonName = "Next Iteration";
	private static final String PreviousButtonName = "Previous Iteration";
	private static final String DefaultParamsName = "Start Experiment with Default Values";
	private static final String FrameBufferName = "Enter";
	private static final String StartEndName = "Start Experiment";
	private static final String BackName = "Back";

	private Button next_button, previous_button, default_params, frame_buffer, start_end, back_button;
	private static final int next_button_width = 100;
	private static final int previous_button_width = 125;
	private static final int default_button_width = 250;
	private static final int frame_start_button_width = 250;
	private static final int back_button_width = 100;
	private static final int button_height = 20;

	private static TextField frame_buffer_height_text, frame_buffer_width_text;
	private static TextField start_x1_text, start_y1_text, end_x2_text, end_y2_text;
	private static final int TEXT_COLUMNS = 50;

	private FrameBuffer fb;
	//private boolean[][] fbCheck;

	private Point selPoint;
	private Point notSelPoint;
	private Vector<Point> selPoints;
	private Vector<Point> notSelPoints;
	private ListIterator<Point> itSel;
	private ListIterator<Point> itNotSel;

	private int state;
	private int counter;
	private boolean next, previous, nextState, previousState;

	private int frameWidth, frameHeight, rasterSize;

	private ScanConversion s;
	private DrawToolkit draw;

	private int TRANSLATE_X = 50;
	private int TRANSLATE_Y = 50;

	private int steps;

	private int x1, x2, y1, y2;

	private double slope_constant[];
	private double actual_value, diff1, diff2;

	public void init()
	{
		s = new ScanConversion();

		/*
		try
		{
			URL url = new URL(this.getCodeBase(), this.getParameter("file_name"));
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			if((str = br.readLine()) == null)
			{
				//=====System.out.println("No entries in the file.");
				System.exit(0);
			}
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */

		frameWidth = 15;
		frameHeight = 15;
		rasterSize = 35;
		fb = new FrameBuffer(frameWidth + 1, frameHeight + 1, rasterSize);

		x1 = 2;
		y1 = 5;
		x2 = 9;
		y2 = 10;
		/*
		if((x1 < 0 || x1 > frameWidth) || (x2 < 0 || x2 > frameWidth) || (y1 < 0 || y1 > frameHeight) || (y2 < 0 || y2 > frameHeight))
		{
			//================System.out.println("Values of starting and ending points of the line cannot be greater than the size of the Frame Buffer or negative");
			System.exit(0);
		}
		 */
		slope_constant = new double[2];

		next = previous = nextState = previousState = false;

		state = FRAME_BUFFER;
		counter = 0;

		draw = new DrawToolkit();

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

		frame_buffer_height_text = new TextField(TEXT_COLUMNS);
		frame_buffer_width_text = new TextField(TEXT_COLUMNS);
		start_x1_text = new TextField(TEXT_COLUMNS);
		start_y1_text = new TextField(TEXT_COLUMNS);
		end_x2_text = new TextField(TEXT_COLUMNS);
		end_y2_text = new TextField(TEXT_COLUMNS);

		add(next_button);
		add(previous_button);
		add(default_params);
		add(frame_buffer);
		add(start_end);
		add(back_button);

		add(frame_buffer_height_text);
		add(frame_buffer_width_text);
		add(start_x1_text);
		add(start_y1_text);
		add(end_x2_text);
		add(end_y2_text);

		next_button.setBackground(Color.WHITE);
		previous_button.setBackground(Color.WHITE);
		default_params.setBackground(Color.WHITE);
		frame_buffer.setBackground(Color.WHITE);
		start_end.setBackground(Color.WHITE);
		back_button.setBackground(Color.WHITE);

		frame_buffer_height_text.setBackground(Color.WHITE);
		frame_buffer_width_text.setBackground(Color.WHITE);
		start_x1_text.setBackground(Color.WHITE);
		start_y1_text.setBackground(Color.WHITE);
		end_x2_text.setBackground(Color.WHITE);
		end_y2_text.setBackground(Color.WHITE);

		next_button.addActionListener(this);
		previous_button.addActionListener(this);
		default_params.addActionListener(this);
		frame_buffer.addActionListener(this);
		start_end.addActionListener(this);
		back_button.addActionListener(this);

		frame_buffer_height_text.addActionListener(this);
		frame_buffer_width_text.addActionListener(this);
		start_x1_text.addActionListener(this);
		start_y1_text.addActionListener(this);
		end_x2_text.addActionListener(this);
		end_y2_text.addActionListener(this);

		next_button.setVisible(false);
		previous_button.setVisible(false);
		default_params.setVisible(true);
		frame_buffer.setVisible(true);
		start_end.setVisible(false);
		back_button.setVisible(false);

		frame_buffer_height_text.setVisible(true);
		frame_buffer_width_text.setVisible(true);
		start_x1_text.setVisible(false);
		start_y1_text.setVisible(false);
		end_x2_text.setVisible(false);
		end_y2_text.setVisible(false);

		next_button.setBounds(TRANSLATE_X + (frameWidth + 1)* rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + 5*WINDOW_GAP_2, next_button_width, button_height);
		previous_button.setBounds(TRANSLATE_X + (frameWidth + 1) * rasterSize + WINDOW_GAP_1 + 2 * WINDOW_GAP_2 + next_button_width,
				TRANSLATE_Y + WINDOW_GAP_1 + 5*WINDOW_GAP_2, previous_button_width, button_height);
		default_params.setBounds(TRANSLATE_X + (frameWidth + 1) * rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + 6*WINDOW_GAP_2, default_button_width, button_height);
		frame_buffer.setBounds(TRANSLATE_X + (frameWidth + 1) * rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + 4*WINDOW_GAP_2 + OFFSET, frame_start_button_width, button_height);
		start_end.setBounds(TRANSLATE_X + (frameWidth + 1) * rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + 4*WINDOW_GAP_2 + OFFSET, frame_start_button_width, button_height);
		back_button.setBounds(TRANSLATE_X + (frameWidth + 1) * rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + 8*WINDOW_GAP_2, back_button_width, button_height);

		frame_buffer_height_text.setBounds(TRANSLATE_X + (frameWidth + 1) * rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + WINDOW_GAP_2,	TEXT_COLUMNS, button_height);
		frame_buffer_width_text.setBounds(TRANSLATE_X + (frameWidth + 1) * rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + 3*WINDOW_GAP_2, TEXT_COLUMNS, button_height);
		start_x1_text.setBounds(TRANSLATE_X + (frameWidth + 1) * rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + WINDOW_GAP_2, TEXT_COLUMNS, button_height);
		start_y1_text.setBounds(TRANSLATE_X + (frameWidth + 1) * rasterSize + WINDOW_GAP_1 + 4*WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + WINDOW_GAP_2, TEXT_COLUMNS, button_height);
		end_x2_text.setBounds(TRANSLATE_X + (frameWidth + 1) * rasterSize + WINDOW_GAP_1 + WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + 3*WINDOW_GAP_2, TEXT_COLUMNS, button_height);
		end_y2_text.setBounds(TRANSLATE_X + (frameWidth + 1) * rasterSize + WINDOW_GAP_1 + 4*WINDOW_GAP_2,
				TRANSLATE_Y + WINDOW_GAP_1 + 3*WINDOW_GAP_2, TEXT_COLUMNS, button_height);

		frame_buffer_height_text.setText(frameHeight+"");
		frame_buffer_width_text.setText(frameWidth+"");
		start_x1_text.setText(x1+"");
		start_y1_text.setText(y1+"");
		end_x2_text.setText(x2+"");
		end_y2_text.setText(y2+"");

		steps = 0;
	}

	public void paint(Graphics g)
	{
		g.translate(TRANSLATE_X, TRANSLATE_Y);

		counter++;
		if(state == FRAME_BUFFER)
		{
			drawDecideFrameSize(g);
		}
		else if(state == START_END_POINT)
		{
			drawDecideStartEnd(g);
		}
		else
		{
			if(next || previous)
			{
				if(state == NEW_POINT)
					drawNewPointState(g);
				else if(state == POINT_CONSIDERATION)
					drawPointConsiderationState(g);
				else if(state == POINT_DECIDED)
					drawPointDecidedState(g);
				else if(state == STATE_OVER)
					drawFrameBufferLine(g);
				next = false;
				previous = false;
			}
			drawFrameBufferLine(g);
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
		g.setColor(Color.WHITE);
		g.drawString("Enter Frame Height (max - 25)",
				TRANSLATE_X + frameWidth * rasterSize, TRANSLATE_Y + WINDOW_GAP_1 - 2*WINDOW_GAP_2);
		g.drawString("Enter Frame Width (max - 25)",
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

		Color gTemp = g.getColor();
		draw.makeRectangle(g, (x1)*rasterSize, (x1 + 1)*rasterSize,
				(frameHeight - y1 - 1)*rasterSize, (frameHeight - y1 - 2)*rasterSize, 
				new Color(0.3f, 0.3f, 0.3f), true, false);
		draw.makeRectangle(g, (x2)*rasterSize, (x2 + 1)*rasterSize,
				(frameHeight - y2 - 1)*rasterSize, (frameHeight - y2 - 2)*rasterSize, 
				new Color(0.3f, 0.3f, 0.3f), true, false);
		g.setColor(gTemp);

		g.setColor(new Color(1.0f, 1.0f, 1.0f));
		g.drawLine((int)((x1 + 0.5) * rasterSize), (int)((frameHeight - y1 - 0.5) * rasterSize),
				(int)((x2 + 0.5) * rasterSize),  (int)((frameHeight - y2 - 0.5) * rasterSize));
		draw.makeFilledRoundPoint(g, (int)((x1 + 0.5) * rasterSize), (int)((frameHeight - y1 - 0.5) * rasterSize),
				(float)((1.0 * rasterSize)/10.0), new Color(0.0f, 0.0f, 1.0f));
		draw.makeFilledRoundPoint(g, (int)((x2 + 0.5) * rasterSize), (int)((frameHeight - y2 - 0.5) * rasterSize),
				(float)((1.0 * rasterSize)/10.0), new Color(0.0f, 0.0f, 1.0f));

		g.setColor(Color.WHITE);
		g.drawString("Enter coordinates (x, y) of 1st end point",
				TRANSLATE_X + frameWidth * rasterSize, TRANSLATE_Y + WINDOW_GAP_1 - 2*WINDOW_GAP_2);
		g.drawString("Enter coordinates (x, y) of 2nd end point",
				TRANSLATE_X + frameWidth * rasterSize, TRANSLATE_Y + WINDOW_GAP_1);
	}

	private void goToPrevious()
	{
		if(steps > 0)
		{
			previous = previousState = true;
			counter = 0;

			steps --;
			if(state == NEW_POINT)
				state = POINT_DECIDED;
			else if(state == POINT_DECIDED || state == STATE_OVER)
				state = NEW_POINT;
			repaint();
		}

	}

	private void goToNext()
	{
		next = nextState = true;
		counter = 0;
		steps++;
		if(state == NEW_POINT)
			state = POINT_DECIDED;
		else if(state == POINT_DECIDED)
			state = NEW_POINT;

		repaint();
	}

	private void drawNewPointState(Graphics g)
	{
		if(next)
		{
			if(!itSel.hasNext())
			{
				state = STATE_OVER;
				return;
			}

			selPoint = itSel.next();
			notSelPoint = itNotSel.next();
			if(previousState)
			{
				previousState = false;
				fb.setPixel(notSelPoint.getX(), notSelPoint.getY(), 0.5f, 0.5f, 0.5f);
				fb.setPixel(selPoint.getX(), selPoint.getY(), 1.0f, 0.0f, 0.0f);
				selPoint = itSel.next();
				notSelPoint = itNotSel.next();
			}

			fb.setPixel(selPoint.getX(), selPoint.getY(), 0.7f, 0.7f, 0.0f);
			fb.setPixel(notSelPoint.getX(), notSelPoint.getY(), 0.7f, 0.7f, 0.0f);
		}
		if(previous)
		{
			if(nextState)
			{
				nextState = false;

				if(itSel.previousIndex() >= 0)
				{
					selPoint = itSel.previous();
					notSelPoint = itNotSel.previous();
				}
			}
			fb.setPixel(notSelPoint.getX(), notSelPoint.getY(), 0.7f, 0.7f, 0.0f);
			fb.setPixel(selPoint.getX(), selPoint.getY(), 0.7f, 0.7f, 0.0f);
		}

		/*==========================
		System.out.println(" ------------- Reached value : " + selPoint.getX() +
				"\t" + selPoint.getY() + " and colours " + fb.getRedForPixel(selPoint.getX(), selPoint.getY()));
		System.out.println(" ------------- Reached wrong value : " + notSelPoint.getX() +
				"\t" + notSelPoint.getY() + "--------------------------------");
				==============*/
	}

	private void drawPointConsiderationState(Graphics g)
	{
	}

	private void drawPointDecidedState(Graphics g)
	{
		if(next)
		{
			if(previousState)
			{
				fb.setPixel(notSelPoint.getX(), notSelPoint.getY(), 0.5f, 0.5f, 0.5f);
				fb.setPixel(selPoint.getX(), selPoint.getY(), 1.0f, 0.0f, 0.0f);
				selPoint = itSel.next();
				notSelPoint = itNotSel.next();
				previousState = false;
			}

			fb.setPixel(notSelPoint.getX(), notSelPoint.getY(), 0.5f, 0.5f, 0.5f);
			fb.setPixel(selPoint.getX(), selPoint.getY(), 1.0f, 0.0f, 0.0f);
		}
		if(previous)
		{
			if(itSel.previousIndex() >= 0)
			{
				fb.unsetPixel(selPoint.getX(), selPoint.getY());
				fb.unsetPixel(notSelPoint.getX(), notSelPoint.getY());

				selPoint = itSel.previous();
				notSelPoint = itNotSel.previous();
				if(nextState)
				{
					nextState = false;
					selPoint = itSel.previous();
					notSelPoint = itNotSel.previous();
				}

				fb.setPixel(notSelPoint.getX(), notSelPoint.getY(), 0.5f, 0.5f, 0.5f);
				fb.setPixel(selPoint.getX(), selPoint.getY(), 1.0f, 0.0f, 0.0f);
			}


		}
		if(Math.abs(slope_constant[0]) <= 1)
		{
			actual_value = (slope_constant[0]*selPoint.getX()) + slope_constant[1];
			diff1 = Math.abs(actual_value - selPoint.getY());
			diff2 = Math.abs(actual_value - notSelPoint.getY());
		}
		else
		{
			actual_value = (selPoint.getY() - slope_constant[1])/(1.0 * slope_constant[0]);
			diff1 = Math.abs(actual_value - selPoint.getX());
			diff2 = Math.abs(actual_value - notSelPoint.getX());
		}
	}

	void drawFrameBufferLine(Graphics g)
	{
		int i, j;

		draw.makeLine(g, 0, 0, frameWidth * rasterSize, 0, new Color(0.3f, 0.3f, 0.3f), false);
		draw.makeLine(g, frameWidth * rasterSize, frameHeight * rasterSize,
				frameWidth * rasterSize, 0, new Color(0.3f, 0.3f, 0.3f), false);		

		for(i = 0; i < frameWidth; i++)
		{
			for (j = 0; j < frameHeight; j++)
			{
				if(fb.pixelActive(i, j))
				{
					if(state == STATE_OVER)
					{
						if(fb.getRedForPixel(i, j) == 1.0f)
						{
							Color gTemp = g.getColor();
							draw.makeRectangle(g, (i)*rasterSize, (i + 1)*rasterSize,
									(frameHeight - j - 1)*rasterSize, (frameHeight - j - 2)*rasterSize, 
									new Color((fb.getRedForPixel(i, j)), (fb.getGreenForPixel(i, j)), (fb.getBlueForPixel(i, j))),
									true, false);
							g.setColor(gTemp);
						}
					}
					else if((i == (selPoint.getX())) && (j == (selPoint.getY())) && (state == NEW_POINT))
					{
						Color gTemp = g.getColor();
						draw.makeRectangle(g, (i)*rasterSize, (i + 1)*rasterSize,
								(frameHeight - j - 1)*rasterSize, (frameHeight - j - 2)*rasterSize, 
								new Color((fb.getRedForPixel(i, j)), (fb.getGreenForPixel(i, j)), (fb.getBlueForPixel(i, j))),
								true, false);
						g.setColor(gTemp);
					}
					else
					{
						Color gTemp = g.getColor();
						draw.makeRectangle(g, (i)*rasterSize, (i + 1)*rasterSize,
								(frameHeight - j - 1)*rasterSize, (frameHeight - j - 2)*rasterSize, 
								new Color((fb.getRedForPixel(i, j)), (fb.getGreenForPixel(i, j)), (fb.getBlueForPixel(i, j))),
								true, false);
						g.setColor(gTemp);
					}
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

		g.setColor(new Color(1.0f, 1.0f, 1.0f));
		g.drawLine((int)((x1 + 0.5) * rasterSize), (int)((frameHeight - y1 - 0.5) * rasterSize),
				(int)((x2 + 0.5) * rasterSize),  (int)((frameHeight - y2 - 0.5) * rasterSize));
		draw.makeFilledRoundPoint(g, (int)((x1 + 0.5) * rasterSize), (int)((frameHeight - y1 - 0.5) * rasterSize),
				(float)((1.0 * rasterSize)/10.0), new Color(0.0f, 0.0f, 1.0f));
		draw.makeFilledRoundPoint(g, (int)((x2 + 0.5) * rasterSize), (int)((frameHeight - y2 - 0.5) * rasterSize),
				(float)((1.0 * rasterSize)/10.0), new Color(1.0f, 1.0f, 1.0f));

		if(state == NEW_POINT)
		{
			g.setColor(new Color(0.7f, 0.7f, 0.0f));
			g.setFont(new Font("Helectiva", Font.BOLD, 18));
			g.drawString("Points in consideration\n", frameWidth * rasterSize + WINDOW_GAP_1, WINDOW_GAP_1);
			g.setFont(new Font("Helectiva", Font.PLAIN, 14));

			if((selPoint.getX() == notSelPoint.getX()) && (selPoint.getY() == notSelPoint.getY()))
			{
				g.drawString("( " + selPoint.getX() + ", " + selPoint.getY() + " )",
						frameWidth * rasterSize + WINDOW_GAP_1, WINDOW_GAP_1 + WINDOW_GAP_2);
			}
			else
			{
				if((selPoint.getX() < notSelPoint.getX()) || (selPoint.getY() < notSelPoint.getY()))
				{
					g.drawString("( " + selPoint.getX() + ", " + selPoint.getY() + " ) and "
							+ "( " + notSelPoint.getX() + ", " + notSelPoint.getY() + " )",
							frameWidth * rasterSize + WINDOW_GAP_1, WINDOW_GAP_1 + WINDOW_GAP_2);
				}
				else
				{
					g.drawString("( " + notSelPoint.getX() + ", " + notSelPoint.getY() + " ) and "
							+ "( " + selPoint.getX() + ", " + selPoint.getY() + " )",
							frameWidth * rasterSize + WINDOW_GAP_1, WINDOW_GAP_1 + WINDOW_GAP_2);
				}
			}
		}
		else if(state == POINT_DECIDED || state == STATE_OVER)
		{
			g.setColor(new Color(1.0f, 0.0f, 0.0f));
			g.setFont(new Font("Helectiva", Font.BOLD, 18));
			g.drawString("Point Decided", frameWidth * rasterSize + WINDOW_GAP_1, WINDOW_GAP_1);
			g.setFont(new Font("Helectiva", Font.PLAIN, 14));
			g.drawString(selPoint.getX() + ", " + selPoint.getY(),
					frameWidth * rasterSize + WINDOW_GAP_1, WINDOW_GAP_1 + WINDOW_GAP_2);

			g.setColor(new Color(1.0f, 1.0f, 1.0f));
			if(Math.abs(slope_constant[0]) <= 1)
			{
				g.drawString("From the equation y = " + String.format("%.2f", slope_constant[0]) +
						"x + " + String.format("%.2f", slope_constant[0]) + ", at x = " + selPoint.getX() +
						", y = " + String.format("%.2f", actual_value),
						frameWidth * rasterSize + WINDOW_GAP_1, WINDOW_GAP_1 + 2*WINDOW_GAP_2);

				if(!((selPoint.getX() == notSelPoint.getX()) && (selPoint.getY() == notSelPoint.getY())))
				{
					if((selPoint.getX() < notSelPoint.getX()) || (selPoint.getY() < notSelPoint.getY()))
					{
						g.drawString("y_diff for y = " + selPoint.getY() + " is " + String.format("%.2f", diff1),
								frameWidth * rasterSize + WINDOW_GAP_1, WINDOW_GAP_1 + 3*WINDOW_GAP_2);
						g.drawString("y_diff for y = " + notSelPoint.getY() + " is " + String.format("%.2f", diff2),
								frameWidth * rasterSize + WINDOW_GAP_1, WINDOW_GAP_1 + 4*WINDOW_GAP_2);
					}
					else
					{
						g.drawString("y_diff for y = " + notSelPoint.getY() + " is " + String.format("%.2f", diff2),
								frameWidth * rasterSize + WINDOW_GAP_1, WINDOW_GAP_1 + 3*WINDOW_GAP_2);
						g.drawString("y_diff for y = " + selPoint.getY() + " is " + String.format("%.2f", diff1),
								frameWidth * rasterSize + WINDOW_GAP_1, WINDOW_GAP_1 + 4*WINDOW_GAP_2);
					}
				}
			}
			else
			{
				g.drawString("From the equation y = " + String.format("%.2f", slope_constant[0]) +
						"x + " + String.format("%.2f", slope_constant[0]) + ", at y = " + selPoint.getY() +
						", x = " + String.format("%.2f", actual_value),
						frameWidth * rasterSize + WINDOW_GAP_1, WINDOW_GAP_1 + 2*WINDOW_GAP_2);

				if(!((selPoint.getX() == notSelPoint.getX()) && (selPoint.getY() == notSelPoint.getY())))
				{
					if((selPoint.getX() < notSelPoint.getX()) || (selPoint.getY() < notSelPoint.getY()))
					{
						g.drawString("x_diff for x = " + selPoint.getX() + " is " + String.format("%.2f", diff1),
								frameWidth * rasterSize + WINDOW_GAP_1, WINDOW_GAP_1 + 3*WINDOW_GAP_2);
						g.drawString("x_diff for x = " + notSelPoint.getX() + " is " + String.format("%.2f", diff2),
								frameWidth * rasterSize + WINDOW_GAP_1, WINDOW_GAP_1 + 4*WINDOW_GAP_2);
					}
					else
					{
						g.drawString("x_diff for x = " + notSelPoint.getX() + " is " + String.format("%.2f", diff2),
								frameWidth * rasterSize + WINDOW_GAP_1, WINDOW_GAP_1 + 3*WINDOW_GAP_2);
						g.drawString("x_diff for x = " + selPoint.getX() + " is " + String.format("%.2f", diff1),
								frameWidth * rasterSize + WINDOW_GAP_1, WINDOW_GAP_1 + 4*WINDOW_GAP_2);
					}
				}
			}
		}
		if(state == STATE_OVER)
		{
			g.setColor(new Color(1.0f, 1.0f, 1.0f));
			g.setFont(new Font("Helectiva", Font.BOLD, 18));
			g.drawString("LINE RASTERIZED!!!", frameWidth*rasterSize + WINDOW_GAP_1, WINDOW_GAP_1 + 7*WINDOW_GAP_2);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		if(e.getActionCommand() == BackName)
		{
			next = previous = nextState = previousState = false;
			state = FRAME_BUFFER;
			counter = steps = 0;
			s = new ScanConversion();
			fb = new FrameBuffer(frameWidth + 1, frameHeight + 1, rasterSize);
			draw = new DrawToolkit();

			next_button.setVisible(false);
			previous_button.setVisible(false);
			default_params.setVisible(true);
			frame_buffer.setVisible(true);
			start_end.setVisible(false);
			back_button.setVisible(false);

			frame_buffer_height_text.setVisible(true);
			frame_buffer_width_text.setVisible(true);
			start_x1_text.setVisible(false);
			start_y1_text.setVisible(false);
			end_x2_text.setVisible(false);
			end_y2_text.setVisible(false);

			frame_buffer_height_text.setText(frameHeight+"");
			frame_buffer_width_text.setText(frameWidth+"");
			start_x1_text.setText(x1+"");
			start_y1_text.setText(y1+"");
			end_x2_text.setText(x2+"");
			end_y2_text.setText(y2+"");
			repaint();
			return;
		}
		if(state == FRAME_BUFFER)
		{
			if(e.getActionCommand() == DefaultParamsName)
			{
				state = NEW_POINT;
				default_params.setVisible(false);
				next_button.setVisible(true);
				previous_button.setVisible(true);
				frame_buffer.setVisible(false);
				back_button.setVisible(true);

				frame_buffer_height_text.setVisible(false);
				frame_buffer_width_text.setVisible(false);
				
				frameWidth = 15;
				frameHeight = 15;
				rasterSize = 35;
				fb = new FrameBuffer(frameWidth + 1, frameHeight + 1, rasterSize);

				x1 = 2;
				y1 = 5;
				x2 = 9;
				y2 = 10;
				Point p1 = new Point(x1, y1);
				Point p2 = new Point(x2, y2);
				Line l = new Line(p1, p2);
				l.getSlopeConstant(slope_constant);

				fb = s.ScanConvert(l, fb);
				//fb.draw();

				selPoints = s.getSeqOrderOfSelPoints();
				notSelPoints = s.getSeqOrderOfNotSelPoints();

				itSel = selPoints.listIterator();
				itNotSel = notSelPoints.listIterator();
				selPoint = itSel.next();
				notSelPoint = itNotSel.next();
				fb.setPixel(selPoint.getX(), selPoint.getY(), 0.7f, 0.7f, 0.0f);

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
						state = START_END_POINT;

						int raster_height, raster_width;
						raster_height = Math.round(PROD_RASTER_FRAME/frameHeight);
						raster_width = Math.round(PROD_RASTER_FRAME/frameWidth);
						rasterSize = Math.min(raster_height, raster_width);

						fb = new FrameBuffer(frameWidth + 1, frameHeight + 1, rasterSize);

						x1 = (x1 < frameWidth)?x1:(frameWidth - 1);
						y1 = (y1 < frameHeight)?y1:(frameHeight - 1);
						x2 = (x2 < frameWidth)?x2:(frameWidth - 1);
						y2 = (y2 < frameHeight)?y2:(frameHeight - 1);

						//System.out.println(x1 + " " + y1);
						//System.out.println(x2 + " " + y2);
						start_x1_text.setText(""+x1);
						start_y1_text.setText(""+y1);
						end_x2_text.setText(""+x2);
						end_y2_text.setText(""+y2);

						frame_buffer_height_text.setVisible(false);
						frame_buffer_width_text.setVisible(false);
						default_params.setVisible(false);
						back_button.setVisible(true);
						start_x1_text.setVisible(true);
						start_y1_text.setVisible(true);
						end_x2_text.setVisible(true);
						end_y2_text.setVisible(true);
						frame_buffer.setVisible(false);
						start_end.setVisible(true);
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
		else if(state == START_END_POINT)
		{
			if(e.getActionCommand() == StartEndName)
			{
				try
				{
					x1 = Integer.parseInt(start_x1_text.getText());
					y1 = Integer.parseInt(start_y1_text.getText());
					x2 = Integer.parseInt(end_x2_text.getText());
					y2 = Integer.parseInt(end_y2_text.getText());

					if(!((x1 < 0 || x1 >= frameWidth) || (x2 < 0 || x2 >= frameWidth)
							|| (y1 < 0 || y1 >= frameHeight) || (y2 < 0 || y2 >= frameHeight)))
					{
						state = NEW_POINT;
						start_end.setVisible(false);
						default_params.setVisible(false);
						next_button.setVisible(true);
						previous_button.setVisible(true);
						back_button.setVisible(true);

						start_x1_text.setVisible(false);
						start_y1_text.setVisible(false);
						end_x2_text.setVisible(false);
						end_y2_text.setVisible(false);

						Point p1 = new Point(x1, y1);
						Point p2 = new Point(x2, y2);
						Line l = new Line(p1, p2);
						l.getSlopeConstant(slope_constant);

						fb = s.ScanConvert(l, fb);
						//fb.draw();

						selPoints = s.getSeqOrderOfSelPoints();
						notSelPoints = s.getSeqOrderOfNotSelPoints();

						itSel = selPoints.listIterator();
						itNotSel = notSelPoints.listIterator();
						selPoint = itSel.next();
						notSelPoint = itNotSel.next();
						fb.setPixel(selPoint.getX(), selPoint.getY(), 0.7f, 0.7f, 0.0f);
					}
					else
					{
						x1 = 2;
						y1 = 5;
						x2 = 9;
						y2 = 10;
					}
				}
				catch(NumberFormatException error)
				{
					x1 = 2;
					y1 = 5;
					x2 = 9;
					y2 = 10;
				}
				repaint();				
			}
		}
		else if(e.getActionCommand() == NextButtonName)
			goToNext();
		else if(e.getActionCommand() == PreviousButtonName)
			goToPrevious();
	}
}
