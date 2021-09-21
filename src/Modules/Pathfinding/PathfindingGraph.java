// By Iacon1
// Created 09/15/2021
// Graph for storing pathfinding data
// If cost is -1 then assume blocked

package Modules.Pathfinding;

import java.util.ArrayList;
import java.util.HashSet;

public class PathfindingGraph
{
	protected static interface Cost
	{
		public int cost(int a, int b);
	}
	private Cost costFunc_;
	
	protected static interface Distance
	{
		public int cost(int a, int b);
	}
	private Distance distFunc_;
	
	private static class Node
	{	
		public HashSet<Integer> neighbors_;
		
		public Node()
		{
			neighbors_ = new HashSet<Integer>();
		}
		
		public void addNeighbor(int neighbor)
		{
			neighbors_.add(neighbor);
		}
		public void removeNeighbor(int neighbor)
		{
			neighbors_.remove(neighbor);
		}
	}
	
	private ArrayList<Node> nodes_;
	
	public PathfindingGraph(Cost costFunc, Distance distFunc)
	{
		costFunc_ = costFunc;
		distFunc_ = distFunc;
		nodes_ = new ArrayList<Node>();
	}
	
	/** Resets the graph for a set size.
	 * 
	 *  @param size Size to reset to.
	 */
	public void setSize(int size)
	{
		nodes_ = new ArrayList<Node>();
		for (int i = 0; i < size; ++i)
		{
			nodes_.add(new Node());
		}
	}
	
	/** Adds a link between a & b.
	 * 
	 * @ param a First node to link.
	 * @ param b Second node to link.
	 */
	public void addLink(int a, int b)
	{
		if (a >= nodes_.size() || b >= nodes_.size()) return;
		
		nodes_.get(a).addNeighbor(b);
		nodes_.get(b).addNeighbor(a);
	}
	
	/** Cuts a link between a & b.
	 * 
	 * @ param a First node to cut.
	 * @ param b Second node to cut.
	 */
	public void cutLink(int a, int b)
	{
		if (a >= nodes_.size() || b >= nodes_.size()) return;
		
		nodes_.get(a).removeNeighbor(b);
		nodes_.get(b).removeNeighbor(a);
	}
	
	/** Returns the list of links to node.
	 * 
	 * @param node The node to get neighbors of.
	 */
	public ArrayList<Integer> getNeighbors(int node)
	{
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		neighbors.addAll(nodes_.get(node).neighbors_);
		return neighbors;
	}
	
	/** Cost of travelling from two neighboring nodes
	 * 
	 * @param a First node.
	 * @param b Second node.
	 */
	public int getCost(int a, int b)
	{
		return costFunc_.cost(a, b);
	}
	
	/** Distance, a seperate form of cost for farther-away nodes
	 * 
	 * @param a First node.
	 * @param b Second node.
	 */
	public int getDist(int a, int b)
	{
		return distFunc_.cost(a, b);
	}
}
