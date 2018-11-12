package r.gui;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

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
public class App_for_User extends javax.swing.JFrame {

	{
		
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	private RTree rtree;
	private GUI_Tree gui_tree;
	private Simulated_Tree Simulated_Tree;
	private Log_viewer TextViewer;
	private Simul_Controller Simul_Controller;
	private Tree_Controller Tree_Controller;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				App_for_User inst = new App_for_User();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	
	
	
	
	public App_for_User() {
		super();
		rtree= new RTree();
		initGUI(); 
		rtree.msg_log("new R*-Tree Created!");
		
		
	}
	
	private void initGUI() {

		try {
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			this.setTitle("R-tree/R*tree Simulator");
			GridBagLayout thisLayout = new GridBagLayout();
			getContentPane().setBackground(new java.awt.Color(255,255,255));
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {7, 7, 7};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
			thisLayout.columnWidths = new int[] {7, 7, 7, 7};
			getContentPane().setLayout(thisLayout);
//			this.setResizable(false);
			{
				Simulated_Tree = new Simulated_Tree(rtree);
				getContentPane().add(Simulated_Tree, new GridBagConstraints(2, 0, 2, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				Simulated_Tree.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
			}
			{
				gui_tree = new GUI_Tree(rtree);
				getContentPane().add(gui_tree, new GridBagConstraints(0, 0, 2, 2, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				gui_tree.setBackground(new java.awt.Color(238,237,234));
				gui_tree.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));

			}
			{
				Tree_Controller = new Tree_Controller(gui_tree);
				getContentPane().add(Tree_Controller, new GridBagConstraints(0, 4, 2, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				Tree_Controller.setPreferredSize(new java.awt.Dimension(400, 150));
				Tree_Controller.setSize(400, 150);
				Tree_Controller.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				Tree_Controller.setBackground(new java.awt.Color(255,255,255));


			}
			{
				Simul_Controller = new Simul_Controller(rtree);
				getContentPane().add(Simul_Controller, new GridBagConstraints(2, 4, 2, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
			
			{
				TextViewer = new Log_viewer(rtree);
				getContentPane().add(TextViewer, new GridBagConstraints(0, 2, 4, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				TextViewer.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
				TextViewer.setBackground(new java.awt.Color(255,255,255));
			}
			pack();
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
