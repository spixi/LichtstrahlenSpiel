package de.bwvaachen.beamoflightgame.model;

import javax.swing.event.ChangeListener;

public interface ITile<T extends ITileState> {
	public int getRow();
	public int getCol();
	public void addChangeListener(ChangeListener cl);
	public void removeChangeListener(ChangeListener cl);
	public T getTileState();
	public boolean isStateChangeable();
}
