// By Iacon1
// Created 04/16/2022
// Interface that implements GUI functions.
/* Works with a grid system.
 * 
 * Components will call getValue() when externally updated and call setValue when internally updated.
 */


package GameEngine;

public interface MenuSlate
{
	// Interfaces
	public interface InfoFunction<T> {public T getValue();}
	public interface DataFunction<T> extends InfoFunction<T> {public void setValue(T data);}
	public interface ButtonFunction {public void onClick();}
	public interface TreeFunction extends DataFunction<Object[]> {};
	public interface TabHandle {public void addTab(String name, MenuSlate slate); public void removeTab(String name);}
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
			if (!function.getValue().equals(data)) function.setValue(data);
		}
		
	}
	// Functions
	public void setCells(int w, int h);
	
	// Prints info
	public void addInfo(int x, int y, String label, int labelLength, int contentLength, int h,
			InfoFunction<String> function);
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
	public void addTextbar(int x, int y, String label, int labelLength, int maxLength, int contentLength, int h,
			DataFunction<String> function);
	// Integer wheel
	public void addIntegerWheel(int x, int y, String label, int labelLength, int min, int max, int contentLength, int h,
			DataFunction<Integer> function);
	// Double wheel
	public void addDoubleWheel(int x, int y, String label, int labelLength, double min, double max, int digits, int contentLength, int h,
			DataFunction<Double> function);
	// Check box
	public void addCheckbox(int x, int y, String label, int labelLength, int contentLength, int h,
			DataFunction<Boolean> function);
	// Button
	public void addButton(int x, int y, String label, int w, int h,
			ButtonFunction function);
	// Option list
	public <T> void addOptions(int x,  int y, String label, int labelLength, int contentLength, int h,
			String[] optionLabels, T[] options, DataFunction<T> function);
	<T> void addOptions(int x, int y, String label, int labelLength, int contentLength, int labelHeight, int contentHeight,
			String[] optionLabels, T[] options, DataFunction<T> function);
	public <E extends Enum<E>> void addOptions(int x, int y, String label, int labelLength, int contentLength, int h,
			E[] options, DataFunction<E> function);
	public <E extends Enum<E>> void addOptions(int x, int y, String label, int labelLength, int contentLength, int labelHeight, int contentHeight,
			E[] options, DataFunction<E> function);
	// TODO tree support
	// Section
	public void addSubSlate(int x, int y, int w, int h,
			MenuSlate subSlate);
	public TabHandle addTabbedSection(int x, int y, int w, int h);
	
}
