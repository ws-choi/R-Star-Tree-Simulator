package r.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import r.tree.RTree;

public class GUI_Tree extends JPanel implements Observer{

	private JLabel jLabel1;
	private JRadioButton Range_Delete;
	private JRadioButton Range_Query;
	private JRadioButton Add;
	private ButtonGroup Mode_Selection;
	private RTree tree;
	private Paint_Panel paint_Panel;
	

	public GUI_Tree(RTree tree) {		
		super();
		tree.addObserver(this);
		this.tree=tree;
		initGUI();
	}

	private void initGUI() {
		try {
			this.setBackground(new java.awt.Color(255,255,255));
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(400, 500));
			{
				paint_Panel = new Paint_Panel(tree);
				this.add(paint_Panel, BorderLayout.CENTER);
				paint_Panel.setBackground(new java.awt.Color(255,255,255));
			}
			{
				final JPanel jPanel1 = new JPanel();
				this.add(jPanel1, BorderLayout.NORTH);
				GridBagLayout jPanel1Layout = new GridBagLayout();
				jPanel1Layout.rowWeights = new double[] {0.1, 0.1, 0.1};
				jPanel1Layout.rowHeights = new int[] {7, 7, 7};
				jPanel1Layout.columnWeights = new double[] {0.1, 0.1, 0.1};
				jPanel1Layout.columnWidths = new int[] {7, 7, 7};
				jPanel1.setLayout(jPanel1Layout);
				jPanel1.setBackground(new java.awt.Color(238,237,234));
				jPanel1.add(getAdd(), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 15, 0, 0), 0, 0));
				jPanel1.add(getRange_Query(), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 15, 0, 0), 0, 0));
				jPanel1.add(getRange_Delete(), new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 15, 0, 0), 0, 0));
				jPanel1.add(getJLabel1(), new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

				{
					Mode_Selection = getMode_Selection();
					Mode_Selection.add(Add);
					Mode_Selection.add(Range_Query);
					Mode_Selection.add(Range_Delete);
					Add.setSelected(true);
					Add.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							paint_Panel.changeMode(Paint_Panel.ADD);
						}
					});
					Range_Query.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							paint_Panel.changeMode(Paint_Panel.QUERY);
						}
					});
					Range_Delete.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							paint_Panel.changeMode(Paint_Panel.DELETE);
						}
					});

					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private JRadioButton getAdd() {
		if(Add == null) {
			Add = new JRadioButton();
			Add.setText("Add Rectangle");
			Add.setBackground(new java.awt.Color(238,237,234));
		}
		return Add;
	}
	
	private JRadioButton getRange_Query() {
		if(Range_Query == null) {
			Range_Query = new JRadioButton();
			Range_Query.setText("Range Query");
			Range_Query.setBackground(new java.awt.Color(238,237,234));
		}
		return Range_Query;
	}
	
	private JRadioButton getRange_Delete() {
		if(Range_Delete == null) {
			Range_Delete = new JRadioButton();
			Range_Delete.setText("Range Delete");
			Range_Delete.setBackground(new java.awt.Color(238,237,234));
		}
		return Range_Delete;
	}
	
	private JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Input Manager");
			jLabel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jLabel1.setBackground(new java.awt.Color(238,237,234));
		}
		return jLabel1;
	}


	private ButtonGroup getMode_Selection() {
		if(Mode_Selection == null) {
			Mode_Selection = new ButtonGroup();
		}
		return Mode_Selection;
	}

	public void Change_MAX(int selected) {
		// TODO Auto-generated method stub
		
	}

	public void Change_MIN(Integer selectedItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
	
		repaint();
	}
	
}
