package r.global;

import r.tree.data.REntry;
import r.tree.data.Rect;


public class Constants {

	//Global Variables
	public static int Dimension=2;
	
	//Global Method
	public static void sync() {}
	
	/*GUI*/
	//-paint panel
	public static int depth=0;
	
	/*GUI*/	
	
	public static int MAX_ENTRIES_IN_NODE=4;
	public static int MIN_ENTRIES_IN_NODE=2;
	public static int N_LastID=1;
	public static int D_LastId=1;
	
	//for Debug
	public static final boolean Trace_Rtree = true;
	public static final boolean Trace_Node = true;
	
	
	//Rtree Mode
	public static final String[] Tree_Mode = {"RTree_Mode", "RStarTree_Mode"};
	public static final int RTree_Mode = 0;
	public static final int RStarTree_Mode = 1;
	public static final int Default_Mode = RStarTree_Mode;	

	//Split Method
	public static final String[] Split_Mode = {"EXHAUSTIVE_SPLIT", "QUADRATIC_SPLIT", "RSTAR_SPLIT"};
	public static final int EXHAUSTIVE_SPLIT=0;
	public static final int QUADRATIC_SPLIT=1;
	public static final int RSTAR_SPLIT=2;
	public static final int Default_Split_Mode = QUADRATIC_SPLIT;	
	public static int CURRENT_SPLIT_MODE= Default_Split_Mode;


	
	//Sort
	
	public static final String[] Sort_axis= {"Sort_by_x","Sort_by_y" };
	public static final int SORT_BY_X = 0;
	public static final int SORT_BY_Y = 1;
	public static final int SORT_BY_UPPER = 0;
	public static final int SORT_BY_LOWER = 1;
	
	
	public static int[] nullVector = new int[Dimension*2];
	public static REntry Orphan; 
	public static final Rect nullRect = new Rect(nullVector);
	
	
	

}
