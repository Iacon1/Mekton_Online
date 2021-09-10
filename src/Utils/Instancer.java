// By Iacon1
// Created 06/06/2021
// For instancing abstract types

package Utils;

public class Instancer<T>
{
	private T template_;
	
	public Instancer(T template)
	{
		template_ = template;
	}
	
	public Class<T> getInstClass()
	{
		return (Class<T>) template_.getClass();
	}
	public T getInstance()
	{
		try {return getInstClass().getDeclaredConstructor().newInstance();}
		catch (Exception e) {Logging.logException(e); return null;}
	}
}
