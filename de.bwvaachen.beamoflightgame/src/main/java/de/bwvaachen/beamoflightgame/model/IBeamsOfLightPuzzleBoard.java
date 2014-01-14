package de.bwvaachen.beamoflightgame.model;

import java.io.Serializable;

import javax.swing.event.ChangeListener;

import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

public interface IBeamsOfLightPuzzleBoard extends Iterable<ITile>, TileComposite , Serializable {
	public void enqueueTile(ITile tile);
	
	public void flush();
	public int getHeight();
	
	public int getNumOfNumberTiles();
	
	public ITile getTileAt(int x, int y) throws IndexOutOfBoundsException;
	public int getWidth();
    
	public boolean hasField(int x, int y);
	
	public void init(int x, int y);
	public boolean isPlacementOfTileStatePossible(ITileState state, int x, int y);
	
	public void putTile(ITile tile);
	
}
