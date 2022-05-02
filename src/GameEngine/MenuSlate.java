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
	// Functions
	public void setCells(int w, int h);
	
	// Prints info
	public void addInfo(int x, int y, String label, int labelLength, int contentLength,
			InfoFunction<String> function);
	// Text bar
	public void addTextbar(int x, int y, String label, int labelLength, int maxLength, int contentLength,
			DataFunction<String> function);
	// Integer wheel
	public void addIntegerWheel(int x, int y, String label, int labelLength, int min, int max, int contentLength,
			DataFunction<Integer> function);
	// Double wheel
	public void addDoubleWheel(int x, int y, String label, int labelLength, double min, double max, int digits, int contentLength,
			DataFunction<Double> function);
	// Check box
	public void addCheckbox(int x, int y, String label, int labelLength, int contentLength,
			DataFunction<Boolean> function);
	// Button
	public void addButton(int x, int y, String label, int w, int h,
			ButtonFunction function);
	// Option list
	public <T> void addOptions(int x,  int y, String label, int labelLength, int contentLength,
			String[] optionLabels, T[] options, DataFunction<T> function);
	<T> void addOptions(int x, int y, String label, int labelLength, int contentLength, int contentHeight,
			String[] optionLabels, T[] options, DataFunction<T> function);
	public <E extends Enum<E>> void addOptions(int x, int y, String label, int labelLength, int contentLength,
			E[] options, DataFunction<E> function);
	public <E extends Enum<E>> void addOptions(int x, int y, String label, int labelLength, int contentLength, int contentHeight,
			E[] options, DataFunction<E> function);
	// TODO tree support
	// Section
	public MenuSlate addSubSlate(int x, int y, int w, int h,
			DataFunction<MenuSlate> function);

	
}
