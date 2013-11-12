package de.bwvaachen.braun.beamoflightpuzzle.model;

import java.awt.Dimension;

public interface IBeamsOfLightPuzzleBoard extends Iterable<ITile> {
	public int getWidth();
	public int getHeight();
	
	public ITile getTileAt(int row, int col)throws IndexOutOfBoundsException;
	public ITile getTileByIndex(int index)throws IndexOutOfBoundsException;
	public Dimension getDimension();
	
	public boolean isPlacementOfTileStatePossible(LightTileState state, int row, int col);
	
	
	
}
