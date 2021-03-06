package de.bwvaachen.beamoflightgame.controller;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.bwvaachen.beamoflightgame.helper.BoardChangeListener;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
 
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
	public boolean gameIsFinished () ;
	public boolean IsTileCorrect ( int _x , int _y ) ;
	public void swapModelWithSolution();
	
	public void addBoardChangeListener(BoardChangeListener bcl);
	public void removeBoardChangeListener(BoardChangeListener bcl);
	
} // public interface ILightController
