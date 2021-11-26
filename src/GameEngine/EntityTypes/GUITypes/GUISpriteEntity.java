// By Iacon1
// Created 09/10/2021
// Has a sprite but doesn't respond to camera
// Has an owner, and won't appear to other players

package GameEngine.EntityTypes.GUITypes;

import GameEngine.GameInfo;
import GameEngine.Point2D;
import GameEngine.ScreenCanvas;
import GameEngine.EntityTypes.SpriteEntity;
import Utils.MiscUtils;
import GameEngine.EntityTypes.CommandRunner;
import GameEngine.EntityTypes.InputGetter;

public abstract class GUISpriteEntity extends SpriteEntity implements InputGetter, CommandRunner
{
	private int ownerID = -1;
	
	public final static String GUICommandHeader = "updateGUI";
	
	/** Sends a command containing a GUI update with the parameters
	 * 
	 *  @param parameters Parameters to send.
	 */
	protected static void sendUpdate(String type, Object... parameters)
	{
		GameInfo.addCommand(GUICommandHeader + " (" + type + ")" + ": " + MiscUtils.arrayToString(parameters, " "));
	}
	
	public GUISpriteEntity()
	{
		super();
	}

	public void setOwnerID(int ownerID)
	{
		this.ownerID = ownerID;
	}
	public int getOwnerID()
	{
		return ownerID;
	}
	@Override
	public void render(ScreenCanvas canvas, Point2D camera) 
	{
		if (sprite != null) sprite.render(canvas, pos.add(spriteOff));
	}

	// InputGetter overrides
	@Override
	public void onMouseClick(int mX, int mY, int button) {sendUpdate("mouseClick", mX, mY, button);}	
	@Override
	public void onMousePress(int mX, int mY, int button) {sendUpdate("mousePress", mX, mY, button);}
	@Override
	public void onMouseRelease(int mX, int mY, int button) {sendUpdate("mouseRelease", mX, mY, button);}
	@Override
	public void onKeyPress(int code) {sendUpdate("keyPress", code);}
	@Override
	public void onKeyRelease(int code) {sendUpdate("keyRelease", code);}
	
	// CommandRunner override
	@Override
	public boolean runCommand(String... words)
	{
		if (!GUICommandHeader.equals(words[0])) return false; // Not a GUI update

		String type = null;
		if (words[1].length() < 4) return false; // No type given
		else type = words[1].substring(1, words[1].length() - 2);
		
		if (type.equals("mouseClick"))
		{
			if (words.length < 5) return false; // Not enough terms
			onMouseClickGUI(Integer.valueOf(words[2]), Integer.valueOf(words[3]), Integer.valueOf(words[4]));
			return true;
		}
		else if (type.equals("mousePress"))
		{
			if (words.length < 5) return false; // Not enough terms
			onMousePressGUI(Integer.valueOf(words[2]), Integer.valueOf(words[3]), Integer.valueOf(words[4]));
			return true;
		}
		else if (type.equals("mouseRelease"))
		{
			if (words.length < 5) return false; // Not enough terms
			onMouseReleaseGUI(Integer.valueOf(words[2]), Integer.valueOf(words[3]), Integer.valueOf(words[4]));
			return true;
		}
		else if (type.equals("keyPress"))
		{
			if (words.length < 3) return false; // Not enough terms
			onKeyPressGUI(Integer.valueOf(words[2]));
			return true;
		}
		else if (type.equals("keyRelease"))
		{
			if (words.length < 3) return false; // Not enough terms
			onKeyReleaseGUI(Integer.valueOf(words[2]));
			return true;
		}
		else return false;
	}
	
	// New server-side InputGetter proxies
	
	/**
	* Called on server-side when mouse was clicked on client-side.
	* 
	* @param  mX     Mouse X-position (or null if mouse is out of game window)
	* @param  mY     Mouse Y-position (or null if mouse is out of game window)
	* @param  button Was the mouse left-clicked (0), right-clicked (1), or mid-clicked (2)?
	*/
	public abstract void onMouseClickGUI(int mX, int mY, int button);
	/**
	* Called on server-side when mouse was pressed on client-side.
	*
	* @param  mX     Mouse X-position (or null if mouse is out of game window)
	* @param  mY     Mouse Y-position (or null if mouse is out of game window)
	* @param  button Was the mouse left-pressed(0), right-pressed (1), or mid-pressed (2)?
	*/
	public abstract void onMousePressGUI(int mX, int mY, int button);
	/**
	* Called on server-side when mouse was released on client-side.
	* 
	* @param  mX     Mouse X-position (or null if mouse is out of game window)
	* @param  mY     Mouse Y-position (or null if mouse is out of game window)
	* @param  button Was the mouse left-released (0), right-released (1), or mid-released (2)?
	*/
	public abstract void onMouseReleaseGUI(int mX, int mY, int button);
	
	/**
	* Called when key is pressed.
	* Important to note that this is a client-side function, so
	* changes to the object here won't be reflected by the server
	* or any future frames.
	*
	* @param  code The key that was pressed.
	*/
	public abstract void onKeyPressGUI(int code);
	/**
	* Called when key is released.
	* Important to note that this is a client-side function, so
	* changes to the object here won't be reflected by the server
	* or any future frames.
	*
	* @param  code The key that was released.
	*/
	public abstract void onKeyReleaseGUI(int code);
}
