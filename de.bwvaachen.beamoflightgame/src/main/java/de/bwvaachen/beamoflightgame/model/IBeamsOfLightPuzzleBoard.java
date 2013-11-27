package de.bwvaachen.beamoflightgame.model;

import java.io.Serializable;
import java.util.List;

public interface IBeamsOfLightPuzzleBoard extends Iterable<ITile> {
	public int getWidth();
	public int getHeight();
	
	public int getNumOfNumberTiles();
	
	public ITile getTileAt(int row, int col) throws IndexOutOfBoundsException;
	
	public boolean isPlacementOfTileStatePossible(LightTileState state, int row, int col);
	public ITile getTileByIndex(long index);
	public boolean hasField(int row, int col);
	
	public void init(int rows, int cols, Iterable<NumberTile> nrs);
}
