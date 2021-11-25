// By Iacon1
// Created 09/11/2021
// A sprite that can move and animate

package GameEngine.EntityTypes;

import GameEngine.Animation;
import GameEngine.Sprite;
import GameEngine.Client.GameFrame;
import GameEngine.Point2D;
import GameEngine.ScreenCanvas;
import Utils.SimpleTimer;

public abstract class SpriteEntity extends GameEntity implements Alignable
{
	// Basic sprite variables
	
	protected Sprite sprite;

	protected Point2D pos; // Position on screen
	protected Point2D boundSize; // Size of hitbox
	
	protected Point2D spriteOff; // Texture offset

	// Kinetic variables
	
	protected Point2D targetPos; // Target position, -1 if none
	protected int speed = 0; // Speed, 0 if none
	
	// Animation variables
	
	protected Animation animation; // Current animation
	protected int frame = -1; // Current frame, -1 if not playing
	protected SimpleTimer animTimer;

	// Constructor
	
	public SpriteEntity()
	{
		super();
		
		pos = new Point2D(0, 0);
		boundSize = new Point2D(0, 0);
		
		spriteOff = new Point2D(0, 0);

		targetPos = new Point2D(-1, -1);
		
		animTimer = new SimpleTimer();
	}
	
	// Basic sprite functions

	/** Sets the sprite used for this object.
	 *  
	 *  @param sprite Sprite to use.
	 */
	public void setSprite(Sprite sprite)
	{
		this.sprite = sprite;
	}
	
	public void setSpriteParams(Integer textureX, Integer textureY, Integer width, Integer height)
	{
		sprite.setBasicParams(textureX, textureY, width, height);
	}
	
	/** Sets the hitbox and sprite offset relative to said hitbox.
	 *  If any value is null then that value will not be updated.
	 *  Good for animations or changing specific offsets.
	 *  
	 *  @param texturePath Texture sheet to load
	 *  
	 *  @param boundSizeX  Width of the hitbox.
	 *  @param boundSizeY  Height of the hitbox.
	 *  
	 *  @param spriteOffX  X coordinate of the top-left corner of the sprite, relative to position.
	 *  @param spriteOffY  X coordinate of the top-left corner of the sprite, relative to position.
	 */
	public void setBounds(Integer boundSizeX, Integer boundSizeY, Integer spriteOffX, Integer spriteOffY)
	{
		
		if (boundSizeX != null) boundSize.x = boundSizeX;
		if (boundSizeY != null) boundSize.y = boundSizeY;
		
		if (spriteOffX != null) spriteOff.x = spriteOffX;
		if (spriteOffY != null) spriteOff.y = spriteOffY;
	}
	
	public void setPos(Point2D pos)
	{
		this.pos = pos;
	}
	public void setPos(int x, int y)
	{
		setPos(new Point2D(x, y));
	}
	
	public Point2D getPos()
	{
		return pos;
	}
	
	// Kinetic functions
	
	public int getSpeed()
	{
		return speed;
	}
	
	/** Called when you start moving.*/
	public abstract void onStart(); // When you stop
	/** Called when you stop moving.*/
	public abstract void onStop(); // When you stop
	
	/**
	* Moves by delta (i. e. adds delta to our position).
	* <p>
	*
	* @param  delta how far to move in each direction..
	*/
	public void moveDelta(Point2D delta)
	{
		pos = pos.add(delta);
	}
	/**
	* Moves to pos at a set speed.
	* <p>
	*
	* @param  pos   Where to go to.
	* @param  speed How fast to move.
	*/
	public void moveTargetSpeed(Point2D pos, int speed)
	{
		targetPos = pos;
		this.speed = speed;
		if (this.speed != 0) onStart();
	}
	/**
	* Moves by delta at a set speed.
	* <p>
	*
	* @param  delta How far to go.
	* @param  speed How fast to move.
	*/
	public void moveDeltaSpeed(Point2D delta, int speed)
	{
		moveTargetSpeed(pos.add(delta), speed);
	}

	protected Point2D getSpeedVector(Point2D delta)
	{
		
		double angle = Math.atan2(delta.y, delta.x);
		int sX = (int) Math.floor(((double) speed) * Math.cos(angle)); // Directional speeds
		int sY = (int) Math.floor(((double) speed) * Math.sin(angle));
		return new Point2D(sX, sY);
	}
	private void updateMove() // Updates movement with speed
	{
		if (targetPos.equals(new Point2D(-1, -1)) && speed == 0) return;
		Point2D delta = new Point2D(targetPos.x - pos.x, targetPos.y - pos.y);
		
		Point2D speedVector = getSpeedVector(delta);
		
		if (Math.abs(delta.x) <= Math.abs(speedVector.x)) pos.x = targetPos.x; // We're within speed of target
		else pos.x += speedVector.x;
		if (Math.abs(delta.y) <= Math.abs(speedVector.y)) pos.y = targetPos.y; // We're within speed of target
		else pos.y += speedVector.y;
		
		if (pos.equals(targetPos))
		{
			targetPos = new Point2D(-1, -1);
			speed = 0;
			onStop();
		}
	}

	// Animation functions
	
	private void setFrame(int offset) // In height multiples
	{
		setSpriteParams(null, sprite.getSize().y * offset, null, null);
	}
	public void startAnimation(Animation animation)
	{
		this.animation = animation;
		frame = 0;
		setFrame(frame);
		animTimer.start();
	}
	public void stopAnimation(boolean handle)
	{
		frame = -1;
		if (handle) onAnimStop();
	}
	public boolean isAnimPlaying()
	{
		return (frame == -1);
	}
	/** Called when a callHandle-type animation stops.*/
	public abstract void onAnimStop();
	private void updateAnim()
	{
		if (frame == -1) return;
		if (animTimer.checkTime(animation.GetFrameDuration()))
		{
			frame += 1;
			if (frame >= animation.frames) switch (animation.action)
			{
			case stickFrame:
				frame = -1;
				break;
			case callHandle:
				frame = -1;
				onAnimStop();
				break;
			case loop:
				frame = 0;
				break;
			}
		}
	}
	
	// Alignment functions
	
	private Point2D getAlignmentOffset(AlignmentPoint point) // Gets the offset instead of the point
	{
		if (sprite == null) return new Point2D(0, 0);
		Point2D offset = new Point2D(0, 0);
		switch (point)
		{
		case northWest: break;
		case north: offset.x = sprite.getSize().x / 2; break;
		case northEast: offset.x = sprite.getSize().x - 1; break;
		
		case west: offset.y = sprite.getSize().y / 2; break;
		case center: offset.x = sprite.getSize().x / 2;
			offset.y = sprite.getSize().y / 2; break;
		case east: offset.x = sprite.getSize().x - 1; 
			offset.y = sprite.getSize().y / 2; break;
			
		case southWest: offset.y = sprite.getSize().y - 1; break;
		case south: offset.x = sprite.getSize().x / 2; 
			offset.y = sprite.getSize().y - 1; break;
		case southEast: offset.x = sprite.getSize().x - 1; 
			offset.y = sprite.getSize().y - 1; break;
			
		default: return null;
		}
		return offset;
	}
	
	// Override functions
	
	@Override
	public Point2D getAlignmentPoint(AlignmentPoint point)
	{
		Point2D offset = getAlignmentOffset(point);
		if (offset != null) return pos.add(offset);
		else return null;
	}
	@Override
	public void align(AlignmentPoint point, Alignable target, AlignmentPoint targetPoint)
	{
		Point2D targetPos = null;
		if (target != null) targetPos = target.getAlignmentPoint(targetPoint);
		else targetPos = GameFrame.getAlignmentPoint(targetPoint); // Align with frame;
		
		setPos(targetPos.subtract(getAlignmentOffset(point)));
	}
	
	@Override
	public void update() {updateAnim(); updateMove();}
	
	@Override
	public void render(ScreenCanvas canvas, Point2D camera) 
	{
		sprite.render(canvas, pos.subtract(camera).add(spriteOff));
	}
}
