package r.gui;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import r.tree.Log;
import r.tree.RTree;

public class Log_viewer extends JScrollPane implements Observer{

	private JTextArea ta;
	private static final long serialVersionUID = 1L;
	private RTree tree;

	public Log_viewer(RTree tree) {
		super();
	
		this.tree = tree;
		tree.addObserver(this);		
		ta=new JTextArea();
		setViewportView(ta);

		this.setPreferredSize(new java.awt.Dimension(400, 100));
		
	}
	@Override
	public void update(Observable o, Object arg) {

		try {
			tree.getSem().acquire();
			if(arg instanceof Log)
			{
				Log log = (Log) arg;
		
				if(log.getMode() == Log.SET_TEXT )
					ta.setText(log.getLog());
				else if( log.getMode() == Log.APPEND_TEXT)
					ta.append(log.getLog());
				

				ta.paint(ta.getGraphics());
				tree.getSem().release();
					
			} 
			else
			{
				tree.getSem().release();
				return;
			}
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		

		
	}

	
}
