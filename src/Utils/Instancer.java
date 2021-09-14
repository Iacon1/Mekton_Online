// By Iacon1
// Created 06/06/2021
// For instancing abstract types

package Utils;

public class Instancer<T>
{
	private Class<T> class_;
	
	public Instancer(T template) // TODO remove
	{
		class_ = (Class<T>) template.getClass();
	}
	
	public Instancer(Class<T> tClass)
	{
		class_ = tClass;
	}
	
	public Class<T> getInstClass()
	{
		return class_;
	}
	public T getInstance()
	{
		try {return getInstClass().getDeclaredConstructor().newInstance();}
		catch (Exception e) {Logging.logException(e); return null;}
	}
}
