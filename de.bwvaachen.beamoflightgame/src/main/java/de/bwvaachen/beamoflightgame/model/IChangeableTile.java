package de.bwvaachen.beamoflightgame.model;

import javax.swing.undo.StateEditable;

public interface IChangeableTile<T extends ITileState> extends ITile<T>, StateEditable {
	public void setState(T state, boolean significant);
}
