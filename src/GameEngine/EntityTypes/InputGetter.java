// By Iacon1
// Created 09/10/2021
// That which listens to input

package GameEngine.EntityTypes;

public interface InputGetter
{
	/**
	* Called when mouse is clicked.
	* Important to note that this is a client-side function, so
	* changes to the object here won't be reflected by the server
	* or any future frames.
	* 
	* @param  mX     Mouse X-position (or null if mouse is out of game window)
	* @param  mY     Mouse Y-position (or null if mouse is out of game window)
	* @param  button Was the mouse left-clicked (0), right-clicked (1), or mid-clicked (2)?
	*/
	public void onMouseClick(int mX, int mY, int button);
	/**
	* Called when mouse is pressed.
	* Important to note that this is a client-side function, so
	* changes to the object here won't be reflected by the server
	* or any future frames.
	*
	* @param  mX     Mouse X-position (or null if mouse is out of game window)
	* @param  mY     Mouse Y-position (or null if mouse is out of game window)
	* @param  button Was the mouse left-pressed(0), right-pressed (1), or mid-pressed (2)?
	*/
	public void onMousePress(int mX, int mY, int button);
	/**
	* Called when mouse is released.
	* Important to note that this is a client-side function, so
	* changes to the object here won't be reflected by the server
	* or any future frames.
	* 
	* @param  mX     Mouse X-position (or null if mouse is out of game window)
	* @param  mY     Mouse Y-position (or null if mouse is out of game window)
	* @param  button Was the mouse left-released (0), right-released (1), or mid-released (2)?
	*/
	public void onMouseRelease(int mX, int mY, int button);
	
	/**
	* Called when key is pressed.
	* Important to note that this is a client-side function, so
	* changes to the object here won't be reflected by the server
	* or any future frames.
	*
	* @param  code The key that was pressed.
	*/
	public void onKeyPress(int code);
	/**
	* Called when key is released.
	* Important to note that this is a client-side function, so
	* changes to the object here won't be reflected by the server
	* or any future frames.
	*
	* @param  code The key that was released.
	*/
	public void onKeyRelease(int code);
}
