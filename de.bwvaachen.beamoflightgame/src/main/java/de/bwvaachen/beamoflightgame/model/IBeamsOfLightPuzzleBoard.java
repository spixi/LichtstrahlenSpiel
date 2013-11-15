package de.bwvaachen.beamoflightgame.model;

import java.io.Serializable;

public interface IBeamsOfLightPuzzleBoard extends Iterable<ITile>, Serializable {
	public int getWidth();
	public int getHeight();
	
	public ITile getTileAt(int row, int col) throws IndexOutOfBoundsException;
	
	public boolean isPlacementOfTileStatePossible(LightTileState state, int row, int col);
}
