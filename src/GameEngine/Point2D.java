// By Iacon1
// Created 09/12/2021
//

package GameEngine;

public class Point2D
{
	public int x_;
	public int y_;
	
	public Point2D(int x, int y)
	{
		x_ = x;
		y_ = y;
	}
	
	public Point2D add(Point2D delta)
	{
		return new Point2D(this.x_ + delta.x_, this.y_ + delta.y_);
	}
	public Point2D multiply(int factor)
	{
		return new Point2D(this.x_ * factor, this.y_ * factor);
	}
	public Point2D subtract(Point2D delta)
	{
		return add(delta.multiply(-1));
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj.getClass() != this.getClass()) return false;
		else
		{
			Point2D point = (Point2D) obj;
			return (x_ == point.x_) && (y_ == point.y_);
		}
	}
	@Override
	public int hashCode()
	{
		return (x_ + ", " + y_).hashCode();
	}
}
