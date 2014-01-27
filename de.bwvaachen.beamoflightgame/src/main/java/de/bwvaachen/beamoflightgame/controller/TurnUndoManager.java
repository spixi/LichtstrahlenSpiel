package de.bwvaachen.beamoflightgame.controller;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

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

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;
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
		
		boolean undoGeschehen = false ;
		
		// Den Turn holen, der von einem Undo betroffen wäre.
		Turn currentTurn = (Turn) editToBeUndone() ;
				
		// Merken des Boards, da man später noch die Turn Nummer ändern muss.
		IBeamsOfLightPuzzleBoard board = currentTurn . getBoard() ;
		
		
		// Die Nummer des momentan angezeigten Turns holen
		int boardTurnNumber = currentTurn . getBoard() . getCurrentTurnNumber() ;
			
		// Solange undo() machen, wie es noch Züge mit der gleichen Turn-Nummer gibt.
		while ( (currentTurn != null ) && ( currentTurn . getTurnNumber() == boardTurnNumber ) ) {
			
			super.undo() ;
			currentTurn = (Turn)  editToBeUndone() ;
			undoGeschehen = true ;
		}
		
		// Im Board setzen, an welchem Zug man sich momentan befindet
		if ( undoGeschehen )
			board . setCurrentTurnNumber(boardTurnNumber-1) ;
		
		notifyChangeListeners();
		
	}
	
	/*
	 * @author Georg Braun
	 */
	@Override
	public void redo() throws CannotRedoException {
		
		boolean redoGeschehen = false ;
		
		// Den nächsten Redone-Eintrag holen
		Turn nextRedoneTurn = (Turn) editToBeRedone() ;
		
		// Die Nummer des zu wiederherstellenden Turns
		int turnNumberToBeRedone = nextRedoneTurn . getBoard() . getCurrentTurnNumber() + 1 ;
		
		// Durch die Liste der Edits laufen und entsprechend redo(s) aufrufen. Bemerkung: Es geht bestimmt auch eleganter.
		int anzahlRedo = 0 ;
		for(UndoableEdit e : edits)
		{
			if (e instanceof Turn) {
								
				Turn currentTurn = (Turn) e ;
				if ( currentTurn . getTurnNumber() == turnNumberToBeRedone ) {
					super.redo() ;
					redoGeschehen = true ;
				} // if ( currentTurn . getTurnNumber() == turnNumberToBeRedone ) 
			} // if (e instanceof Turn) 
			
		} // for(UndoableEdit e : edits)

		// Die Nummer des Turns im Board anpassen
		if ( redoGeschehen ) 
			nextRedoneTurn . getBoard() . setCurrentTurnNumber ( turnNumberToBeRedone ) ;
		
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
	
	
	/*
	 * @author Georg Braun
	 * Diese Methode löscht alle Turns, die eine höhere Turn Nummer als die übergebene haben.
	 */
	public void deleteTurns ( int newLastTurn ) {
		
		for (UndoableEdit e : edits)
		{
			if (e instanceof Turn) {			
				
				Turn currentTurn = (Turn) e ;
				
				if ( currentTurn . getTurnNumber() > newLastTurn )
					currentTurn . die() ;
				
			} // if (e instanceof Turn)
		} // for (UndoableEdit e : edits)
		
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
	
	/*
	 * @author Georg Braun
	 * Diese Methode gibt ein paar Informationen über die Turns aus.
	 */
	public void getTurnsInfo()
	{
		
		System . out . println ( "--- Turns Info Anfang ---" ) ;
		LinkedList<Turn> turns = new LinkedList<Turn>();
		for(UndoableEdit e : edits)
		{
			if (e instanceof Turn) {
				turns.add((Turn)e);
				
				Turn currentTurn = (Turn) e ;
				
				String activeTurn = "" ;
				if ( currentTurn . getBoard() . getCurrentTurnNumber() == currentTurn . getTurnNumber() )
					activeTurn = "-> " ;
				
				System . out . println ( activeTurn + "TurnNumber: "  + currentTurn . getTurnNumber() + "(" + currentTurn . getX() + "/" + currentTurn . getY() + ") " +  "\t Marker: " + Boolean.toString(currentTurn . hasFlag(FLAG_MARKER)) + "\t Error: " + Boolean.toString(currentTurn . hasFlag(FLAG_ERROR)) +  "\t Signifikant: " + Boolean.toString(currentTurn . isSignificant()) ) ;
			}
				
		}
		System . out . println ( "--- Turns Info Ende ---" ) ;
	
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
