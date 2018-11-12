package r.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
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
public class Simul_Controller extends javax.swing.JPanel implements Observer  {


	private JPanel Name_Panel;
	private JButton Play;
	private JButton Redo;
	private JComboBox Delay_Chooser;
	private JLabel Ani_Delay;
	private JButton Undo;
	private JButton anjwl;
	private JLabel Animation;
	private JPanel CenterPanel;
	private JLabel Name_Panel1;
	private RTree rtree;


	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new Simul_Controller(null));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public Simul_Controller(RTree tree) {
		super();
		rtree= tree;
		initGUI();
		rtree.addObserver(this);

	}

	public void select_tree (int tree_Type) {
	}
	
	private void initGUI() {
		try {
			{
				BorderLayout thisLayout = new BorderLayout();
				this.setPreferredSize(new java.awt.Dimension(400, 140));
				this.setLayout(thisLayout);
				this.setBackground(new java.awt.Color(238,237,234));
				this.setSize(400, 140);
				{
					Name_Panel = new JPanel();
					BorderLayout Name_PanelLayout = new BorderLayout();
					Name_Panel.setLayout(Name_PanelLayout);
					this.add(Name_Panel, BorderLayout.NORTH);
					Name_Panel.setBackground(Color.white);
					{
						Name_Panel1 = new JLabel("Simulator Setting");
						Name_Panel.add(Name_Panel1, BorderLayout.CENTER);
						Name_Panel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
						Name_Panel1.setBackground(new java.awt.Color(243,243,243));
						Name_Panel1.setText("Simulator Setter");
					}
				}
				{
					CenterPanel = new JPanel();
					this.add(CenterPanel, BorderLayout.CENTER);
					GridBagLayout CenterPanelLayout = new GridBagLayout();
					CenterPanelLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
					CenterPanelLayout.rowHeights = new int[] {7, 7, 7, 7, 7, 7, 7};
					CenterPanelLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1};
					CenterPanelLayout.columnWidths = new int[] {7, 7, 7, 7, 7};
					CenterPanel.setLayout(CenterPanelLayout);
					CenterPanel.setPreferredSize(new java.awt.Dimension(400, 100));
					CenterPanel.setSize(400, 100);
					CenterPanel.setBackground(new java.awt.Color(238,237,234));
					{
						Animation = new JLabel();
						CenterPanel.add(Animation, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 7, 0, 0), 0, 0));
						Animation.setText("Animation");
						Animation.setFont(new java.awt.Font("¸¼Àº °íµñ",1,12));
					}
					{
						Play = new JButton();
						CenterPanel.add(Play, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
						Play.setText("\u25b7");
					}
					{
						anjwl = new JButton();
						CenterPanel.add(anjwl, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
						anjwl.setText("\u25a1");
						
						//				javax.swing.Timer timer = new javax.swing.Timer(arg0, arg1)

						anjwl.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								if(rtree.isStop())
								{
									anjwl.setText("\u25a1");
									rtree.setStop(false);
								}
								
								else
								{
									anjwl.setText("\u25b7");
									rtree.setStop(true);
								}
							}
						});
					}
					
					{
						Undo = new JButton();
						CenterPanel.add(Undo, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
						Undo.setText("UNDO");
						Undo.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								rtree.undo();
								Undo.setEnabled(false);
								Redo.setEnabled(true);
							}
						});
					}
					{
						Redo = new JButton();
						CenterPanel.add(Redo, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
						Redo.setText("REDO");
						Redo.setEnabled(false);
						Redo.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								rtree.redo();
								Undo.setEnabled(true);
								Redo.setEnabled(false);
							}
						});
					}
					{
						Ani_Delay = new JLabel();
						CenterPanel.add(Ani_Delay, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 7, 0, 0), 0, 0));
						Ani_Delay.setText("Animation Delay");
						Ani_Delay.setFont(new java.awt.Font("¸¼Àº °íµñ",1,12));
					}
					{
						ComboBoxModel Delay_ChooserModel = 
							new DefaultComboBoxModel(
									new Integer[] {100, 200, 300, 400, 500,0 });
						Delay_Chooser = new JComboBox();
						CenterPanel.add(Delay_Chooser, new GridBagConstraints(3, 4, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
						Delay_Chooser.setModel(Delay_ChooserModel);
						Delay_Chooser.addItemListener(new ItemListener() {
							
							@Override
							public void itemStateChanged(ItemEvent e) {
								rtree.setDelay((Integer)e.getItem());
							}
						});
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		if(rtree.isUndoable())
		{
			Redo.setEnabled(false);
			Undo.setEnabled(true);			
		}
		
	}
}


class StopWorker extends SwingWorker<Integer, Integer>
{
	@Override
	protected Integer doInBackground() throws Exception {
		Thread.sleep(100);
		return null;
	}
}