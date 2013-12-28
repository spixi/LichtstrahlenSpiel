package de.bwvaachen.beamoflightgame.model;

import java.io.Serializable;
import java.util.List;

import javax.swing.event.ChangeListener;

import de.bwvaachen.beamoflightgame.helper.ITileVisitor;
import de.bwvaachen.beamoflightgame.helper.Visitable;

public interface IBeamsOfLightPuzzleBoard extends Iterable<ITile> {
	public int getWidth();
	public int getHeight();
	
	public int getNumOfNumberTiles();
	public ITile getTileAt(int x, int y) throws IndexOutOfBoundsException;
	
	public boolean isPlacementOfTileStatePossible(ITileState state, int x, int y);
	
	public boolean hasField(int x, int y);
	public void init(int x, int y);
    
	public void putTile(ITile tile);
	
	public void enqueueTile(ITile tile);
	public void flush();
	
	public void addChangeListener(ChangeListener cl);
	public void removeChangeListener(ChangeListener cl);
}
