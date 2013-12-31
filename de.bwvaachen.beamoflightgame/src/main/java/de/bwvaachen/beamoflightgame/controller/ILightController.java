package de.bwvaachen.beamoflightgame.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.undo.CannotUndoException;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.LightTileState;
 
public interface ILightController {
	
	// Spielzï¿½ge
	public Turn 					doTurn ( int x , int y , LightTileState oldTileState, LightTileState newTileState ) throws Exception ; //TODO Bitte Exception spezifizieren
	
	public IBeamsOfLightPuzzleBoard getBoard ( ) throws Exception ;
    public IBeamsOfLightPuzzleBoard getCurrentModel();
	//public int countEmptyFields();
	//public int countLightFields();
	//public int countLightedFields();
	public boolean 					isRedoable (); 
	public boolean 					isUndoable ();
	public void 					loadGame (File f) throws FileNotFoundException, IOException, ClassNotFoundException;
	
	// Spiel erzeugen/laden/speichern
	public IBeamsOfLightPuzzleBoard newGame  ( int x , int y ) throws Exception ;	//TODO Bitte Exception spezifizieren
	public void returnToNextUndoMark () throws CannotUndoException;
	public void returnToStableState () throws CannotUndoException;
	public void 					saveGame (File f) throws IOException;
	public void						setBoard ( IBeamsOfLightPuzzleBoard _board ) throws Exception ;
	public void 			        setUndoMark ();
	
} // public interface ILightController
