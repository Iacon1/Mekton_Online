// By Iacon1
// Created 09/10/2021
// That which listens to input

package GameEngine.EntityTypes;

public interface InputGetter extends CommandRunner
{
	/**
	* Called when mouse is clicked, generates a command to run as this entity.
	* 
	* @param  mX     Mouse X-position (or -1 if mouse is out of game window)
	* @param  mY     Mouse Y-position (or -1 if mouse is out of game window)
	* @param  button Was the mouse left-clicked (0), right-clicked (1), or mid-clicked (2)?
	* 
	* @return A command to run, or null if none.
	*/
	public String onMouseClick(int mX, int mY, int button);
	/**
	* Called when mouse is pressed, generates a command to run as this entity.
	*
	* @param  mX     Mouse X-position (or -1 if mouse is out of game window)
	* @param  mY     Mouse Y-position (or -1 if mouse is out of game window)
	* @param  button Was the mouse left-pressed(0), right-pressed (1), or mid-pressed (2)?
	* 
	* @return A command to run, or null if none.
	*/
	public String onMousePress(int mX, int mY, int button);
	/**
	* Called when mouse is clicked, generates a command to run as this entity.
	* 
	* @param  mX     Mouse X-position (or -1 if mouse is out of game window)
	* @param  mY     Mouse Y-position (or -1 if mouse is out of game window)
	* @param  button Was the mouse left-released (0), right-released (1), or mid-released (2)?
	* 
	* @return A command to run, or null if none.
	*/
	public String onMouseRelease(int mX, int mY, int button);
	
	/**
	* Called when key is pressed, generates a command to run as this entity.
	*
	* @param  code The key that was pressed.
	* 
	* @return A command to run, or null if none.
	*/
	public String onKeyPress(int code);
	/**
	* Called when key is released, generates a command to run as this entity.
	*
	* @param  code The key that was released.
	* 
	* @return A command to run, or null if none.
	*/
	public String onKeyRelease(int code);
}
