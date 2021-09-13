package Modules.HexUtilities.HexStructures;
public class AxialCoord3D extends AxialCoord
	{
		public int z_;
		public AxialCoord3D(int q, int r, int z)
		{
			super(q, r);
			z_ = z;
		}
	}