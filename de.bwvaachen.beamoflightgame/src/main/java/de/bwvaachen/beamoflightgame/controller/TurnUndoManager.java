package de.bwvaachen.beamoflightgame.controller;

import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

public class TurnUndoManager extends UndoManager
{
	boolean hasMarker = false;
	
	public boolean undoToMarker() throws CannotUndoException
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
	
	public void undoToLastStableState() {
		UndoableEdit lastEdit = lastEdit();
		
		while(lastEdit != null) {
			if(lastEdit instanceof Turn) {
				if ((((Turn) lastEdit).getFlags() & Turn.FLAG_ERROR) == 0){
					break;
				}
			}
			undo();
		}
	}
	
}
