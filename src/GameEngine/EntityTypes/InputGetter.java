// By Iacon1
// Created 09/10/2021
// That which listens to input

package GameEngine.EntityTypes;

public interface InputGetter
{
	/**
	* Called when mouse is clicked.
	* <p>
	* 
	* @param  mX     Mouse X-position (or null if mouse is out of game window)
	* @param  mY     Mouse Y-position (or null if mouse is out of game window)
	* @param  button Was the mouse left-clicked (0), right-clicked (1), or mid-clicked (2)?
	*/
	public void onMouseClick(int mX, int mY, int button);
	/**
	* Called when mouse is pressed.
	* <p>
	*
	* @param  mX     Mouse X-position (or null if mouse is out of game window)
	* @param  mY     Mouse Y-position (or null if mouse is out of game window)
	* @param  button Was the mouse left-pressed(0), right-pressed (1), or mid-pressed (2)?
	*/
	public void onMousePress(int mX, int mY, int button);
	/**
	* Called when mouse is released.
	* <p>
	* 
	* @param  mX     Mouse X-position (or null if mouse is out of game window)
	* @param  mY     Mouse Y-position (or null if mouse is out of game window)
	* @param  button Was the mouse left-released (0), right-released (1), or mid-released (2)?
	*/
	public void onMouseRelease(int mX, int mY, int button);
	
	/**
	* Called when key is pressed.
	* <p>
	* This method always returns immediately, whether or not the 
	* image exists. When this applet attempts to draw the image on
	* the screen, the data will be loaded. The graphics primitives 
	* that draw the image will incrementally paint on the screen. 
	*
	* @param  code The key that was pressed.
	*/
	public void onKeyPress(int code);
	/**
	* Called when key is released.
	* <p>
	*
	* @param  code The key that was released.
	*/
	public void onKeyRelease(int code);
}
