// By Iacon1
// Created 09/15/2021
//

package Modules.Pathfinding;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class AStar implements PathfindingAlgorithm // https://www.redblobgames.com/pathfinding/a-star/introduction.html#astar
{
	private static class PriNode
	{
		public int node_;
		public int pri_;
		
		public PriNode(int node, int pri)
		{
			node_ = node;
			pri_ = pri;
		}
	}
	@Override
	public ArrayList<Integer> findOptimalPath(PathfindingGraph g, int a, int b)
	{
		HashMap<Integer, Integer> prevNode = new HashMap<Integer, Integer>(); // -1 -> no node
		HashMap<Integer, Integer> bestCost = new HashMap<Integer, Integer>(); 
		
		PriorityQueue<PriNode> queue = new PriorityQueue<PriNode>(0, (x, y) -> {return y.pri_ - x.pri_;}); // Init
		queue.add(new PriNode(a, 0));
		prevNode.put(a, -1);
		bestCost.put(a, 0);
		
		boolean found = false;
		
		while (!queue.isEmpty()) // While more to look at
		{
			int node = queue.poll().node_; // Grab the next one
			if (node == b)
			{
				found = true;
				break; // We found the target!
			}
			
			ArrayList<Integer> neighbors = g.getNeighbors(node);
			for (int i = 0; i < neighbors.size(); ++i) // Look at its neighbors
			{
				int cost = bestCost.get(node) + g.getCost(node, i);
				if (bestCost.get(i) == null || bestCost.get(i) < bestCost.get(i)) // Found a record for fastest way to get to i!
				{
					bestCost.put(i, cost);
					prevNode.put(i, node);
					int dist = cost + g.getDist(i, b);
					queue.add(new PriNode(i, dist));
				}
			}	
		}
		if (!found) return null;
		
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		int i = b;
		while (i != a)
		{
			path.add(0, i);
			i = prevNode.get(i);
		}
		return path;
	}

}
