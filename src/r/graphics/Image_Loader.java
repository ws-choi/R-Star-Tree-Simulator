package r.graphics;

import java.awt.Image;
import java.awt.Toolkit;

public class Image_Loader {

	static public  Image Node[];
	static public  Image Selected_Node[];
	
	static public  Image Data;
	static public  Image Selected_Data;
	static public  Image Query;	
	
	static public  int node_empty = 0;
	static public  int node_1 = 1;
	static public  int node_2 = 2;
	static public  int node_3 = 3;
	static public  int node_4 = 4;

	public Image_Loader() {
		
	Node = new Image[5];
		
		Node[node_empty] = Toolkit.getDefaultToolkit().getImage("images/yellow_0.png");
		Node[node_1] = Toolkit.getDefaultToolkit().getImage("images/yellow_1.png");
		Node[node_2] = Toolkit.getDefaultToolkit().getImage("images/yellow_2.png");
		Node[node_3] = Toolkit.getDefaultToolkit().getImage("images/yellow_3.png");
		Node[node_4] = Toolkit.getDefaultToolkit().getImage("images/yellow_4.png");
	
		
		Selected_Node = new Image[5];
		
		Selected_Node[node_empty] = Toolkit.getDefaultToolkit().getImage("images/selected_0.png");
		Selected_Node[node_1] = Toolkit.getDefaultToolkit().getImage("images/selected_1.png");
		Selected_Node[node_2] = Toolkit.getDefaultToolkit().getImage("images/selected_2.png");
		Selected_Node[node_3] = Toolkit.getDefaultToolkit().getImage("images/selected_3.png");
		Selected_Node[node_4] = Toolkit.getDefaultToolkit().getImage("images/selected_4.png");
	
		Data = Toolkit.getDefaultToolkit().getImage("images/yellow_data.png");
		Selected_Data = Toolkit.getDefaultToolkit().getImage("images/red_data.png");
		Query = Toolkit.getDefaultToolkit().getImage("images/Query.png");
		
	}
}
