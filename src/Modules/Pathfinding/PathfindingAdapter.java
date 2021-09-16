// By Iacon1
// Created 09/15/2021
// Adapts pathfinding for other things than integers

package Modules.Pathfinding;

import java.util.ArrayList;
import java.util.function.Supplier;

public class PathfindingAdapter<T, A extends PathfindingAlgorithm>
{
	private static interface Cost<T>
	{
		public int cost(T a, T b);
	}
	private Cost<T> costFunc_;
	private static interface Distance<T>
	{
		public int cost(T a, T b);
	}
	private Distance<T> distFunc_;
	private static interface Neighbors<T>
	{
		public ArrayList<T> neighbors(T value);
	}
	private Neighbors<T> nearFunc_;
	private Supplier<A> supplier_;
	
	public PathfindingAdapter(Cost<T> costFunc, Distance<T> distFunc, Neighbors<T> nearFunc, Supplier<A> supplier)
	{
		costFunc_ = costFunc;
		distFunc_ = distFunc;
		nearFunc_ = nearFunc;
		supplier_ = supplier;
	}
	
	public ArrayList<T> findOptimalPath(ArrayList<T> list, T a, T b)
	{
		PathfindingGraph graph = new PathfindingGraph(
				(x, y) -> (costFunc_.cost(list.get(x), list.get(y))),
				(x, y) -> (distFunc_.cost(list.get(x), list.get(y)))
				);
		
		graph.setSize(list.size());
		for (int i = 0; i < list.size(); ++i)
		{
			ArrayList<T> neighbors = nearFunc_.neighbors(list.get(i));
			for (int j = 0; j < neighbors.size(); ++j)
			{
				graph.addLink(i, list.indexOf((neighbors.get(j))));
			}
		}
		
		A algo = supplier_.get();
		ArrayList<Integer> path = algo.findOptimalPath(graph, list.indexOf(a), list.indexOf(b));
		ArrayList<T> pathValue = new ArrayList<T>();
		for (int i = path.size() - 1; i >= 0; --i)
		{
			pathValue.add(list.get(path.get(i)));
		}
		
		return pathValue;
	}
}
