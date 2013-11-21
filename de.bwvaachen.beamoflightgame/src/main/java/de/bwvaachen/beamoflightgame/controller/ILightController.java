package de.bwvaachen.beamoflightgame.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
 
public interface ILightController {
	
	public IBeamsOfLightPuzzleBoard getCurrentModel();
	//public int countEmptyFields();
	//public int countLightFields();
	//public int countLightedFields();
	
	// Spiel erzeugen/laden/speichern
	public IBeamsOfLightPuzzleBoard newGame  ( int x , int y ) throws Exception ;	
    public void 					saveGame (File f) throws IOException;
	public void 					loadGame (File f) throws FileNotFoundException, IOException, ClassNotFoundException;
	
	// Spielzüge
	public void 					doTurn ( int x , int y , char orientaion , boolean isEnd ) throws Exception ;
	public boolean 					isUndoable () throws Exception ;
	public boolean 					isRedoable () throws Exception ;
	public void 			        setUndoMark () throws Exception ;
	public IBeamsOfLightPuzzleBoard returnToNextUndoMark () throws Exception ;
	public IBeamsOfLightPuzzleBoard returnToStableStaate () throws Exception ;
	
} // public interface ILightController
