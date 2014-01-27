package de.bwvaachen.beamoflightgame.controller.impl;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.controller.SolverBuilder;
import de.bwvaachen.beamoflightgame.controller.Turn;
import de.bwvaachen.beamoflightgame.controller.TurnUndoManager;
import de.bwvaachen.beamoflightgame.helper.BoardChangeListener;
import de.bwvaachen.beamoflightgame.helper.Pair;
import de.bwvaachen.beamoflightgame.helper.SimpleASCIICodec;
import de.bwvaachen.beamoflightgame.helper.WrongCodecException;
import de.bwvaachen.beamoflightgame.helper.ZipPersister;
import de.bwvaachen.beamoflightgame.logic.ISolver;
import de.bwvaachen.beamoflightgame.logic.strategies.IntersectionStrategy;
import de.bwvaachen.beamoflightgame.logic.strategies.LonelyFieldStrategy;
import de.bwvaachen.beamoflightgame.logic.strategies.TryAndErrorStrategy;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.ui.PrototypModelForIntersectionStrategy;
import de.bwvaachen.beamoflightgame.ui.PrototypModelForLonelyFieldStrategy;

public class LightController implements ILightController {
	private TurnUndoManager          turnManager;
	private IBeamsOfLightPuzzleBoard puzzleBoard;
	private IBeamsOfLightPuzzleBoard solutionBoard;
	private Set<BoardChangeListener> boardChangeListeners;
	
	public LightController() {
		boardChangeListeners = new HashSet<BoardChangeListener>();
	}
	
	
	@Override
	public IBeamsOfLightPuzzleBoard getCurrentModel() 
	{
		return puzzleBoard;
		
	} // public IBeamsOfLightPuzzleBoard getCurrentModel()

	@Override
	public void loadGame(File f) throws FileNotFoundException, IOException {
		ZipPersister persister = new ZipPersister(new SimpleASCIICodec());
		try {
			
			Pair<IBeamsOfLightPuzzleBoard[],List<Turn>> pair = persister.load(f);
			
			this.setBoard(pair.left[0]);
			this.solutionBoard = pair.left[1];
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	} // public void loadGame(File f)
	

	@Override
	public void newGame(int width, int height) throws Exception 
	{
		solutionBoard = null;
		setBoard(new BeamsOfLightPuzzleBoard());
		puzzleBoard.init(width, height);
	} // public IBeamsOfLightPuzzleBoard newGame(int x, int y)
	

	@Override
	public void saveGame(File f) throws IOException {
				
		ZipPersister persister = new ZipPersister(new SimpleASCIICodec());
		persister.save(f, puzzleBoard, turnManager.getTurns(),solutionBoard); 
				
	} // public void saveGame(File f)
	
	//Nur fuer Debugging !
	/**
	 * @author Andi
	 * @param args
	 */
//	public static void main(String[] args)
//	{
//		
//		ILightController c = new LightController();
//		IBeamsOfLightPuzzleBoard m = new PrototypModelForIntersectionStrategy();
//		try {
//			c.setBoard(m);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			c.saveGame(new File("test.bol"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	
	/**
	 * Turn a tile into its String representation
	 * @param t is the ITile that need to be transformed
	 * @return is the String that represents the Tile
	 * @author Andi 
	 */
//	@Deprecated
//	private String getStringRepresentation(ITile t)
//	{
//		try{
//			NumberTile ti = (NumberTile) t;
//			return String.format("{%s|%d|%d|%d}","N",ti.getNumber(),ti.getX(),ti.getY());
//		}
//		catch (Exception e)
//		{
//			LightTile ti = (LightTile) t;
//			return String.format("{%s|%d|%d|%d}","L",ti.getTileState().getSign(),ti.getX(),ti.getY());		
//		}
//	}
	
	

	@Override
	public void setBoard(IBeamsOfLightPuzzleBoard _board) throws Exception {
		if(_board == puzzleBoard) {
			//no change has happened!
			return;
		}
		
		puzzleBoard = _board ;
		if(solutionBoard == null)
		{	
			solve();
		}
		turnManager = new TurnUndoManager();
		
		puzzleBoard.addUndoableEditListener(turnManager);

		notifyBoardChangeListeners();
	}

	@Override
	public TurnUndoManager getUndoManager() {
		return turnManager;
	}
	
	/*
	 * @author Georg Braun
	 * Die Lösung erzeugen
	 */
	@Override 
	public void solve () {
		
		try {
			// Das "Spielboard" kopieren
			solutionBoard = puzzleBoard . clone () ;			
		
			// Den Solver für die Musterlösung erzeugen
			ISolver s =
					SolverBuilder.buildWith(LonelyFieldStrategy.class).
					and(IntersectionStrategy.class).
					//and(TryAndErrorStrategy.class).
					forBoard(solutionBoard);
			if(System.getProperty("os.name", "generic").toLowerCase().startsWith("win")) {
				System.setErr(new PrintStream("nul"));
			}
			else {
				System.setErr(new PrintStream("/dev/null"));
			}
			s.solve();
			s.getLevel();
			
		}
		//Was soll das hier? Es kam eine MaximumIterationsExceededException
		//Niemals einfach nur Exception oder Throwable abfangen,
		//sonst weiß nachher niemand mehr, welche Exception überhaupt aufgetreten ist!!!
		catch ( Exception e ) {
			System . out . println ( e.getMessage() ) ; //ebenso schwachsinnig, da nicht jede Exception eine Message hat
			                                            //Dafür hat die Exception doch ihren Klassennamen, damit man sie unterscheiden kann!
			//solutionBoard = null ;                    //Erstmal auskommentiert!
		} // try .. catch
		
	} // public void solve () 

	/*
	 * @author Georg Braun
	 * Prüfen ob das Spielfeld mit der Lösung übereinstimmt
	 */
	public boolean GameIsCorrect () {
				
		int puzzleBoardWidth    = puzzleBoard . getWidth() ;
		int puzzleBoardHeight   = puzzleBoard . getHeight() ;
		boolean gameIsCorrect = true ;
		
		for ( int currentRow = 0 ; currentRow < puzzleBoardHeight ; currentRow++ ) {
			for ( int currentCol = 0 ; currentCol < puzzleBoardWidth ; currentCol++ ) {
				ITile currentBoardTile = puzzleBoard . getTileAt( currentCol , currentRow) ;
				
				if ( currentBoardTile instanceof LightTile ) {
					LightTile currentBoardLightTile = (LightTile) currentBoardTile ;
					LightTileState currentBoardLightTileState = currentBoardLightTile . getTileState() ;
					LightTile currentSolutionLightTile = (LightTile) solutionBoard . getTileAt( currentCol , currentRow ) ;					
										
					if ( currentBoardLightTileState != LightTileState . EMPTY ) {
						if ( currentBoardLightTile . getTileState() . equals( currentSolutionLightTile . getTileState() ) ) {
							// Korrekt
						}
						else
						{
							// Fehler 
							gameIsCorrect = false ;
						}
					} // if ( currentBoardLightTileState != LightTileState . EMPTY ) 					
	
						
				} // if ( currentBoardTile instanceof LightTile )
				
			} // for ( int currentCol = 0 ; currentCol < puzzleBoardWidth ; currentCol++ )			
		} // for ( int currentRow = 0 ; currentRow < puzzleBoardHeight ; currentRow++ )
		
		
		return gameIsCorrect ;
	} // public boolean GameIsCorrect () 
	
	
	/*
	 * @author Georg Braun
	 * @Prüfen ob das übergeben Feld richtig ist (Vergleich mit der Musterlösung)
	 */
	public boolean IsTileCorrect ( int _x , int _y ) {
		
		if ( ( puzzleBoard != null ) && ( solutionBoard != null ) ) {
			ITile puzzleTile   = puzzleBoard . getTileAt( _x , _y) ;
			ITile solutionTile = solutionBoard . getTileAt( _x , _y) ;
			
			// Prüfung ob es LightTiles sind
			if ( ( puzzleTile instanceof LightTile ) && ( solutionTile instanceof LightTile ) ) {
				
				LightTile puzzleLightTile = (LightTile) puzzleTile ;
				LightTile solutionLightTile = (LightTile) solutionTile ;
				
				if (  puzzleLightTile . getTileState() .equals( solutionLightTile . getTileState() )  ) {
					return true ;
				}
				
			}
		}
		return false ;
		
	} // public boolean IsTileCorrect ( int _x , int _y ) 

	@Override
	public void swapModelWithSolution() {
		IBeamsOfLightPuzzleBoard tmp;
		tmp = puzzleBoard;
		puzzleBoard = solutionBoard;
		solutionBoard = tmp;
		notifyBoardChangeListeners();
	}
	
	private void notifyBoardChangeListeners() {
		for(BoardChangeListener bcl : boardChangeListeners)
			bcl.boardHasChanged();
	}


	@Override
	public void addBoardChangeListener(BoardChangeListener bcl) {
		boardChangeListeners.add(bcl);
	}


	@Override
	public void removeBoardChangeListener(BoardChangeListener bcl) {
		boardChangeListeners.remove(bcl);
	}
	
} // public class LightController
