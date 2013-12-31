package de.bwvaachen.beamoflightgame.model;

import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditListener;

public interface TileComposite {
	public void addChangeListener(ChangeListener cl);
	public void addUndoableEditListener(UndoableEditListener ul);
	public void removeChangeListener(ChangeListener cl);
	public void removeUndoableEditListener(UndoableEditListener ul);
}
