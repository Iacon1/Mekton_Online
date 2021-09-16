package Modules.HexUtilities.HexStructures.Axial;

import java.util.ArrayList;

import GameEngine.Point2D;
import Modules.HexUtilities.HexConfigManager;
import Modules.HexUtilities.HexDirection;
import Modules.HexUtilities.HexStructures.HexCoord;
import Modules.HexUtilities.HexStructures.HexCoordConverter;

public class AxialHexCoord3D extends AxialHexCoord
	{
		private AxialHexCoord3D convertAxial(AxialHexCoord coord, int z)
		{
			AxialHexCoord3D coord3D = HexCoordConverter.convert(coord, AxialHexCoord.class, AxialHexCoord3D.class);
			coord3D.z_ = z;
			return coord3D;
		}
		public int z_;
		public AxialHexCoord3D(int q, int r, int z)
		{
			super(q, r);
			z_ = z;
		}

		@Override
		public AxialHexCoord3D rAdd(HexCoord delta)
		{
			AxialHexCoord3D delta3D = HexCoordConverter.convert(delta, delta.getClass(), AxialHexCoord3D.class);
			return convertAxial(super.rAdd(delta), this.z_ + delta3D.z_);
		}

		@Override
		public AxialHexCoord3D rMultiply(int factor)
		{
			return convertAxial(super.rMultiply(factor), this.z_ * factor);
		}

		@Override
		public AxialHexCoord3D getUnitVector(HexDirection dir)
		{
			switch (dir)
			{
			case up: return new AxialHexCoord3D(0, 0, 1);
			case down: return new AxialHexCoord3D(0, 0, -1);
				
			default: return convertAxial(super.getUnitVector(dir), 0);
			}
		}
		@Override
		public AxialHexCoord3D getNeighbor(HexDirection dir)
		{
			switch (dir)
			{
			case up: return new AxialHexCoord3D(this.q_, this.r_, this.z_ + 1);
			case down: return new AxialHexCoord3D(this.q_, this.r_, this.z_ - 1);
				
			default: return convertAxial(super.getNeighbor(dir), this.z_);
			}	
		}

		@Override
		public int distance(HexCoord target)
		{
			AxialHexCoord3D target3D = HexCoordConverter.convert(target, target.getClass(), AxialHexCoord3D.class);
			return super.distance(target) + this.z_ - target3D.z_;
		}

		/*@Override
		public ArrayList<AxialHexCoord3D> straightLine(HexCoord target)
		{
			// TODO Auto-generated method stub
			return null;
		}*/

		/*@Override
		public ArrayList<AxialHexCoord3D> withinDistance(int r)
		{
			// TODO Auto-generated method stub
			return null;
		}*/
		
		@Override
		public AxialHexCoord3D fromPixel(Point2D point)
		{
			return convertAxial(super.fromPixel(point), 0);
		}
	}