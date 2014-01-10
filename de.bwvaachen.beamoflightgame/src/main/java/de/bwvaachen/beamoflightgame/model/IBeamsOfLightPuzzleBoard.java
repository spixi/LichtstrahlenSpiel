package de.bwvaachen.beamoflightgame.model;

import javax.swing.event.ChangeListener;

public interface IBeamsOfLightPuzzleBoard extends Iterable<ITile>, TileComposite {
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
