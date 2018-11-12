package r.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import r.tree.RNode;
import r.tree.RTree;
import r.tree.data.REntry;
import r.tree.data.Rect;

public class Paint_Panel extends JPanel 
implements MouseMotionListener, MouseListener, Observer{

	private static final long serialVersionUID = 8820407529947469553L;

	private RTree rtree;
	
	int x1, y1, x2, y2, w,h ,x, y;
	private boolean isNewRect;

	int Mode;
	private boolean isIdVisible;
	
	static final int ADD = 1;
	static final int QUERY = 2;
	static final int DELETE = 3;
	
	public Paint_Panel(RTree r_Tree2) {

		super();
		initGUI();
			
		//ASSOSIATION
		rtree = r_Tree2;
		//INIT
		x1=y1=x2=y2=w=h=0;
		isNewRect = true;
		Mode = ADD;
		
		setBackground(Color.white);
		setIdVisible(false);
		rtree.addObserver(this);
		
		Runnable painter = new Runnable() {
			
			@Override
			public void run() {
				
				while (true) {
					
					try {
						Thread.sleep(50);
						repaint();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}		
		};

		new Thread(painter).start();
	
	}
		
	
	private void initGUI() {
		try {
			{
				this.addMouseMotionListener(this);
			}
			{
				this.addMouseListener(this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeMode (int newMode)
	{Mode=newMode;}
	public void add_rect (Rect rect) 
	{rtree.add_rect(rect); repaint();}
	public void range_query (Rect rect) 
	{rtree.range_query(rect); repaint();}
	public void range_delete (Rect rect) 
	{rtree.range_delete(rect); repaint();}
	
	//implements
	public void mouseMoved(MouseEvent arg0) 
	{
		if(Mode!=ADD) return;
		Point cusor = arg0.getPoint();
		detect_rect(rtree.getRoot(), cusor);
	
		x = arg0.getX();
		y = arg0.getY();
		

		repaint();
	}
	
	private void detect_rect (REntry entry, Point cusor)
	{
		Rect rectangle = entry.getRectangle();
		
			
		if(entry.isDataEntry()) {
			
			if(rectangle.intersect(cusor))
				rectangle.peek=true;
			else
				rectangle.peek=false;
			return;
		}
		
		RNode node = (RNode)entry;
		
		if(!node.isEmpty())
			for(int i=0; i<node.getSize(); i++)
				detect_rect(node.getEntries()[i], cusor);

		if(rectangle.intersect(cusor))
			rectangle.peek=true;
		else
			rectangle.peek=false;
		
	}
	
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		this.x1 = arg0.getX();
		this.y1 = arg0.getY();
		repaint(); 
	}
	
	private int[] get_rect_vector ()
	{
		int[] vector = new int[4];
		
		int width = this.x1 - this.x2; 
		int height = this.y1 - this.y2; 

		vector[0] = width < 0 ? this.x1 : this.x2;
		vector[1] = Math.abs( width );
		vector[2] = height < 0 ? this.y1 : this.y2;
		vector[3] = Math.abs( height );
		
		return vector;
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		this.x2 = arg0.getX();
		this.y2 = arg0.getY();
		
		Rect rect = 	new Rect(get_rect_vector());

		switch (Mode) {
		case ADD:	add_rect(rect);break;
		case QUERY:	range_query(rect);break;
		case DELETE:range_delete(rect);break;
		default:break;
		}
	
		this.w = this.h = this.x1 = this.y1 = this.x2 = this.y2 = 0; 
		this.isNewRect = true; 
		
		repaint(); 
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		this.x2 = arg0.getX();
		this.y2 = arg0.getY(); 
		this.isNewRect = false;
		repaint(); 
	}
	
	@Override 
	public void paint( final Graphics g ) { 
		super.paint( g ); // clear the frame surface 
		
		switch (Mode) {
			case ADD :
				g.drawString( "Add this" , this.x1, this.y1 );		
				break;

			case QUERY :
				g.drawString( "Range Query", this.x1, this.y1 );		
				break;

			case DELETE :
				g.drawString( "Range Delete", this.x1, this.y1 );		
				break;

			default :
				break;
		}
		

		if ( !this.isNewRect ) { 
			if(Mode==QUERY) g.setColor(Color.GREEN);
			if(Mode==ADD) g.setColor(Color.BLUE);
			if(Mode==DELETE) g.setColor(Color.RED);
			
			int width = this.x1 - this.x2; 
			int height = this.y1 - this.y2; 

			g.drawRect(width < 0 ? this.x1 
					: this.x2, height < 0 ? this.y1 
							: this.y2, Math.abs( width ), Math.abs( height )); 
			
		} 
		else
			g.drawString("(" + x + ',' +y+')', x, y);

		
				
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw_Tree(g, false);
		draw_Tree(g, true);
		
	}
	private void draw_Rects(Graphics g, Rect rectangle, Color color) {
		g.setColor(color);
		g.drawRect( rectangle.get_min(0), rectangle.get_min(1), 
				rectangle.get_Dist(0), rectangle.get_Dist(1));
	}

	private void draw_Tree (Graphics g, boolean peek_only)
	{
		draw_child(g, rtree.getRoot(), peek_only);	
	}
	
	private void draw_child (Graphics g, REntry entry, boolean peek_only)
	{
		if(!entry.isDataEntry())
		{
			RNode node = (RNode)entry;
			
			if(!node.isEmpty())
				for(int i=0; i<node.getSize(); i++)
					draw_child(g, node.getEntries()[i], peek_only);
		}
		
		Rect rectangle = entry.getRectangle();

		Color color = rectangle.peek ? Color.red : Color.black;
		
		if(!peek_only)
			draw_Rects(g, rectangle, color);
		else
			if(rectangle.peek)	
				draw_Rects(g, rectangle, color);
			
				
		if(isIdVisible || (!isIdVisible && entry.getRectangle().peek)) 
			if(entry.isDataEntry())
				g.drawString(entry.getID(), rectangle.get_min(0),rectangle.get_min(1));
		
			else if(entry.isLeafNode())
				g.drawString(entry.getID(), rectangle.get_max(0),rectangle.get_min(1));
			else	
				g.drawString(entry.getID(), rectangle.get_max(0),rectangle.get_max(1));
		
		


	}
	public boolean isIdVisible() {
		return isIdVisible;
	}
	public void setIdVisible(boolean isIdVisible) {
		this.isIdVisible = isIdVisible;
	}
	@Override
	public void update(Observable o, Object arg) {
		try {
			rtree.getSem().acquire();
			paint(getGraphics());
			rtree.getSem().release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

