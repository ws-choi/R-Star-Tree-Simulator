package r.tree.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import r.global.Constants;

public class REntry_List extends LinkedList<REntry>{


	public REntry_List(REntry[] entries) {
		super();
		for(int i=0; i<entries.length; i++)
			add(entries[i]);
	}
	
	public REntry_List() {
		super();
	}


	public REntry[] getSorted (int axis, int upper_or_lower)
	{
		REntry[] result = this.toArray(new REntry[0]);
		
		switch (axis) {
		case Constants.SORT_BY_X:
			
			if(upper_or_lower==Constants.SORT_BY_LOWER)
				Collections.sort(this, new Comparator<REntry>() {

					@Override
					public int compare(REntry o1, REntry o2) {
						
						int o1_lower_x=o1.getRectangle().get_x1();
						int o2_lower_x=o2.getRectangle().get_x1();
						
						if(o1_lower_x > o2_lower_x )
							return 1;
						else if (o1_lower_x < o2_lower_x)
							return -1;
						return 0;
					}
				});
			
			else if(upper_or_lower==Constants.SORT_BY_UPPER)
				Collections.sort(this, new Comparator<REntry>() {

					@Override
					public int compare(REntry o1, REntry o2) {
						
						int o1_upper_x=o1.getRectangle().get_x2();
						int o2_upper_x=o2.getRectangle().get_x2();
						
						if(o1_upper_x > o2_upper_x )
							return 1;
						else if (o1_upper_x < o2_upper_x)
							return -1;
						return 0;
					}
				});
			
			else return null;
			break;

		case Constants.SORT_BY_Y:
			
			if(upper_or_lower==Constants.SORT_BY_LOWER)
				Collections.sort(this, new Comparator<REntry>() {

					@Override
					public int compare(REntry o1, REntry o2) {
						
						int o1_lower_y=o1.getRectangle().get_y1();
						int o2_lower_y=o2.getRectangle().get_y1();
						
						if(o1_lower_y > o2_lower_y )
							return 1;
						else if (o1_lower_y < o2_lower_y)
							return -1;
						return 0;
					}
				});
			
			else if(upper_or_lower==Constants.SORT_BY_UPPER)
				Collections.sort(this, new Comparator<REntry>() {

					@Override
					public int compare(REntry o1, REntry o2) {
						
						int o1_upper_y=o1.getRectangle().get_y2();
						int o2_upper_y=o2.getRectangle().get_y2();
						
						if(o1_upper_y > o2_upper_y )
							return 1;
						else if (o1_upper_y < o2_upper_y)
							return -1;
						return 0;
					}
				});
			
			else return null;

			break;
		default:
			return null;
		}
		
		return this.toArray(new REntry[0]);
	}

}
