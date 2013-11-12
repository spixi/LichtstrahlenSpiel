package de.bwvaachen.braun.beamoflightpuzzle.model;

import java.awt.Dimension;
import java.io.Serializable;

public interface IBeamsOfLightPuzzleBoard extends Iterable<ITile>, Serializable {
	public int getWidth();
	public int getHeight();
	
	public ITile getTileAt(int row, int col)throws IndexOutOfBoundsException;
	public ITile getTileByIndex(int index)throws IndexOutOfBoundsException;
	public Dimension getDimension();
	
	public boolean isPlacementOfTileStatePossible(LightTileState state, int row, int col);
	
	
	
}
