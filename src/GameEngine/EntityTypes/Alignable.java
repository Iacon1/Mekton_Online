// By Iacon1
// Created 09/17/2021
// Something that can be aligned with other Alignables

package GameEngine.EntityTypes;

import GameEngine.IntPoint2D;

public interface Alignable
{
	public enum AlignmentPoint // Corners and edges that can be aligned.
	{
		northWest,
		north,
		northEast,
		
		west,
		center,
		east,
		
		southWest,
		south,
		southEast
	}
	
	/** Gets the coordinate of one of our alignment points.
	 * 
	 * @param point Point to get coordinate of.
	 */
	public IntPoint2D getAlignmentPoint(AlignmentPoint point);
	/** Aligns us with another Alignable. 
	 * 
	 * @param point Which of our alignment points to align.
	 * @param target Target to align with; If null then assumes the frame itself.
	 * @param targetPoint Which of their alignment points to align with.
	 * @return none.
	 */
	public void align(AlignmentPoint point, Alignable target, AlignmentPoint targetPoint);
}
