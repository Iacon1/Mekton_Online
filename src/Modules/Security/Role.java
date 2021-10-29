// By Iacon1
// Created 10/27/2021
// A security role, which has things it is allowed to do and not allowed to do.

package Modules.Security;

import java.util.ArrayList;

public class Role
{
	private String name;
	private ArrayList<String> permissions; // Things it is allowed to do
	
	public Role(String name)
	{
		this.name = name;
		this.permissions = new ArrayList<String>();
	}
	
	public String getName()
	{
		return name;
	}
	public void givePermission(String action)
	{
		if (!hasPermission(action)) permissions.add(action);
	}
	public boolean hasPermission(String action)
	{
		return permissions.contains(action);
	}
}
