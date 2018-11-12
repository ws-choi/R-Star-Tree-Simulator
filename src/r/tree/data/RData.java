package r.tree.data;

import java.awt.Color;

import r.global.Constants;
import r.graphics.Image_Loader;
import r.graphics.Sprite;

public class RData extends REntry{

	public int id;
	private Rect data;
	private boolean Marked;
	private Image_Loader il = new Image_Loader();

	public RData(Rect data) {
		this.data=data;
		this.id = Constants.D_LastId++;
		set_Img(il.Data);
	}
	
	public RData(Rect data, int id2) {
		this.data=data;
		this.id=id2;
		set_Img(il.Data);
	}

	@Override
	public Rect getRectangle() {return data;}
	@Override
	public boolean isDataEntry() {return true;}
	@Override
	public boolean isLeafNode()  {return false;}
	@Override
	public String getID() {return "DATA ID: " + id;}

	@Override
	public void mark() {
		super.set_Color(Color.green);
		Marked=true;
		getRectangle().peek=true;		
	}
	
	@Override
	public void unmark() {
		super.set_Color(Color.black);
		Marked=false;
		getRectangle().peek=false;		
	}

	@Override
	public boolean isMarked() {
		return Marked;
	}

	public RData copy() {
		RData newData = new RData(getRectangle(), id);
		newData.Locate(get_Point());
		return newData;
	}
	
	public String toString ()
	{
		return "Data id: " +id +". "+data;
	}
	

}
