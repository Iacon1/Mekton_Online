// By Iacon1
// Created 06/17/2021
//

package GameEngine.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import GameEngine.GameInfo;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.Net.ConnectionPairThread;
import GameEngine.Net.Server.Server;
import Utils.JSONManager;
import Utils.MiscUtils;

public abstract class GameServer<A extends Account, T extends ConnectionPairThread> extends Server<T>
{
	private HashMap<String, Account> accounts;

	private void loadAccounts()
	{
		accounts = new HashMap<String, Account>();
		accounts = JSONManager.deserializeJSON(MiscUtils.readText("Local Data/Server/Accounts.json"), accounts.getClass());

		accounts = (HashMap<String, Account>) JSONManager.deserializeCollectionJSONList(MiscUtils.readText("Local Data/Server/Accounts.json"), HashMap.class, String.class, Account.class);

		if (accounts == null) accounts = new HashMap<String, Account>();
	}
	private void saveAccounts()
	{
		MiscUtils.saveText("Local Data/Server/Accounts.json", JSONManager.serializeJSON(accounts));
	}
	
	public GameServer(Supplier<T> supplier)
	{	
		super(supplier);

		accounts = new HashMap<String, Account>();

		loadAccounts();
		saveAccounts();
		loadAccounts();
	}
	
	public boolean addAccount(A account) // Returns true if successful
	{
		accounts.put(account.username, account);
		saveAccounts();
		return true; // TODO whitelist / blacklist
	}
	public A getAccount(String username)
	{
		return (A) accounts.get(username);
	}
	public ArrayList<A> getAccounts()
	{
		ArrayList<A> accountList = new ArrayList<A>();
		
		for (Map.Entry<String, Account> entry : accounts.entrySet())
		{
			accountList.add((A) entry.getValue());
		}
		
		return accountList;
	}
	public void update()
	{
		for (int i = 0; i < GameInfo.getWorld().getEntities().size(); ++i)
		{
			GameEntity entity = GameInfo.getWorld().getEntities().get(i);
			if (entity != null) entity.update();
		}
	}
	public boolean login(String username, String password)
	{
		if (accounts.get(username) == null) return false;
		else return accounts.get(username).eqHash(password);
	}
}
