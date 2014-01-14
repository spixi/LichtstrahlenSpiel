package de.bwvaachen.beamoflightgame.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.undo.CannotUndoException;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.LightTileState;
 
public interface ILightController {
    public IBeamsOfLightPuzzleBoard getCurrentModel();
	public void loadGame (File f) throws FileNotFoundException, IOException;
	public void saveGame(File f) throws IOException;
	
	// Spiel erzeugen/laden/speichern
	public void newGame  ( int x , int y ) throws Exception ;	//TODO Bitte Exception spezifizieren
	
	TurnUndoManager getUndoManager();
	void setBoard(IBeamsOfLightPuzzleBoard _board) throws Exception;
	
	public void solve();
	public boolean GameIsCorrect () ;
	
} // public interface ILightController
