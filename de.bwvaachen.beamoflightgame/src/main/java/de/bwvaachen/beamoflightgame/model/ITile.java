package de.bwvaachen.beamoflightgame.model;

import javax.swing.event.ChangeListener;

import de.bwvaachen.beamoflightgame.helper.Visitable;

public interface ITile<T extends ITileState> extends Visitable {
	public int getX();
	public int getY();
	public IBeamsOfLightPuzzleBoard getBoard();
	public void addChangeListener(ChangeListener cl);
	public void removeChangeListener(ChangeListener cl);
	public T getTileState();
	public boolean isStateChangeable();
	public boolean equals(ITile t);
}
