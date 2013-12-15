package de.bwvaachen.beamoflightgame.model;

import java.io.Serializable;
import java.util.List;

import de.bwvaachen.beamoflightgame.helper.ITileVisitor;
import de.bwvaachen.beamoflightgame.helper.Visitable;

public interface IBeamsOfLightPuzzleBoard extends Iterable<ITile> {
	public int getWidth();
	public int getHeight();
	
	public int getNumOfNumberTiles();
	public ITile getTileAt(int x, int y) throws IndexOutOfBoundsException;
	
	public boolean isPlacementOfTileStatePossible(LightTileState state, int x, int y);
	
	public boolean hasField(int x, int y);
	public void init(int x, int y);
    
	public void putTile(ITile tile);
	
	public void enqueueTile(ITile tile);
	public void flush();
}
