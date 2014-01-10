package de.bwvaachen.beamoflightgame.model;

import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.Visitable;

public interface ITile<T extends ITileState> extends Visitable, TileComposite {
	public IBeamsOfLightPuzzleBoard getBoard();
	public T getTileState();
	public BoardTraverser getTraverser();
	public int getX();
	public int getY();
	public boolean isStateChangeable();
	public void put();
	public void setUndoMode(boolean onOff);
}
