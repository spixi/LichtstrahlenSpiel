package de.bwvaachen.beamoflightgame.model;

import java.io.Serializable;
import java.util.List;

public interface IBeamsOfLightPuzzleBoard extends Iterable<ITile> {
	public int getWidth();
	public int getHeight();
	
	public int getNumOfNumberTiles();
	public ITile getTileAt(int x, int y) throws IndexOutOfBoundsException;
	
	public boolean isPlacementOfTileStatePossible(LightTileState state, int x, int y);
	
	public boolean hasField(int x, int y);
	public void init(int x, int y);

	public void putTile(LightTile tile);
	public void putTile(NumberTile tile);
}
