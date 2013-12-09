package de.bwvaachen.beamoflightgame.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.undo.CannotUndoException;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.LightTileState;
 
public interface ILightController {
	
	public IBeamsOfLightPuzzleBoard getCurrentModel();
	//public int countEmptyFields();
	//public int countLightFields();
	//public int countLightedFields();
	
	// Spiel erzeugen/laden/speichern
	public IBeamsOfLightPuzzleBoard newGame  ( int x , int y ) throws Exception ;	//TODO Bitte Exception spezifizieren
    public void 					saveGame (File f) throws IOException;
	public void 					loadGame (File f) throws FileNotFoundException, IOException, ClassNotFoundException; 
	public void						setBoard ( IBeamsOfLightPuzzleBoard _board ) throws Exception ;
	public IBeamsOfLightPuzzleBoard getBoard ( ) throws Exception ;
	
	// Spielzüge
	public Turn 					doTurn ( int x , int y , LightTileState oldTileState, LightTileState newTileState ) throws Exception ; //TODO Bitte Exception spezifizieren
	public boolean 					isUndoable ();
	public boolean 					isRedoable ();
	public void 			        setUndoMark ();
	public void returnToNextUndoMark () throws CannotUndoException;
	public void returnToStableState () throws CannotUndoException;
	
} // public interface ILightController
