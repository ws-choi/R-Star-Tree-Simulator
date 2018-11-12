/**
 * 
 */
package r.tree.data;

import java.util.LinkedList;

import r.global.Constants;



/**
 * @author �ֿ켺
 *
 */
public class Rect_List extends LinkedList<Rect> {


	public Rect_List() {
	}

	public Rect_List(Rect[] list) {
		
		for(int i=0; i<list.length; i++)
			add(list[i]);
	}

	
	public Rect generateMBR ()
	{
		if(isEmpty()) return Constants.nullRect;
	
		int[] MBRvectors = new int[Constants.Dimension * 2];
	
		for(int i=0; i<Constants.Dimension; i++)
		{
			MBRvectors[i*2] = getMin(i);
			MBRvectors[i*2+1] = getMax(i) - getMin(i);
		}
		
		return new Rect(MBRvectors);
	}
	
	private int getMax (int axis)
	{
		int result = Integer.MIN_VALUE;
		for( Rect rectangle : this )
			if(result<rectangle.get_max(axis)) result = (int)rectangle.get_max(axis);
	
		return result;
	}
	
	private int getMin (int axis)
	{
		int result = Integer.MAX_VALUE;
		for( Rect rectangle : this )
			if(result>rectangle.get_min(axis)) result = (int)rectangle.get_min(axis);
	
		return result;
	}
	
/*	public static void main(String[] args) {
		
		R_Rect r1 = new R_Rect(new int[] {0,10, 0, 10});
		R_Rect r3 = new R_Rect(new int[] {0,1, 0, 1});
		
		Rect_List rl = new Rect_List();
		rl.add(r1);
		rl.add(r3);
		
		System.out.println(rl.generateMBR());
	}*/
}
