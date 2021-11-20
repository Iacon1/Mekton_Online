// By Iacon1
// Created 06/17/2021
//

package GameEngine.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import GameEngine.GameInfo;
import GameEngine.EntityTypes.GameEntity;
import GameEngine.Net.ConnectionPairThread;
import GameEngine.Net.Server.Server;
import Utils.JSONManager;
import Utils.MiscUtils;

public abstract class GameServer<T extends ConnectionPairThread> extends Server<T>
{
	private Map<String, Account> accounts;

	private void loadAccounts()
	{
		accounts = new HashMap<String, Account>();
		accounts = (HashMap<String, Account>) JSONManager.deserializeJSON(MiscUtils.readText("Local Data/Server/Accounts.json"), accounts.getClass());
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
	
	public boolean addAccount(Account account) // Returns true if successful
	{
		accounts.put(account.username, account);
		saveAccounts();
		return true; // TODO whitelist / blacklist
	}
	public Account getAccount(String username)
	{
		return accounts.get(username);
	}
	public List<Account> getAccounts()
	{
		List<Account> accountList = new ArrayList<Account>();
		
		for (Map.Entry<String, Account> entry : accounts.entrySet())
		{
			accountList.add(entry.getValue());
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
