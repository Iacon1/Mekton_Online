// By Iacon1
// Created 09/12/2021
// Pair of double points.

package GameEngine;

public class DoublePoint2D extends Point2D<Double, DoublePoint2D>
{
	public DoublePoint2D(Double x, Double y)
	{
		this.x = x;
		this.y = y;
	}
	public <M extends Number> DoublePoint2D(Point2D<M, ?> point)
	{
		this.x = point.x.doubleValue();
		this.y = point.y.doubleValue();
	}

	@Override
	public <M extends Number> DoublePoint2D add(Point2D<M, ?> delta) {return new DoublePoint2D(this.x.doubleValue() + delta.x.doubleValue(), this.y.doubleValue() + delta.y.doubleValue());}
	@Override
	public <M extends Number> DoublePoint2D multiply(M factor) {return new DoublePoint2D(this.x.doubleValue() * factor.doubleValue(), this.y.doubleValue() * factor.doubleValue());}
	@Override
	public <M extends Number> DoublePoint2D subtract(Point2D<M, ?> delta) {return new DoublePoint2D(this.x.doubleValue() - delta.x.doubleValue(), this.y.doubleValue() - delta.y.doubleValue());}
	@Override
	public <M extends Number> DoublePoint2D divide(M factor) {return new DoublePoint2D(this.x.doubleValue() / factor.doubleValue(), this.y.doubleValue() / factor.doubleValue());}
}
