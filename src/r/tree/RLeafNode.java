 package r.tree;

import r.global.Constants;
import r.tree.data.RData;
import r.tree.data.Rect;

public class RLeafNode extends RNode {

	public RLeafNode(RNode Parent) {
		super(Parent);
	}

	public RLeafNode(RNode Parent, int id) {
		super(Parent, id);
	}

	@Override
	public Rect getRectangle() {return MBR;}
	@Override
	public boolean isDataEntry() {return false;}
	@Override
	public boolean isLeafNode() {return true;}

	@Override
	public String getID() { return "LEAF_NODE ID: " + id;}

	public RNode copy_subtree() {
		
		RNode newNode= new RLeafNode(null, this.id);
		newNode.Locate(this.get_Point());
		
		for(int i=0; i<getSize(); i++)
			newNode.push_entry(((RData)entries[i]).copy()); 
		
		return newNode;
	}	
	
}
