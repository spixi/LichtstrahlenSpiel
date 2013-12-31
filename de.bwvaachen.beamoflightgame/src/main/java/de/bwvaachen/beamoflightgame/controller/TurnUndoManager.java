package de.bwvaachen.beamoflightgame.controller;

import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

public class TurnUndoManager extends UndoManager
{
	private boolean stable       = true;
	private int     numOfMarkers = 0;
	
	public void addMarker()
	{
		UndoableEdit editToBeUndone = editToBeUndone();
		
		if (editToBeUndone instanceof Turn) {
			//Does the last turn already have the flag?
			if((((Turn) editToBeUndone).getFlags() & Turn.FLAG_MARKER) == Turn.FLAG_MARKER) return;
			
			
			((Turn) editToBeUndone).setFlag(Turn.FLAG_MARKER);
			numOfMarkers++;
			
			this.undo();
		}
		//TODO: Wir können keinen Marker vor dem ersten Zug setzen ...
	}
	
	public int getNumOfMarkers() {
		return numOfMarkers;
	}
	
	public void setError()
	{
		UndoableEdit editToBeUndone = editToBeUndone(); 
		if (editToBeUndone instanceof Turn) {
			((Turn) editToBeUndone).setFlag(Turn.FLAG_ERROR);
			stable = false;
		}
	}
	
	public final void undoToLastMarker() throws CannotUndoException
	{
		if(numOfMarkers == 0) return;
		UndoableEdit editToBeUndone;

		while((editToBeUndone = editToBeUndone()) != null && numOfMarkers > 0) {
			if(editToBeUndone instanceof Turn) {
				if ((((Turn) editToBeUndone).getFlags() & Turn.FLAG_MARKER) == Turn.FLAG_MARKER){
					break;
				}
			}
			if(isSignificant()) numOfMarkers--;
			undo();
		}
	}
	
	public void undoToLastStableState() {
		if(stable) return;
		UndoableEdit editToBeUndone;
		while((editToBeUndone = editToBeUndone()) != null) {
			if(editToBeUndone instanceof Turn) {
				if ((((Turn) editToBeUndone).getFlags() & Turn.FLAG_ERROR) == 0){
					stable = true;
					break;
				}
			}
			undo();
		}
	}
	

	/*
	@Override
	public void stateChanged(ChangeEvent e) {
		Pair<ITileState, ITile> change = (Pair<ITileState, ITile>) e.getSource();
		ITileState oldState = change.left;
		ITile      tile     = change.right;
		Turn turn = new Turn(tile.getBoard(), tile.getX(), tile.getY(), tile.getTileState(), oldState);
		if(tile.getBoard().isPlacementOfTileStatePossible(tile.getTileState(), tile.getX(), tile.getY())) {
			stable = false;
		}
		if(!stable) turn.setFlag(Turn.FLAG_ERROR);
		this.addEdit(turn);
	}
	*/
	
}
