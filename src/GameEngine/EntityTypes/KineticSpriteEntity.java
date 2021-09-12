// By Iacon1
// Created 09/11/2021
// A sprite that moves

package GameEngine.EntityTypes;

public abstract class KineticSpriteEntity extends SpriteEntity
{
	protected int tX_ = -1; // Target coords; -1 if no target
	protected int tY_ = -1;
	protected int speed_ = 0; // Speed, 0 if none

	/**
	* Moves x pixels to the right and y pixels down.
	* <p>
	*
	* @param  x How far right (or left, if negative) to move.
	* @param  y How far down (or up, if negative) to move.
	*/
	public void moveDelta(int x, int y)
	{
		x_ += x;
		y_ += y;
	}

	/**
	* Moves to (x, y) at a set speed.
	* <p>
	*
	* @param  x     X coord to move to.
	* @param  y     Y coord to move to.
	* @param  speed How fast to move.
	*/
	public void moveTargetSpeed(int x, int y, int speed)
	{
		tX_ = x;
		tY_ = y;
		speed_ = speed;
	}
	/**
	* Moves x to the right and y to the left at a set speed.
	* <p>
	*
	* @param  x     How far right (or left, if negative) to move.
	* @param  y     How far down (or up, if negative) to move
	* @param  speed How fast to move.
	*/
	public void moveDeltaSpeed(int dX, int dY, int speed)
	{
		moveTargetSpeed(x_ + dX, y_ + dY, speed);
	}

	protected int getDirSpeedX(int dX, int dY)
	{
		double angle = Math.atan2(dY, dX);
		return (int) (((double) speed_) * Math.cos(angle)); // Directional speeds
	}
	protected int getDirSpeedY(int dX, int dY)
	{
		double angle = Math.atan2(dY, dX);
		return (int) (((double) speed_) * Math.sin(angle));
	}
	/**
	* Called when you stop moving.
	*/
	public abstract void onStop(); // When you stop
	private void updateMove() // Updates movement with speed
	{
		if (tX_ == -1 && tY_ == -1 && speed_ == 0) return;
		int dX = tX_ - x_; // Delta x - The distance left to go
		int dY = tY_ - y_;

		int speedX = getDirSpeedX(dX, dY); 
		int speedY = getDirSpeedY(dX, dY);
		
		if (Math.abs(dX) <= Math.abs(speedX)) x_ = tX_; // We're within speed of target
		else x_ += speedX;
		if (Math.abs(dY) <= Math.abs(speedY)) y_ = tY_; // We're within speed of target
		else y_ += speedY;
		
		if (x_ == tX_ && y_ == tY_)
		{
			tX_ = -1;
			tY_ = -1;
			speed_ = 0;
			onStop();
		}
		
		
	}
	@Override
	public void update() {updateMove();}
}
