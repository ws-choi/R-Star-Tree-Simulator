package r.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Hashtable;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import r.graphics.Image_Loader;
import r.graphics.Sprite;
import r.graphics.Sprite_thread;
import r.graphics.myJPanel;
import r.tree.Log;
import r.tree.RDirNode;
import r.tree.RLeafNode;
import r.tree.RNode;
import r.tree.RTree;
import r.tree.data.RData;

public class Visualized_Tree extends myJPanel implements Observer{

	private static final long serialVersionUID = 20L;
	RTree tree;
	Map<Integer, Integer> map_traverse;
	Map<Integer, Integer> map_draw;
	Image_Loader il;
	private int delay;
	
	private int max;
	private int margin;
	private int first_y;
	
	public Visualized_Tree(final RTree tree) {
		this.tree = tree;
		tree.addObserver(this);
		setPreferredSize(new Dimension(400, 400));
		map_traverse = new Hashtable<Integer, Integer>();
		map_draw = new Hashtable<Integer, Integer>();
		
		il = new Image_Loader();
		init();
		
		
		Runnable painter = new Runnable() {
			
			@Override
			public void run() {
				
				while (true) {
					
					try {
						Thread.sleep(100);
						set_vertex_pos();
						repaint();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		new Thread(painter).start();
	
	
	
/*		Timer timer = new Timer(10, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				set_vertex_pos();
				repaint();
			}
		});
	timer.start();*/
	}
	
	public void init() {
		tree.notifyObservers();
		setDelay(100);
		max=margin= 50;
		first_y=30;
	}


	@Override
	public void update(Observable o, Object arg) {

		if(arg instanceof Integer)
		{
			setDelay((Integer)arg);
			return;
		}
				
		if(tree.getPinned()!= null)
			new Thread(new Sprite_thread
					(this, tree.getPinned(), tree.getSem(), delay)).start();
	


		if(tree.getNew_node_pinned()!= null)
			new Thread(new Sprite_thread
					(this, tree.getNew_node_pinned(),
							tree.getSem(), delay)).start();
			
		
	}


		private void setDelay(int arg) {
			delay=arg;
	}
		
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw_lines(g);
		draw_Vertex(g);
		draw_Pinned(g, tree.getPinned());
		if(tree.getNew_node_pinned()!=null)
		{
			draw_lines(g, tree.getNew_node_pinned());
			draw_Vertex(g, tree.getNew_node_pinned());
		}
	}
	private void draw_Vertex (Graphics g)
	{
		g.setColor(Color.black);
		draw_Vertex(g, tree.getRoot());	
	}
	private void draw_Vertex (Graphics g, RNode node)
	{
		Image img = node.isMarked() ? Image_Loader.Selected_Node[node.getSize()]
				: Image_Loader.Node[node.getSize()];
		g.drawImage(img, node.get_Point().x- (img.getWidth(this)/2),
				node.get_Point().y -((img.getHeight(this)/2)), this);
		g.drawString("ID: "+node.id, node.get_Point().x-10, node.get_Point().y-10);
		//System.out.println(node.get_Point());
		
		if(node.isLeafNode())
		{
			for(int i=0; i<node.getSize(); i++)
				draw_Data(g, (RData)node.getEntries()[i]);
			return;
		}
		if(!node.isEmpty())
			for(int i=0; i<node.getSize(); i++)
				draw_Vertex(g, (RNode)node.getEntries()[i]);

	}
	
	private void draw_Data (Graphics g, RData Data)
	{
		Image img = Data.isMarked() ? il.Selected_Data : il.Data ;
		g.drawImage(img, Data.get_Point().x- (img.getWidth(this)/2),
				Data.get_Point().y -((img.getHeight(this)/2)), this);
		
		g.drawString("ID:"+Data.id, Data.get_Point().x-15, Data.get_Point().y+5);	
	}
	
				private void draw_lines (Graphics g)
				{
					draw_lines(g, tree.getRoot());
				}
				
				private void draw_lines (Graphics g, RNode node)
				{
					for(int i=0; i<node.getSize(); i++)
					{
						Sprite child = (Sprite)node.getEntries()[i];
						g.drawLine(node.get_Point().x, node.get_Point().y,
								child.get_Point().x, child.get_Point().y);
						
						if(!node.getEntries()[i].isDataEntry())
							draw_lines(g,(RNode)node.getEntries()[i] );
					}
				}
		

		private void draw_Pinned (Graphics g, Sprite s)
		{
			if(s==null) return;
			g.drawImage(s.get_Img(), s.get_Point().x- (s.get_Img().getWidth(this)/2),
					s.get_Point().y -((s.get_Img().getHeight(this)/2)), this);
		}
		
	private void set_vertex_pos ()
	{
		max=50;
		map_draw.clear();
		set_vertex_pos(tree.getRoot(), 0);
		setPreferredSize(new Dimension(map_draw.get(0)*2, 400));
		revalidate();
	}
	
	private void set_vertex_pos (RNode node, int level)
	{
		int this_level = level;
		
		if(node instanceof RDirNode)
		{
			for(int i = 0; i<node.getSize() ; i++)
				set_vertex_pos ((RNode)node.getEntries()[i], this_level+1);
		}
		
		else if (node instanceof RLeafNode)
		{
			int first_pos= max + margin;

			for(int i = 0; i<node.getSize() ; i++)
				node.getEntries()[i].Locate(first_pos+i*margin, 
						first_y+(level+1)*margin);
			
			set_pos_parent(node, first_y+level*margin);
			
			if(max < get_max_x(node)) max = get_max_x(node);
			
		
		}
		
		
		set_pos_parent(node, first_y+level*margin);
		
		int currunt_x= node.get_Point().x;
		int currunt_y =node.get_Point().y;
		
		int max_x_in_this_level;
		if(!map_draw.containsKey(level))
		{
			map_draw.put(level, currunt_x);
			return;
		}
		
		max_x_in_this_level = map_draw.get(level);
		

		if(currunt_x - max_x_in_this_level < il.Node[0].getWidth(this))
		{
			currunt_x=currunt_x-max_x_in_this_level-
					(il.Node[0].getWidth(this))/2;
			migrate_recursive ( currunt_x, currunt_y, node);
		}

		map_draw.put(level, currunt_x);
	}
	
	
	private void set_pos_parent(RNode node, int y) {
		int x=0;
		for(int i=0; i< node.getSize(); i++)
		{
			x+=node.getEntries()[i].get_Point().x;
		}
		
		if(!node.isEmpty())
			node.Locate(x/node.getSize(), y);
		else
			node.Locate(getWidth()/2, y);
			
	}
	
	private int get_max_x(RNode node) {
		int max = node.get_Point().x + (il.Node[0].getWidth(this))/2;
		if(node.isEmpty()) return max;

		if(node.isLeafNode())
		{
			int x =node.getEntries()[node.getSize()-1].get_Point().x
					+il.Data.getWidth(this)/2;
			return (max> x) ? max : x;
		
		}
		
		else
			return get_max_x((RNode)node.getEntries()[node.getSize()-1]);
	}
	
	private void migrate_recursive(int x, int  y, RNode node) {

		node.migrate(x, y);
		if(node.isLeafNode())
			for(int i=0; i<node.getSize(); i++)
				node.getEntries()[i].migrate(x, y);
		
		else	
			for(int i=0; i<node.getSize(); i++)
				migrate_recursive(x, y, (RNode)node.getEntries()[i]);
	}


	public int getDelay() {
		return delay;
	}
}

