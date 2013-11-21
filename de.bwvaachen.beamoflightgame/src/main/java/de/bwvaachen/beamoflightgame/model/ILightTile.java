package de.bwvaachen.beamoflightgame.model;

import javax.swing.undo.UndoableEdit;

public interface ILightTile extends ITile<LightTileState> {
	public void setState(LightTileState state);
}
