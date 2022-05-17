// By Iacon1
// Created 09/12/2021
// Pair of integers.

package GameEngine;

public class IntPoint2D extends Point2D<Integer, IntPoint2D>
{
	public IntPoint2D(Integer x, Integer y)
	{
		this.x = x;
		this.y = y;
	}
	public <M extends Number> IntPoint2D(Point2D<M, ?> point)
	{
		this.x = point.x.intValue();
		this.y = point.y.intValue();
	}
	@Override
	public int hashCode()
	{
		return (x + ", " + y).hashCode();
	}
	@Override
	public <M extends Number> IntPoint2D add(Point2D<M, ?> delta)
	{
		return new IntPoint2D(this.x.intValue() + delta.x.intValue(), this.y.intValue() + delta.y.intValue());
	}
	@Override
	public <M extends Number> IntPoint2D multiply(M factor)
	{
		return new IntPoint2D(this.x.intValue() * factor.intValue(), this.y.intValue() * factor.intValue());
	}
	@Override
	public <M extends Number> IntPoint2D subtract(Point2D<M, ?> delta)
	{
		return new IntPoint2D(this.x.intValue() - delta.x.intValue(), this.y.intValue() - delta.y.intValue());
	}
	@Override
	public <M extends Number> IntPoint2D divide(M factor)
	{
		return new IntPoint2D(this.x.intValue() / factor.intValue(), this.y.intValue() / factor.intValue());
	}
}
