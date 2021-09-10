// By Iacon1
// Created 06/05/2021
// For serializing & deserializing GameWorld

package Utils.GSONConfig;

import java.io.IOException;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import GameEngine.GameInfo;
import Utils.Logging;
import Utils.GSONConfig.Delegation.AdapterDelegator;
import Utils.GSONConfig.Delegation.DelegatingAdapter;
import Utils.GSONConfig.Delegation.DelegatingFactory;

public class GameWorldAdapter<T> extends DelegatingAdapter<T>
{
	public GameWorldAdapter(AdapterDelegator delegator, TypeToken<T> type)
	{
		super(delegator, type);
	}

	@Override
	public T read(JsonReader arg0) throws IOException // Assumed T = GameWorld
	{
		GameInfo world = null;
	    try
	    {
	    	world = (GameInfo) delegator_.getAdapter(GameInfo.class.getCanonicalName()).read(arg0);
	    	
	    	for (int i = 0; i < world.getEntities().size(); ++i)
	    	{
	    		world.getEntities().get(i).setWorld(world);
	    	}
	    }
	    catch (Exception e) {Logging.logException(e);}
		return (T) world;
	}

	@Override
	public void write(JsonWriter arg0, T arg1) throws IOException
	{
		delegator_.getAdapter(GameInfo.class.getCanonicalName()).write(arg0, arg1);
	}
	
	public static class Factory extends DelegatingFactory<GameInfo>
	{
		Factory()
		{
			super(GameInfo.class);
		}

		@Override
		protected <J> DelegatingAdapter<J> getAdapter(AdapterDelegator delegator, TypeToken<J> type)
		{
			return new GameWorldAdapter<J>(delegator, type);
		}		
	}
}
