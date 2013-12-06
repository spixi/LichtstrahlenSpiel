package de.bwvaachen.beamoflightgame.controller;

import javax.swing.undo.UndoManager;

public class TurnUndoManager extends UndoManager
{
	boolean hasMarker = false;
	
	public boolean undoToMarker() 
	{
		if(!hasMarker)
			return false;
		
		this.undoTo(Marker.instance);
		return true;
	}
	
	public void addMarker()
	{
		this.addEdit(Marker.instance);
		hasMarker = true;
	}
	
}
