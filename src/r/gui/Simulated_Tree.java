package r.gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import r.tree.RTree;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class Simulated_Tree extends javax.swing.JPanel implements Observer {
	
	private JPanel jPanel1;
	private JScrollPane jScrollPane1;
	public Visualized_Tree Visualized_Tree;
	private JLabel Panel_Name;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/

	
	public Simulated_Tree(RTree tree) {
		super();
		initGUI(tree);
		tree.addObserver(this);
			
	}
	
	private void initGUI(RTree tree) {
		
		try {
			{
				BorderLayout thisLayout = new BorderLayout();
				this.setLayout(thisLayout);
				this.setPreferredSize(new java.awt.Dimension(400, 500));
				this.setBackground(new java.awt.Color(255,255,255));
				{
					jPanel1 = new JPanel();
					FlowLayout jPanel1Layout = new FlowLayout();
					jPanel1Layout.setAlignment(FlowLayout.LEFT);
					jPanel1.setLayout(jPanel1Layout);
					this.add(jPanel1, BorderLayout.NORTH);
					jPanel1.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
					jPanel1.setPreferredSize(new java.awt.Dimension(400, 24));
					{
						Panel_Name = new JLabel();
						jPanel1.add(Panel_Name);
						Panel_Name.setText("R*-tree's Tree Viewer");
						Panel_Name.setPreferredSize(new java.awt.Dimension(138, 11));
					}
				}
				{
					jScrollPane1 = new JScrollPane();
					this.add(jScrollPane1, BorderLayout.CENTER);
					jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 476));
					{
						Visualized_Tree = new Visualized_Tree(tree);
						jScrollPane1.setViewportView(Visualized_Tree);
						Visualized_Tree.setPreferredSize(new java.awt.Dimension(400, 458));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}
	
}
