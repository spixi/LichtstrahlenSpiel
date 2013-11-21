package de.bwvaachen.beamoflightgame.model;

import java.io.Serializable;

import javax.swing.undo.StateEditable;

public interface ITile<T extends ITileState> extends Serializable, StateEditable {
	public int getRow();
	public int getCol();
	public T getTileState();
}
