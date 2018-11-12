package r.tree.data;

import r.graphics.Sprite;

public abstract class REntry extends Sprite{
	public abstract Rect getRectangle ();
	public abstract boolean isDataEntry ();
	public abstract boolean isLeafNode ();
	public abstract String getID ();
	
	public abstract void mark();
	public abstract void unmark();
	public abstract boolean isMarked();
	
	
}
