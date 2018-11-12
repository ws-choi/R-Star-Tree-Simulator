package r.graphics;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

public class Sprite
{
	static final int max_speed = 1000;
	Color color;
	Point point; //아마 현재 위치?
	Point destination; //가야할 위치
	boolean Flag;
	private Image img;
	
	public Sprite() {
		point= new Point(-1,-1);
		destination = new Point(-1,-1);
		color = Color.BLACK;
		Flag=false;
	}
	// Getter Setter of {point} //
	public void Locate(int x, int y)
	{	
		point = new Point(x,y);	
		destination = new Point(x,y);
	}

	public void Locate(Point pointx)
	{	
		point.setLocation(pointx);
		destination.setLocation(pointx);
	}
	
	public Point get_Point ()
	{	return this.point;	}

	// Getter Setter of {destination} //
	public void set_destination (Sprite tobe)
	{	destination = new Point(tobe.point.x, tobe.point.y);	}

	public void set_destination (int x, int y)
	{	destination = new Point(x,y);	}
	
	public Point get_Destination ()
	{	return this.destination;	}
	
	// Getter Setter of {color} //
	public void set_Color (Color color)
	{	this.color = color;	}
	
	public Color get_color ()
	{	return this.color;	}
	
	// Getter Setter of {Flag} //
	public void set_Flag (Boolean flag)
	{	this.Flag = flag;	}
	
	public boolean get_Flag ()
	{	return this.Flag;	}
	
	public void move() {
		move_to(destination, 7);
	}
	public void move_to (Point to_point, int speed)
	{
		if(speed ==0)
			return;
		
		this.destination.setLocation(to_point);
		
		if(this.destination.equals(this.point)) return;
		
		int sign_x = Integer.signum(destination.x-point.x);
		int sign_y = Integer.signum(destination.y-point.y);
		
		int dx = Math.abs(destination.x-point.x);
		int dy = Math.abs(destination.y-point.y);
		
		int n = dx >= dy ? dx : dy;
		
		int delay = 10 - speed +1 ;
		n /= delay;
		
		if( dx >= dy)
		{
			dy <<= 1;
			int bal = dy - dx;
			dx <<=1;
			
			for(int i=0; i<=n; i++)
			{
				if(bal >=0)
				{
					point.y += sign_y;
					bal -= dx;
				}
				bal += dy;
				point.x += sign_x;
				
				if(point.equals(destination)) return;
			}
		}
		

		else
		{
			dx <<= 1;
			int bal = dx - dy;
			dy <<=1;
			
			for(int i=0; i<=n; i++)
			{
				if(bal >=0)
				{
					point.x += sign_x;
					bal -= dy;
				}
				bal += dx;
				point.y += sign_y;
				
				if(point.equals(destination)) return;
			}
		}
		
		
	}
/*	
	public void move_to (Point to_point)
	{
		
		if(this.destination==null) destination=new Point(to_point);
		else destination.setLocation(to_point);

		
		if(this.point.distance(this.destination)<20) 
		{
			this.point.setLocation(this.destination);
			return;
		}
		int dis_x = destination.x - point.x;
		int dis_y = destination.y - point.y;
		
		
		dis_x /=10;
		dis_y /=10;
		
		
		this.point.x += dis_x;
		this.point.y += dis_y;
		
		
		if(this.point.distance(this.destination)<15) 
			this.point.setLocation(this.destination);
	
		System.out.println("stil moving");
		System.out.println(this.point);
		System.out.println(this.destination);
		System.out.println(this.point.distance(this.destination));
	}
	*/
	public boolean isArrived ()
	{
		if(this.point.equals(this.destination)) return true;
		else return false;
	}
	public Image get_Img() {
		return img;
	}
	public void set_Img(Image img) {
		this.img = img;
	}
	
	public void migrate (int x, int y)
	{
		Locate(get_Point().x+x, get_Point().y+y);
	}
	
}