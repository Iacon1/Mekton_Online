// By Iacon1
// Created 04/16/2022
// Interface that implements GUI functions.
/* Works with a grid system.
 * 
 * Components will call getValue() when externally updated and call setValue when internally updated.
 */


package GameEngine.MenuStuff;

import GameEngine.EntityTypes.Alignable;
import GameEngine.Graphics.Sprite;

public interface MenuSlate
{
	// Interfaces
	public interface InfoFunction<T> {public T getValue();}
	public interface DataFunction<T> extends InfoFunction<T> {public void setValue(T data);}
	public interface ButtonFunction {public void onClick();}
	public interface TreeFunction extends DataFunction<Object[]> {};
	public interface ComponentHandle
	{
		int getX();
		int getY();
		int getW();
		int getH();
	
		void setX(int x);
		void setY(int y);
		void setW(int w);
		void setH(int h);
	};
	public interface SubHandle extends ComponentHandle {public void removeSlate(); public void swapSlate(MenuSlate newSlate);}
	public interface TabHandle extends ComponentHandle {public void setTab(String name, MenuSlate slate); public void removeTab(String name);}
	public class DataFunctionWrapper<T> implements DataFunction<T>
	{
		private DataFunction<T> function;
		
		public DataFunctionWrapper(DataFunction<T> function)
		{
			this.function = function;
		}
		@Override public T getValue() {return function.getValue();}
		@Override public void setValue(T data)
		{
			if (function.getValue() != null && !function.getValue().equals(data)) function.setValue(data);
			else if (function.getValue() == null) function.setValue(data);
		}
		
	}
	
	// Functions
	public void setCells(int w, int h);
	
	// Prints info
	public ComponentHandle addInfo(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, int contentLength, int h,
			InfoFunction<String> function);
	public ComponentHandle addInfo(int x, int y, String label, int labelLength, int contentLength, int h,
			InfoFunction<String> function);
	/** Creates a textbar the user can edit, locked relative to another component.
	 *  @param parent The component to lock position relative to.
	 *  @param anchorPoint the part of said component to align to. 
	 *  @param x The x-coord of the cell the top-left corner is in.
	 *  @param y The y-coord of the cell the top-left corner is in.
	 *  @param label The label to display.
	 *  @param labelLength The length of the label, in cells.
	 *  @param maxLength The max length of the text, in characters.
	 *  @param contentLength The length of the textbox, in cells.
	 *  @param h The height of the label and textbox, in cells.
	 *  @param function What can override the text and what to do when the text is changed by the user.
	 */
	public ComponentHandle addTextbar(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, int maxLength, int contentLength, int h,
			DataFunction<String> function);
	/** Creates a textbar the user can edit.
	 *  @param x The x-coord of the cell the top-left corner is in.
	 *  @param y The y-coord of the cell the top-left corner is in.
	 *  @param label The label to display.
	 *  @param labelLength The length of the label, in cells.
	 *  @param maxLength The max length of the text, in characters.
	 *  @param contentLength The length of the textbox, in cells.
	 *  @param h The height of the label and textbox, in cells.
	 *  @param function What can override the text and what to do when the text is changed by the user.
	 */
	public ComponentHandle addTextbar(int x, int y, String label, int labelLength, int maxLength, int contentLength, int h,
			DataFunction<String> function);
	
	// Integer wheel
	public ComponentHandle addIntegerWheel(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, int min, int initial, int max, int contentLength, int h,
			DataFunction<Integer> function);
	public ComponentHandle addIntegerWheel(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, int min, int max, int contentLength, int h,
			DataFunction<Integer> function);
	public ComponentHandle addIntegerWheel(int x, int y, String label, int labelLength, int min, int initial, int max, int contentLength, int h,
			DataFunction<Integer> function);
	public ComponentHandle addIntegerWheel(int x, int y, String label, int labelLength, int min, int max, int contentLength, int h,
			DataFunction<Integer> function);
	// Double wheel
	public ComponentHandle addDoubleWheel(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, double min, double initial, double max, int digits, int contentLength, int h,
			DataFunction<Double> function);
	public ComponentHandle addDoubleWheel(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, double min, double max, int digits, int contentLength, int h,
			DataFunction<Double> function);
	public ComponentHandle addDoubleWheel(int x, int y, String label, int labelLength, double min, double initial, double max, int digits, int contentLength, int h,
			DataFunction<Double> function);
	public ComponentHandle addDoubleWheel(int x, int y, String label, int labelLength, double min, double max, int digits, int contentLength, int h,
			DataFunction<Double> function);
	// Check box
	public ComponentHandle addCheckbox(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, int contentLength, int h,
			DataFunction<Boolean> function);
	public ComponentHandle addCheckbox(int x, int y, String label, int labelLength, int contentLength, int h,
			DataFunction<Boolean> function);
	// Button
	public ComponentHandle addButton(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int w, int h,
			ButtonFunction function);
	public ComponentHandle addButton(int x, int y, String label, int w, int h,
			ButtonFunction function);
	// Option list
	public <T> ComponentHandle addOptions(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x,  int y, String label, int labelLength, int contentLength, int h,
			String[] optionLabels, T[] options, DataFunction<T> function);
	<T> ComponentHandle addOptions(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, int contentLength, int labelHeight, int contentHeight,
			String[] optionLabels, T[] options, DataFunction<T> function);
	public <E extends Enum<E>> ComponentHandle addOptions(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, int contentLength, int h,
			E[] options, DataFunction<E> function);
	public <E extends Enum<E>> ComponentHandle addOptions(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, String label, int labelLength, int contentLength, int labelHeight, int contentHeight,
			E[] options, DataFunction<E> function);
	public <T> ComponentHandle addOptions(int x,  int y, String label, int labelLength, int contentLength, int h,
			String[] optionLabels, T[] options, DataFunction<T> function);
	<T> ComponentHandle addOptions(int x, int y, String label, int labelLength, int contentLength, int labelHeight, int contentHeight,
			String[] optionLabels, T[] options, DataFunction<T> function);
	public <E extends Enum<E>> ComponentHandle addOptions(int x, int y, String label, int labelLength, int contentLength, int h,
			E[] options, DataFunction<E> function);
	public <E extends Enum<E>> ComponentHandle addOptions(int x, int y, String label, int labelLength, int contentLength, int labelHeight, int contentHeight,
			E[] options, DataFunction<E> function);
	// TODO tree support
	// Section
	public SubHandle addSubSlate(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, int w, int h,
			MenuSlate subSlate);
	public SubHandle addSubSlate(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y,
			MenuSlate subSlate);
	public SubHandle addSubSlate(int x, int y, int w, int h,
			MenuSlate subSlate);
	public SubHandle addSubSlate(int x, int y,
			MenuSlate subSlate);
	public TabHandle addTabbedSection(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, int w, int h);
	public TabHandle addTabbedSection(int x, int y, int w, int h);
	public ComponentHandle addSprite(ComponentHandle parent, Alignable.AlignmentPoint anchorPoint,
			int x, int y, int w, int h,
			InfoFunction<Sprite> function);
	public ComponentHandle addSprite(int x, int y, int w, int h,
			InfoFunction<Sprite> function);
	public void clear();
	
	public int getHCells();
	public int getVCells();
	
}
