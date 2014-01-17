package de.bwvaachen.beamoflightgame.controller.impl;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.controller.SolverBuilder;
import de.bwvaachen.beamoflightgame.controller.TurnUndoManager;
import de.bwvaachen.beamoflightgame.helper.SimpleASCIICodec;
import de.bwvaachen.beamoflightgame.helper.ZipPersister;
import de.bwvaachen.beamoflightgame.logic.ISolver;
import de.bwvaachen.beamoflightgame.logic.strategies.IntersectionStrategy;
import de.bwvaachen.beamoflightgame.logic.strategies.LonelyFieldStrategy;
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
	public IBeamsOfLightPuzzleBoard solutionBoard ;
	

	

	/*
	@Override
	public IBeamsOfLightPuzzleBoard getCurrentModel() {
		return puzzleBoard;
	}

	@Override
	public int countEmptyFields() {
		int count=0;
		IBeamsOfLightPuzzleBoard currentModel = getCurrentModel();
		for(ITile field:currentModel){
			if(field instanceof ILightTile 
					&& ((ILightTile)field).getState()==LightTileState.EMPTY){
				count++;
			}
		}
		return count;
	}

	@Override
	public int countLightFields() {
		int count=0;
		IBeamsOfLightPuzzleBoard currentModel = getCurrentModel();
		for(ITile field:currentModel){
			if(field instanceof ILightTile){
				count++;
			}
		}
		return count;
	}

	@Override
	public int countLightedFields() {
		int count=0;
		IBeamsOfLightPuzzleBoard currentModel = getCurrentModel();
		for(ITile field:currentModel){
			if(field instanceof ILightTile 
					&& ((ILightTile)field).getState()!=LightTileState.EMPTY){
				count++;
			}
		}
		return count;
	}
	*/
	
	@Override
	public IBeamsOfLightPuzzleBoard getCurrentModel() 
	{
		return puzzleBoard;
		
	} // public IBeamsOfLightPuzzleBoard getCurrentModel()

	@Override
	public void loadGame(File f) throws FileNotFoundException, IOException {
//		FileInputStream   fis   = new FileInputStream(f);
//		BufferedInputStream bis = new BufferedInputStream(fis);
//		LzmaInputStream   lis   = new LzmaInputStream(bis, new Decoder());
//		ObjectInputStream ois   = new ObjectInputStream(lis);
//		
//		puzzleBoard = (IBeamsOfLightPuzzleBoard)ois.readObject();
//		turnManager = (UndoManager)ois.readObject();
//		
//		ois.close();
	} // public void loadGame(File f)
	

	@Override
	public void newGame(int width, int height) throws Exception 
	{
		setBoard(new BeamsOfLightPuzzleBoard());
		puzzleBoard.init(width, height);
	} // public IBeamsOfLightPuzzleBoard newGame(int x, int y)
	

	@Override
	public void saveGame(File f) throws IOException {
//		TODO You can ZipPersister or write your one persister you dont need an extern jar for zip streams  
//		FileOutputStream  fos    = new FileOutputStream(f);
//		LzmaOutputStream  los    = new LzmaOutputStream.Builder(fos).build();
//		BufferedOutputStream bos = new BufferedOutputStream(los);
//		ObjectOutputStream oos   = new ObjectOutputStream(bos);
				
		ZipPersister persister = new ZipPersister(new SimpleASCIICodec());
		persister.save(f, puzzleBoard, turnManager.getTurns()); 
		
		
		
	} // public void saveGame(File f)
	
	//Nur fuer Debugging !
	public static void main(String[] args)
	{
		
		ILightController c = new LightController();
		IBeamsOfLightPuzzleBoard m = new PrototypModelForIntersectionStrategy();
		try {
			c.setBoard(m);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			c.saveGame(new File("test.bol"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Turn a tile into its String representation
	 * @param t is the ITile that need to be transformed
	 * @return is the String that represents the Tile
	 * @author Andi 
	 */
	private String getStringRepresentation(ITile t)
	{
		try{
			NumberTile ti = (NumberTile) t;
			return String.format("{%s|%d|%d|%d}","N",ti.getNumber(),ti.getX(),ti.getY());
		}
		catch (Exception e)
		{
			LightTile ti = (LightTile) t;
			return String.format("{%s|%d|%d|%d}","L",ti.getTileState().getSign(),ti.getX(),ti.getY());		
		}
	}
	
	

	@Override
	public void setBoard(IBeamsOfLightPuzzleBoard _board) throws Exception {
		puzzleBoard = _board ;
		turnManager = new TurnUndoManager();
		puzzleBoard.addUndoableEditListener(turnManager);
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
					/*and(TryAndErrorStrategy.class).*/
					forBoard(solutionBoard);
			s.solve();
			
		}
		catch ( Exception e ) {
			System . out . println ( e.getMessage() ) ;
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
	}
	
} // public class LightController
