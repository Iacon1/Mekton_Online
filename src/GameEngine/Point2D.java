// By Iacon1
// Created 09/12/2021
//

package GameEngine;

public class Point2D implements Cloneable
{
	public int x;
	public int y;
	
	public Point2D(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Point2D add(Point2D delta)
	{
		return new Point2D(this.x + delta.x, this.y + delta.y);
	}
	public Point2D multiply(int factor)
	{
		return new Point2D(this.x * factor, this.y * factor);
	}
	public Point2D subtract(Point2D delta)
	{
		return add(delta.multiply(-1));
	}
	public Point2D divide(int factor)
	{
		return new Point2D(this.x / factor, this.y / factor);
	}
	
	@Override
	public Point2D clone()
	{
		return new Point2D(this.x, this.y);
	}
	@Override
	public boolean equals(Object obj)
	{
		if (obj.getClass() != this.getClass()) return false;
		else
		{
			Point2D point = (Point2D) obj;
			return (x == point.x) && (y == point.y);
		}
	}
	@Override
	public int hashCode()
	{
		return (x + ", " + y).hashCode();
	}
}
