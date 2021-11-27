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
import Utils.GappyArrayList;
import Utils.JSONManager;
import Utils.Logging;
import Utils.MiscUtils;

public abstract class GameServer<T extends ConnectionPairThread> extends Server<T>
{
	private GappyArrayList<Account> accounts;

	private void loadAccounts()
	{
		accounts = new GappyArrayList<Account>();
//		accounts = (HashMap<String, Account>) JSONManager.deserializeJSON(MiscUtils.readText("Local Data/Server/Accounts.json"), accounts.getClass());
		accounts = JSONManager.deserializeCollectionJSONList(MiscUtils.readText("Local Data/Server/Accounts.json"), GappyArrayList.class, Account.class);
		
		if (accounts == null) accounts = new GappyArrayList<Account>();
	}
	private void saveAccounts()
	{
		MiscUtils.saveText("Local Data/Server/Accounts.json", JSONManager.serializeJSON(accounts));
	}
	
	public GameServer(Supplier<T> supplier)
	{	
		super(supplier);

		accounts = new GappyArrayList<Account>();

		loadAccounts();
		saveAccounts();
		loadAccounts();
	}
	
	public int addAccount(Account account) // Returns true if successful
	{
		int id = accounts.getFirstGap();
		accounts.add(account);
		saveAccounts();
		return id; // TODO whitelist / blacklist
	}
	public Account getAccount(int id)
	{
		return accounts.get(id);
	}
	public Account getAccount(String username)
	{
		for (int i = 0; i < accounts.size(); ++i)
		{
			if (accounts.get(i) != null && accounts.get(i).username.equals(username))
				return accounts.get(i);
		}
		
		return null;
	}
	public List<Account> getAccounts()
	{
		return accounts; // TODO copy?
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
		if (getAccount(username) == null) return false;
		else return getAccount(username).eqHash(password);
	}
	
	public void runCommand(Account account, String command)
	{
		command = command.toLowerCase();
		Logging.logNotice("User " + account.username + " used command: \"" + command + "\"");
		String[] commands = command.split("; ");
		for (int i = 0; i < commands.length; ++i) account.runCommand(commands[i].split(" "));
	}
	public void runCommand(int id, String command)
	{
		runCommand(getAccount(id), command);
	}
	public void runCommand(String username, String command)
	{
		runCommand(getAccount(username), command);
	}
}
