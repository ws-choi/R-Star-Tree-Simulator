package r.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

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
public class Tree_Controller extends javax.swing.JPanel {

	private GUI_Tree r_tree;
	
	private JPanel Name_Panel;
	private JLabel MINlabel;
	private JLabel MAXlabel;
	private JRadioButton QUADRATIC;
	private JComboBox MINChooser;
	private JComboBox MAXChooser;
	private JPanel CenterPanel;
	private JLabel Name_Panel1;
	private ButtonGroup TreeSelector;
	private ButtonGroup SplitSelector;
	private JRadioButton previous_Split;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/

	public Tree_Controller(GUI_Tree r_Tree) {
		super();
		r_tree = r_Tree;
		initGUI();
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
						Name_Panel1 = new JLabel("Environment Setting");
						Name_Panel.add(Name_Panel1, BorderLayout.CENTER);
						Name_Panel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
						Name_Panel1.setBackground(new java.awt.Color(243,243,243));
						Name_Panel1.setText("R*-Tree's Environment Setter");
					}
				}
				{
					CenterPanel = new JPanel();
					this.add(CenterPanel, BorderLayout.CENTER);
					GridBagLayout CenterPanelLayout = new GridBagLayout();
					CenterPanelLayout.rowWeights = new double[] {0.1, 0.1};
					CenterPanelLayout.rowHeights = new int[] {7, 7};
					CenterPanelLayout.columnWeights = new double[] {0.1, 0.2, 0.1};
					CenterPanelLayout.columnWidths = new int[] {7, 7, 7};
					CenterPanel.setLayout(CenterPanelLayout);
					CenterPanel.setPreferredSize(new java.awt.Dimension(400, 100));
					CenterPanel.setSize(400, 100);
					CenterPanel.setBackground(new java.awt.Color(238,237,234));
					{
						MAXlabel = new JLabel();
						CenterPanel.add(MAXlabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 7, 0, 0), 0, 0));
						MAXlabel.setText("MAX # of children");
						MAXlabel.setFont(new java.awt.Font("¸¼Àº °íµñ",1,12));
					}
					{
						MINlabel = new JLabel();
						CenterPanel.add(MINlabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 7, 0, 0), 0, 0));
						MINlabel.setText("MIN # of children");
						MINlabel.setFont(new java.awt.Font("¸¼Àº °íµñ",1,12));
						MINlabel.setPreferredSize(new java.awt.Dimension(400, 150));
						MINlabel.setSize(400, 150);
					}
					{
						ComboBoxModel MAXChooserModel = 
							new DefaultComboBoxModel(
									new Integer[] {4,5,6,7,8,9,10,11,12});
						MAXChooser = new JComboBox();
						CenterPanel.add(MAXChooser, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						MAXChooser.setModel(MAXChooserModel);
						MAXChooser.setBackground(Color.white);
						MAXChooser.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								JComboBox buf = (JComboBox)arg0.getSource();
								int selected = (Integer)buf.getSelectedItem();
								r_tree.Change_MAX(selected);
								Integer[] newArray = new Integer[(selected/2)-1];
								for(int i=2; i<=selected/2; i++) newArray[i-2]=i;
								ComboBoxModel newChooserModel = 
									new DefaultComboBoxModel(newArray);
								MINChooser.setModel(newChooserModel);
								MINChooser.setSelectedIndex(0);
								System.gc();
							}
						});
					}
					{
						ComboBoxModel MINChooserModel = 
							new DefaultComboBoxModel(
									new Integer[] {2,3,4});
						MINChooser = new JComboBox();
						CenterPanel.add(MINChooser, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						MINChooser.setModel(MINChooserModel);
						MINChooser.setBackground(Color.white);
						MINChooser.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								JComboBox buf = (JComboBox)arg0.getSource();
								r_tree.Change_MIN((Integer)buf.getSelectedItem());
							}
						});
					}
					
					TreeSelector = new ButtonGroup();

					SplitSelector = new ButtonGroup();
					previous_Split = QUADRATIC;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
