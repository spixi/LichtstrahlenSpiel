package de.bwvaachen.beamoflightgame.controller;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

import de.bwvaachen.beamoflightgame.helper.Pair;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.ITileState;

public class TurnUndoManager extends UndoManager implements ChangeListener
{
	private boolean stable       = true;
	private int     numOfMarkers = 0;
	
	public final void undoToLastMarker() throws CannotUndoException
	{
		if(numOfMarkers == 0) return;
		UndoableEdit lastEdit;

		while((lastEdit = lastEdit()) != null) {
			if(lastEdit instanceof Turn) {
				if ((((Turn) lastEdit).getFlags() & Turn.FLAG_MARKER) == Turn.FLAG_MARKER){
					break;
				}
			}
			undo();
		}
	}
	
	private final void undoToStableState() throws CannotUndoException
	{
		UndoableEdit lastEdit;
		while((lastEdit = lastEdit()) != null) {
			if(lastEdit instanceof Turn) {
				if ((((Turn) lastEdit).getFlags() & Turn.FLAG_ERROR) == 0){
					break;
				}
			}
			undo();
		}
	}
	
	public int getNumOfMarkers() {
		return numOfMarkers;
	}
	
	public void addMarker()
	{
		UndoableEdit lastEdit = lastEdit(); 
		if (lastEdit instanceof Turn) {
			((Turn) lastEdit).setFlag(Turn.FLAG_MARKER);
			numOfMarkers++;
		}
	}
	
	public void setError()
	{
		UndoableEdit lastEdit = lastEdit(); 
		if (lastEdit instanceof Turn) {
			((Turn) lastEdit).setFlag(Turn.FLAG_ERROR);
			stable = false;
		}
	}
	
	
	public void undoToLastStableState() {
		if(stable) return;
		UndoableEdit lastEdit;
		while((lastEdit = lastEdit()) != null) {
			if(lastEdit instanceof Turn) {
				if ((((Turn) lastEdit).getFlags() & Turn.FLAG_ERROR) == 0){
					stable = true;
					break;
				}
			}
			undo();
		}
	}

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
	
}
