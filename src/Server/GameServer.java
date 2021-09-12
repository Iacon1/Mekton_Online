// By Iacon1
// Created 06/17/2021
//

package Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import GameEngine.GameInfo;
import GameEngine.EntityTypes.GameEntity;
import Net.ConnectionPairThread;
import Net.Server.Server;
import Utils.JSONManager;
import Utils.MiscUtils;

public abstract class GameServer<A extends Account, T extends ConnectionPairThread> extends Server<T>
{
	private HashMap<String, Account> accounts_;

	private void loadAccounts()
	{
		accounts_ = new HashMap<String, Account>();
		accounts_ = JSONManager.deserializeJSON(MiscUtils.readText("Local Data/Server/Accounts.json"), accounts_.getClass());

		accounts_ = (HashMap<String, Account>) JSONManager.deserializeCollectionJSONList(MiscUtils.readText("Local Data/Server/Accounts.json"), HashMap.class, String.class, Account.class);

		if (accounts_ == null) accounts_ = new HashMap<String, Account>();
	}
	private void saveAccounts()
	{
		MiscUtils.saveText("Local Data/Server/Accounts.json", JSONManager.serializeJSON(accounts_));
	}
	
	public GameServer(T template)
	{	
		super(template);

		accounts_ = new HashMap<String, Account>();

		loadAccounts();
		saveAccounts();
		loadAccounts();
	}
	
	public boolean addAccount(A account) // Returns true if successful
	{
		accounts_.put(account.username, account);
		saveAccounts();
		return true; // TODO whitelist / blacklist
	}
	public A getAccount(String username)
	{
		return (A) accounts_.get(username);
	}
	public ArrayList<A> getAccounts()
	{
		ArrayList<A> accountList = new ArrayList<A>();
		
		for (Map.Entry<String, Account> entry : accounts_.entrySet())
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
		if (accounts_.get(username) == null) return false;
		else return accounts_.get(username).eqHash(password);
	}
}
