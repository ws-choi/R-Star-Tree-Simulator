package r.tree.data;

import java.awt.Point;
import java.util.Arrays;

import r.global.Constants;
import r.graphics.Sprite;

public class Rect extends Sprite{

	public int[] vectors;
	StringBuffer output;
	public boolean peek;
	
	public static void main(String[] args) {
		
		Rect rect = new Rect(new int[] {0, 10, 0, 10});
		System.out.println(rect.intersect(new Point(0,9)));
		
	}
	public Rect(int[] vectors)  {
		//check input
		try {
			TestInput(vectors);
			this.vectors = vectors.clone();
			output = new StringBuffer();
			RefineInput();			
			peek=false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Dimension error.");
		}
	}
	
	private static void TestInput (int[] vectors) throws Exception
	{
		if(vectors.length != Constants.Dimension *2)
			throw new Exception();
	}
	
	private void RefineInput () throws Exception
	{
		for(int i=0; i<Constants.Dimension; i++)
		{
			if(vectors[i*2+1] < 0)
			{
				vectors[i*2] += vectors[i*2+1];
				vectors[i*2+1] = Math.abs(vectors[i*2+1]);
			}
		}		
	}
	
	public Rect get_Overlap_Rect (Rect rect) {
		
		int[] newVector = new int[2*Constants.Dimension];
		
		for(int i=0; i<Constants.Dimension; i++)
		{
			if(vectors[i*2] >= rect.vectors[i*2])
				if(vectors[i*2] <= rect.vectors[i*2] + rect.vectors[i*2+1])
				{
					newVector[i*2] = vectors[i*2];
					if(rect.vectors[i*2] + rect.vectors[i*2+1]
					    > vectors[i*2] + vectors[i*2+1] )
						newVector[i*2+1] = vectors[i*2+1];
					else newVector[i*2+1] = 
						rect.vectors[i*2] + rect.vectors[i*2+1] - vectors[i*2];
				}
				else return Constants.nullRect;
			else if(rect.vectors[i*2] <= vectors[i*2]+ vectors[i*2+1])
				if(rect.vectors[i*2] >= vectors[i*2])
				{
					newVector[i*2] = rect.vectors[i*2];
					if(vectors[i*2] + vectors[i*2+1]
					   > rect.vectors[i*2] + rect.vectors[i*2+1])
						newVector[i*2+1] = rect.vectors[i*2+1];
					
					else newVector[i*2+1] = 
						vectors[i*2] + vectors[i*2+1] - rect.vectors[i*2];
				}
				else return Constants.nullRect;
			else return Constants.nullRect;
		}
		return new Rect(newVector);
	}
	
	public int get_Overlap (Rect rect) {
		
		int result =1;
		
		for(int i=0; i<Constants.Dimension; i++)
		{
			if(vectors[i*2] >= rect.vectors[i*2])
				if(vectors[i*2] <= rect.vectors[i*2] + rect.vectors[i*2+1])
				{
					if(rect.vectors[i*2] + rect.vectors[i*2+1]
					    > vectors[i*2] + vectors[i*2+1] )
						result *= vectors[i*2+1];
					else result *= 
						rect.vectors[i*2] + rect.vectors[i*2+1] - vectors[i*2];
				}
				else return 0;
			else if(rect.vectors[i*2] <= vectors[i*2]+ vectors[i*2+1])
				if(rect.vectors[i*2] >= vectors[i*2])
				{
					if(vectors[i*2] + vectors[i*2+1]
					   > rect.vectors[i*2] + rect.vectors[i*2+1])
						result *= rect.vectors[i*2+1];
					
					else result *=
						vectors[i*2] + vectors[i*2+1] - rect.vectors[i*2];
				}
				else return 0;
			else return 0;
		}
		return result;
	}
	
	public int get_area () 
	{
		int result = 1;
		for(int i=0; i<Constants.Dimension; i++)
			result *= vectors[i*2+1];
		
		return result;
	}
	
	public int get_min(int axis){return vectors[2 * axis];}
	public int get_max(int axis){return (vectors[2 * axis] + vectors[2 * axis + 1]);}
	public int get_Dist (int axis) {return vectors[2*axis+1]; }
	
	
	
	//boolean method
	public boolean equals (Rect rect){return Arrays.equals(vectors, rect.vectors);}
	public boolean intersect (Rect rect)
	{return (get_Overlap_Rect(rect).equals(Constants.nullRect)) ? false : true;}
	
	//only for 2 dim
	public boolean intersect (Point point)
	{
		Rect rect = new Rect(new int[] {point.x, 0, point.y,0});
		return intersect(rect);
	}
	
	@Override
	public String toString() {
		output.delete(0, output.length());

		output.append("Vertex vec: { ");
		output.append(vectors[0]);
		for(int i=1; i<Constants.Dimension; i++)
		{
			output.append(',');
			output.append(' ');
			output.append(vectors[i*2]);
		}
		output.append(" }");
		
		output.append(", Length vec: { ");
		output.append(vectors[1]);
		for(int i=1; i<Constants.Dimension; i++)
		{
			output.append(',');
			output.append(' ');
			output.append(vectors[i*2+1]);
		}
		output.append(" }");

		return output.toString();
	}
	
	public int get_x1 () {return get_min(0);}
	public int get_y1 () {return get_min(1);}
	public int get_x2 () {return get_max(0);}
	public int get_y2 () {return get_max(1);}
	
	
	
}
