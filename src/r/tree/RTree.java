/**
 *�ֿ켺 
 */

package r.tree;
import java.util.Observable;
import java.util.concurrent.Semaphore;

import r.global.Constants;
import r.graphics.Image_Loader;
import r.graphics.Sprite;
import r.tree.data.RData;
import r.tree.data.REntry;
import r.tree.data.REntry_List;
import r.tree.data.RQuery;
import r.tree.data.Rect;
import r.tree.data.Rect_List;

public class RTree extends Observable {

	private RNode root, undoTree;
	private REntry pinned;
	private RNode split_node_pinned, new_node_pinned, current_node_pinned;
	private Image_Loader image_loader;

	private boolean isStop;
	private boolean undoable;
	private Semaphore sem;
	
	private Log log;
	
	public RTree() {

		image_loader = new Image_Loader();
		root = new RLeafNode(null);
		root.set_Img(Image_Loader.Node[Image_Loader.node_empty]);
		setStop(false);
		setUndoable(true);
		
		sem = new Semaphore(10);

	}

	private void tree_backup ()
	{
		undoTree = root.copy_subtree();
	}
	

	
	//Operation
	public synchronized void add_rect (Rect rect) 
	{
		tree_backup();
		unmark_All();
		clr_log();		
		
		/*For the First Step*/
		RData data = new RData(rect);
		data.set_Img(Image_Loader.Data);
		setPinned(data);
		getPinned().Locate(root.get_Point().x,0); 
		getPinned().set_destination(root);
		msg_log("ADD_Rectangle: " + rect);
		/* Choose Leaf */
		
		RLeafNode leaf = ChooseLeaf(root, data);
		
		if(leaf == null)
		{
			return ;
		}
		else
		{
			if(leaf.isFool())
			{
				msg_log("\tLeaf is Fool, has to be splitted.");
				Constants.Orphan=data;
				RNode split_node = leaf;
				REntry entry = data;
				do
				{
					RNode new_node=Rstar_split(split_node,entry);
					
					/*ANIMATION*/
					fresh_Pinned();
					
					/*ANIMATION*/
					new_node.Locate(split_node.get_Point());
					new_node.set_destination(split_node.get_Point().x+150, split_node.get_Point().y);
					split_node_pinned=split_node;
					new_node_pinned=new_node;
					msg_log("\tNew Node (ID: " + new_node.getID() +") Created!" );
					
					if(split_node.Parent == null) //Root Split
					{
						/*ANIMATION : Split Root*/ 
						RNode newRoot = new RDirNode(null);
						newRoot.Locate(root.get_Point().x, 0);
						msg_log("\tNew Root (ID: " + newRoot.getID() +") Created!" );
							
						newRoot.push_entry(split_node);
						newRoot.push_entry(new_node);
						
						
						root=newRoot; //Change Root
						root.adjust(); //adjust Root

						update();
						fresh_Pinned();
						
						split_node=null;
					}
					
					else{
						if(!split_node.Parent.isFool())
						{
							split_node.Parent.push_entry(new_node);
							split_node.adjust();
							split_node=null;
						}
						else
						{
							split_node = split_node.Parent;
							entry = new_node;
						}
						
					}
					fresh_Pinned();
					
				}while(split_node!=null);
			}
			
			else{
				leaf.push_entry(data);
				leaf.adjust();
				fresh_Pinned();
			}
				
			
		}
		
		setUndoable(true);

		msg_log("\t"+data+" is added.");
	}
	

	private RNode Rstar_split(RNode split_node, REntry entry) {
		
		msg_log("\t\t"+split_node.getID() + "is being splitted.");

		REntry_List sort_helper = new REntry_List(split_node.getEntries());
		sort_helper.add(entry);

		///		"perpendicular to which the split is performed." );
		int split_axis = choose_split_axis(sort_helper);
		
		//println_If_Traceable(Constants.Sort_axis[split_axis] +" is selected");
		
		
		//println_If_Traceable("Invoke ChooseSplitIndex to determin the best" +
		//		"distribution into two groups along that axis");
		
		REntry[] lower_sorted = sort_helper.getSorted(split_axis, Constants.SORT_BY_LOWER);
		REntry[] upper_sorted = sort_helper.getSorted(split_axis, Constants.SORT_BY_UPPER);
		
		Split_Index_Upper_Lower result = 
				choose_split_index(lower_sorted,upper_sorted);
		
		//println_If_Traceable("result: " + (result.is_upper ? "Upper, " : "Lower, ")
		//		+"Split index: " + result.split_index);
		
		//Distribute the entreis into twho groups
		
		split_node.remove_all();

		RNode new_Node;
		
		if(split_node.isLeafNode())
			new_Node = new RLeafNode(split_node.Parent);
		else
			new_Node = new RDirNode(split_node.Parent);
		
		for(int i=0; i<result.split_index; i++)
			split_node.push_entry((result.is_upper? upper_sorted[i] : lower_sorted[i]));
		
		for(int i=result.split_index; i<sort_helper.size(); i++)
			new_Node.push_entry((result.is_upper? upper_sorted[i] : lower_sorted[i]));
		
		split_node.adjust();
		new_Node.adjust();
		
		return new_Node;
		
	}
	
	private int choose_split_axis (REntry_List Sorter)
	{

		int x_total_cost = gen_cost_for_axis_upper_and_lower(Sorter, Constants.SORT_BY_X);
		int y_total_cost = gen_cost_for_axis_upper_and_lower(Sorter, Constants.SORT_BY_Y);
		
		//println_If_Traceable("Total Cost for this axis is " + y_total_cost);
		
		return (x_total_cost<y_total_cost)? Constants.SORT_BY_X : Constants.SORT_BY_Y;
	}
	
		private int gen_cost_for_axis_upper_and_lower (REntry_List Sorter, int axis)
		{
			int total_cost = 0;
			
			//For Upper
			REntry[] sorted = Sorter.getSorted(axis, Constants.SORT_BY_UPPER);
			total_cost += gen_cost_for_axis(sorted);
			
			//For Lower
			sorted = Sorter.getSorted(axis, Constants.SORT_BY_LOWER);
			total_cost += gen_cost_for_axis(sorted);
			
			return total_cost;
			
		}
	
		private int gen_cost_for_axis (REntry[] sorted)
		{
			Rect_List First_Gruoup = new Rect_List();
			Rect_List Second_Gruoup = new Rect_List();
			
			int total_cost=0;
			for(int i=Constants.MIN_ENTRIES_IN_NODE; 
					i<Constants.MAX_ENTRIES_IN_NODE - Constants.MIN_ENTRIES_IN_NODE+2; i++)
			{
				First_Gruoup.clear();
				Second_Gruoup.clear();
				
				for(int j=0; j<i; j++)
					First_Gruoup.add(sorted[j].getRectangle());
				
				for(int j=i; j<sorted.length; j++)
					Second_Gruoup.add(sorted[j].getRectangle());

				Rect First_MBR = First_Gruoup.generateMBR();
				Rect Second_MBR = Second_Gruoup.generateMBR();
				
				//println_If_Traceable("First Group's MBR is " + First_MBR);
				//println_If_Traceable("Second Group's MBR is " + Second_MBR);
				
				int cost = 2*(First_MBR.get_Dist(0) + First_MBR.get_Dist(1))
						+2*(Second_MBR.get_Dist(0) + Second_MBR.get_Dist(1));
					
				//println_If_Traceable("This Distribution's margin cost is :" + cost);
				
				total_cost+=cost;
			}
			return total_cost;
		}
		
	private Split_Index_Upper_Lower choose_split_index (REntry[] lower_sorted, REntry[] upper_sorted)
	{
		int min_over=Integer.MAX_VALUE;
		int min_area=Integer.MAX_VALUE;
		int min_split_index=-1;
		boolean is_upper = false;
		
		Rect_List First_Gruoup = new Rect_List();
		Rect_List Second_Gruoup = new Rect_List();
		
		
		//Lower
		for(int i=Constants.MIN_ENTRIES_IN_NODE; 
				i<Constants.MAX_ENTRIES_IN_NODE - Constants.MIN_ENTRIES_IN_NODE+2; i++)
		{
			First_Gruoup.clear();
			Second_Gruoup.clear();
		
			for(int j=0; j<i; j++)
				First_Gruoup.add(lower_sorted[j].getRectangle());
			
			for(int j=i; j<lower_sorted.length; j++)
				Second_Gruoup.add(lower_sorted[j].getRectangle());
			
			int overlap_cost = First_Gruoup.generateMBR().
					get_Overlap(Second_Gruoup.generateMBR());
			
			int area_cost = First_Gruoup.generateMBR().get_area()
					+ Second_Gruoup.generateMBR().get_area();
			
			//println_If_Traceable("(Lower) index i: "+ i+"\n\toverlap_cost is "+ overlap_cost
			//+"area_cost is " + area_cost);
			if(min_over > overlap_cost)
			{
				min_split_index=i;
				min_over=overlap_cost;
				min_area=area_cost;
			}
			else if (min_over == overlap_cost)
				if(min_area > area_cost)
				{
					min_split_index=i;
					min_over=overlap_cost;
					min_area=area_cost;
				}
		}

		//Upper
		for(int i=Constants.MIN_ENTRIES_IN_NODE; 
				i<Constants.MAX_ENTRIES_IN_NODE - Constants.MIN_ENTRIES_IN_NODE+2; i++)
		{
			First_Gruoup.clear();
			Second_Gruoup.clear();
		
			for(int j=0; j<i; j++)
				First_Gruoup.add(upper_sorted[j].getRectangle());
			
			for(int j=i; j<upper_sorted.length; j++)
				Second_Gruoup.add(upper_sorted[j].getRectangle());
			
			int overlap_cost = First_Gruoup.generateMBR().
					get_Overlap(Second_Gruoup.generateMBR());
			
			int area_cost = First_Gruoup.generateMBR().get_area()
					+ Second_Gruoup.generateMBR().get_area();
	

			if(min_over > overlap_cost)
			{
				min_split_index=i;
				min_over=overlap_cost;
				min_area=area_cost;
				is_upper=true;
			}
			else if (min_over == overlap_cost)
				if(min_area > area_cost)
				{
					min_split_index=i;
					min_over=overlap_cost;
					min_area=area_cost;
					is_upper=true;
				}
		}
		
		if(is_upper)
			return new Split_Index_Upper_Lower(true, min_split_index);
		else
			return new Split_Index_Upper_Lower(false, min_split_index);
	}

	
	private synchronized void fresh_Pinned ()
	{
		pinned=null;
		new_node_pinned=null;
		split_node_pinned=null;
		current_node_pinned=null;

		update();
	}
	
	private void update()
	{
		super.setChanged();
		notifyObservers();
		
		while(true){
			try {
				Thread.sleep(100);
				if(sem.availablePermits() == 10) 
				{
					Thread.sleep(100);
					break;
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private void update(Object args)
	{
		super.setChanged();
		notifyObservers(args);
		
		while(true){
			try {
				Thread.sleep(100);
				if(sem.availablePermits() == 10) 
				{
					Thread.sleep(100);
					break;
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void msg_log (String str)
	{
		if(log==null) log=new Log();
		log.setMode(Log.APPEND_TEXT);
		log.setLog(str+'\n');

		update(log);
		
	}

	public void clr_log ()
	{
		if(log==null) log=new Log();
		log.setMode(Log.SET_TEXT);
		log.setLog("");

		update(log);
		
	}
	public synchronized void range_query (Rect rect) 
	{
		clr_log();
		tree_backup();
		unmark_All();
		
		RQuery query=new RQuery(rect, -1);
		query.Locate(root.get_Point().x, 0);
		
		
		REntry_List list = Intersecting_subtree(root, query);
		
		while(!list.isEmpty())
		{
			REntry entry = list.remove();
			entry.mark();
		}
		
		update();
		fresh_Pinned();
		
		
	}
	
	public REntry_List Intersecting_subtree (RNode head, RQuery query)
	{	
		/*ANIMATION*/
		
		head.getRectangle().peek=true;
		setCurrent_node_pinned(head);
		setPinned(query);
		query.set_destination(head);
		msg_log("Finde Intersecting subtrees with " + query );
		head.getRectangle().peek=false;		
		REntry_List list = new REntry_List();
		if(head.isLeafNode())
		{
			for(int i=0; i<head.getSize(); i++)
				if(head.getEntries()[i].getRectangle().intersect(query.getRectangle()))
				{
					list.add(head.getEntries()[i]);
					head.getEntries()[i].getRectangle().peek=true;
				}
			
			if(list.isEmpty())
				if(head.getRectangle().intersect(query.getRectangle()))
				{
					list.add(head);
					head.getRectangle().peek=true;
				}
			
			return list;
		}
		
		if(!head.getRectangle().intersect(query.getRectangle()))
			return list;
		
		else
		{
			
			for(int i=0; i<head.getSize(); i++)
				if(head.getEntries()[i].getRectangle().intersect(query.getRectangle()))
				{
					REntry_List buf_list = Intersecting_subtree
							(((RNode) head.getEntries()[i] ), query);
					
					list.addAll(buf_list);

					//ANIMATION
					query.set_destination(head);
					update();

				}
			
			if(list.isEmpty())
				list.add(head);
			
			return list;
		}
		
		
	}
	
	private void unmark_All() {
		unmark_children(root);
	}


	private void unmark_children(RNode node) {
		//if()
		node.unmark();
		for(int i=0; i<node.size; i++)
			if(node.isLeafNode())
				node.getEntries()[i].unmark();
			else
				unmark_children((RNode)node.getEntries()[i]);
	}


	public void range_delete (Rect rect) 
	{
		tree_backup();
		unmark_All();
		
		RQuery delete_query=new RQuery(rect, -1);
		delete_query.Locate(root.get_Point().x, 0);
		
		
		
	}
	
	
	
	public RNode getRoot() {return root;}

	public void setPinned(REntry pinned) {
		this.pinned = pinned;
	}

	public REntry getPinned() {
		return pinned;
	}
	
	
	
	/* General Algorithm */
	private int findMinIndex (int[] input)
	{
		int result = 0;
		
		for(int i=0; i<input.length; i++)
			if(input[result]>input[i]) result=i;
		
		return result;
	}
	
	
	
	
	
	
	/*R Tree alorithm */
	
	public synchronized RLeafNode ChooseLeaf (RNode head, RData data)
	{
		if(head.isLeafNode()){
			msg_log("\tResult of Choose_Leaf: "+ head.getID());
			return (RLeafNode)head;
		}

		msg_log("\tcost evaluation for "+ head.getEntries());

		int[] costs = new int[head.getSize()];

		for(int i=0; i<head.getSize();i++)	
		{

			costs[i] = ((RNode)head.getEntries()[i]).get_Needed_Enlargement(data);
			msg_log("\t\t cost for" + head.getEntries()[i].getID()+": " + costs[i]);

		}
		
		
		/*For graphic*/
		pinned.set_destination(head.entries[findMinIndex(costs)]);
		
		msg_log("\tChoose Leaf for "+head.entries[findMinIndex(costs)].getID());
		
		return ChooseLeaf((RNode)(head).entries[findMinIndex(costs)], data);
	}


	public RNode getSplitted_Node() {
		return split_node_pinned;
	}

	public void setSplitted_Node(RNode splitted_Node) {
		split_node_pinned = splitted_Node;
	}

	public RNode getNew_node_pinned() {
		return new_node_pinned;
	}

	public void setNew_node_pinned(RNode new_node_pinned) {
		this.new_node_pinned = new_node_pinned;
	}
	public synchronized void setDelay(Integer item) {
		setChanged();
		notifyObservers(item);
	}
	public boolean isStop() {
		return isStop;
	}

	public synchronized void setStop(boolean isStop) {
		this.isStop = isStop;
	}
	
	public synchronized void a ()
	{
		setChanged();
		notifyObservers();
	}

	public RNode getUndoTree() {
		return undoTree;
	}

	public void setUndoTree(RNode undoTree) {
		this.undoTree = undoTree;
	}

	public boolean isUndoable() {
		return undoable;
	}

	public void setUndoable(boolean undoable) {
		this.undoable = undoable;
	}
	
	public void undo()
	{
		if(isUndoable())
		{
			RNode backup = root;
			root=undoTree;
			undoTree=backup;
			fresh_Pinned();
			update();
			setUndoable(false);
		}
	}

	public void redo()
	{
		if(!isUndoable())
		{
			RNode backup = root;
			root=undoTree;
			undoTree=backup;
			fresh_Pinned();
			update();
			setUndoable(true);
		}
	}

	public RNode getCurrent_node_pinned() {
		return current_node_pinned;
	}

	public void setCurrent_node_pinned(RNode current_node_pinned) {
		this.current_node_pinned = current_node_pinned;
	}

	public Semaphore getSem() {
		return sem;
	}

	public void setSem(Semaphore sem) {
		this.sem = sem;
	}

}

class looper extends Thread
{
	RTree tree;
	public looper(RTree tree) {
		this.tree=tree;

	}
	
	@Override
	public void run() {
		while(tree.isStop())
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		synchronized (tree) {
			tree.a();
			
		}
	}

}



class Split_Index_Upper_Lower
{
	boolean is_upper;
	int split_index;
	
	public Split_Index_Upper_Lower(boolean is_upper, int split_index) {
		this.is_upper=is_upper;
		this.split_index=split_index;
	}
}