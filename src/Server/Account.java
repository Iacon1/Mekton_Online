// By Iacon1
// Created 05/10/2021
// Account data

package Server;

public class Account
{
	public String username;
	public int possessee; // Entity our user is possessing
	
	private int hash;
	
	public Account()
	{
		username = null;
		possessee = -1;
		hash = -1;
	}
	
	public void setHash(String password) // Sets the hash
	{
		hash = password.hashCode(); // More secure probably possible
	}
	public boolean eqHash(String password) // Is the given password equal to our hash?
	{
		return password.hashCode() == hash;
	}
}
