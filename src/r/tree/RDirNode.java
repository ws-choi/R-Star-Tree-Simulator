package r.tree;

import r.tree.data.RData;
import r.tree.data.Rect;

public class RDirNode extends RNode {

	public RDirNode(RNode Parent) {
		super(Parent);
	}

	public RDirNode(RNode Parent, int id) {
		super(Parent, id);
	}

	@Override
	public Rect getRectangle() {return MBR;}
	@Override
	public boolean isDataEntry() {return false;}
	@Override
	public boolean isLeafNode() {return false;}
	@Override
	public String getID() {return "DIR_NODE ID: " + id;}

	public RNode copy_subtree() {
		
		RNode newNode= new RDirNode(null, id);
		newNode.Locate(this.get_Point());
		
		for(int i=0; i<getSize(); i++)
			newNode.push_entry(((RNode)entries[i]).copy_subtree() );
		
		newNode.adjust();
		return newNode;
	}
}
