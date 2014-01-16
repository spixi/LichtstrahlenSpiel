package de.bwvaachen.beamoflightgame.controller;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import static de.bwvaachen.beamoflightgame.controller.Turn.*;

public class TurnUndoManager extends UndoManager
{
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 4610323453539735962L;
	private boolean stable       = true;
	private HashSet<ChangeListener> changeListeners = new HashSet<ChangeListener>();
	
	public void addMarker()
	{
		UndoableEdit editToBeUndone = editToBeUndone();
		
		if (editToBeUndone instanceof Turn) {
			//Does the last turn already have the flag?
			if(((Turn) editToBeUndone).hasFlag(FLAG_MARKER)) return;
			((Turn) editToBeUndone).setFlag(FLAG_MARKER);
		}
		//TODO: Wir können keinen Marker vor dem ersten Zug setzen ...
	}
	
	public void setError()
	{
		UndoableEdit editToBeUndone = editToBeUndone(); 
		if (editToBeUndone instanceof Turn) {
			((Turn) editToBeUndone).setFlag(FLAG_ERROR);
			stable = false;
		}
	}
	
	public final void undoToLastMarker() throws CannotUndoException
	{
		Turn lastMarker = findLastMarker();
		if(lastMarker == null) return;
		while (editToBeUndone() != lastMarker) {
			System.out.println(editToBeUndone() );
			undo();
		}
	}
	
	private Turn findLastMarker() {
		UndoableEdit editToBeUndone;
		int maxIndex;
		
		editToBeUndone = this.editToBeUndone();
		if(editToBeUndone != null) {
			maxIndex = this.edits.indexOf(editToBeUndone);
		}
		else {
			maxIndex = this.edits.indexOf(this.lastEdit());
		}
		
		//the current index could be a marker itself
		// 
		if(maxIndex >= 1) maxIndex--;
		
		for(int i=maxIndex; i>=0; i--) {
			UndoableEdit edit = edits.get(i);
			if(edit instanceof Turn) {
				Turn turn = (Turn) edit;
				if (turn.hasFlag(FLAG_MARKER)) {
					return turn;
				}
			}
		}
		
		return null;
	}
	
	public final void deleteLastMarker() throws CannotUndoException {
		Turn lastMarker = findLastMarker();
		if(lastMarker != null) lastMarker.unsetFlag(FLAG_MARKER);
	}
	
	public void deleteAllMarker () throws CannotUndoException {
		LinkedList<Turn> turns = (LinkedList<Turn>) getTurns() ;
		for ( Turn currentTurn : turns ) {
			currentTurn . unsetFlag ( FLAG_MARKER ) ;
		} // for ( Turn currentTurn : turns )
		
	} // public void deleteAllMarker ()
	
	
	
	public void undoToLastStableState() {
		if(stable) return;
		UndoableEdit editToBeUndone;
		while((editToBeUndone = editToBeUndone()) != null) {
			if(editToBeUndone instanceof Turn) {
				if ( ! ((Turn) editToBeUndone).hasFlag(FLAG_ERROR) ){
					stable = true;
					break;
				}
			}
			undo();
		}
	}
	
	public boolean addEdit(UndoableEdit anEdit) {
		boolean success = (super.addEdit(anEdit));
			if(success) notifyChangeListeners();
		return success;
	}
	
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		notifyChangeListeners();
	}
	
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		notifyChangeListeners();
	}
	
	private void notifyChangeListeners() {
		for(ChangeListener cl: changeListeners) {
			cl.stateChanged(new ChangeEvent(this));
		}
	}

	public void addChangeListener(ChangeListener l) {
		changeListeners.add(l);
	}
	
	public void removeChangeListener(ChangeListener l) {
		changeListeners.remove(l);
	}
	
	//TODO ZipPersister anpassen so dass die Turns sofort in den Writer geschrieben werden können 
	
	//APauls
	//Leider nicht mit dem ZipPersister von Basti kompatiebel 
//	public void writeIn(Writer w)
//	{
//		for(UndoableEdit e : edits)
//		{
//			if(e instanceof Turn);
//				
//		}
//	}
	
	
	/**Get all turns for ZipPersister
	 * @author pauls_and
	 * @return All Turns 
	 */
	public List<Turn> getTurns()
	{
		LinkedList<Turn> turns = new LinkedList<Turn>();
		for(UndoableEdit e : edits)
		{
			if (e instanceof Turn)
				turns.add((Turn)e);
		}
		return turns;
	}
	
	//TODO Testing! :D
	/** Stellt alle Turns wieder her
	 * @author pauls_and
	 * @param turns
	 */
	public void restoreTurns(List<Turn> turns)
	{
		for(Turn t : turns)
		{
			addEdit(t);
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
