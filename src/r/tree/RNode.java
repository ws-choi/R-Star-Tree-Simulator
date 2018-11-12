/**
 * 
 */
package r.tree;

import java.awt.Color;

import r.global.Constants;
import r.tree.data.RData;
import r.tree.data.REntry;
import r.tree.data.REntry_List;
import r.tree.data.Rect;
import r.tree.data.Rect_List;

/**
 * @author �ֿ켺
 *
 */
public abstract class RNode extends  REntry{

	//For its child
	int size;
	REntry[] entries;
	public Rect MBR;
	
	//For its own
	public int id;
	
	//For its parent
	public RNode Parent;
	
	//For output
	public StringBuffer buf;
	
	private boolean Marked;
	
	//Constructor
	public RNode(RNode Parent) 
	{
		this.Parent = Parent;
		id=Constants.N_LastID++;
		MBR=generateMBR();
		entries= new REntry[Constants.MAX_ENTRIES_IN_NODE];
		size=0;
		buf = new StringBuffer();
	}
	
	public RNode(RNode Parent, int id) 
	{
		this.id=id;
		this.Parent = Parent;
		MBR=generateMBR();
		entries= new REntry[Constants.MAX_ENTRIES_IN_NODE];
		size=0;
		buf = new StringBuffer();
	}
	
	
	//own method
	public boolean add (REntry entry)
	{
		if(isLeafNode()) return ((RLeafNode)this).add((RData)entry);
		else return ((RDirNode)this).add((RNode)entry);		
	}
	
	//Method related to # of children
	public boolean isEmpty () {return (size==0);}
	public boolean isFool () {return (size>=Constants.MAX_ENTRIES_IN_NODE);}
	public boolean isLack () {return (size<Constants.MIN_ENTRIES_IN_NODE);}
	
	//Method related to MBR
	public void adjust () 
	{
		MBR = generateMBR();
		if(Parent!=null) Parent.adjust();
		
	}
	public Rect generateMBR ()
	{
		if(isEmpty()) return Constants.nullRect;

		Rect_List bufList = new Rect_List();
		
		for(int i=0; i<size; i++)
			bufList.add(entries[i].getRectangle());
		
		Rect result = bufList.generateMBR();
		bufList = null;
		return result;		
	}

	//for output buffer
	public String toString ()
	{
		//init buf
		buf.delete(0, buf.length());
		
		if(isLeafNode())
			buf.append("LeafNode: ");
		else
			buf.append("DirNode: ");
		
		//print Node id
		buf.append("Node_ID: ");
		buf.append(id);

		//print the MBR of the node
		buf.append(", MBR: ");
		buf.append(getRectangle());		

		//print size
		buf.append(", # of DATAS: ");
		buf.append(size);
		//return
		return buf.toString();
	}
	
	public void push_entry (REntry entry) 
	{
		entries[size++] = entry;
		if(!entry.isDataEntry()) ((RNode)entry).Parent=this;
//		if(this.get_Point()!=null)
//		entry.set_destination(this.get_Point().x, this.get_Point().y+60);
	}
	public void remove_entry (Rect rect)
	{
		int newindex=0;
		for(int i=0; i<size; i++)
			if(entries[i].getRectangle().equals(rect)) continue;
			else entries[newindex++] = entries[i];
		
		size = newindex;
	}
	public void remove_intersect (Rect rect)
	{
		int newindex=0;
		for(int i=0; i<size; i++)
			if(entries[i].getRectangle().intersect(rect)) continue;
			else entries[newindex++] = entries[i];
		
		size = newindex;
	}
	
	public void remove_all ()
	{
		entries = new REntry[Constants.MAX_ENTRIES_IN_NODE];
		size = 0;
	}
	
	public int getSize() {return this.size;}
	public REntry[] getEntries() {return this.entries;}
	
	public int get_Needed_Enlargement (REntry entry)
	{
		Rect_List buf = new Rect_List();
		buf.add(MBR);
		buf.add(entry.getRectangle());
	
		Rect enlarged = buf.generateMBR();
		
		return enlarged.get_area()-MBR.get_area();
	}
	
	
	@Override
	public void mark() {
		super.set_Color(Color.green);
		Marked=true;
		/*TODO*/
		getRectangle().peek=true;		
	}
	
	@Override
	public void unmark() {
		super.set_Color(Color.black);
		Marked=false;
		getRectangle().peek=false;		
	}

	public boolean isMarked() {
		return Marked;
	}

	public abstract RNode copy_subtree();

	public REntry copy() {
		return null;
	}	
	
}

