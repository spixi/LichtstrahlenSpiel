package de.bwvaachen.beamoflightgame.controller.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.undo.UndoManager;

import org.easymock.EasyMock;

import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.controller.Turn;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

public class LightController implements ILightController {
	private UndoManager              turnManager;
	private IBeamsOfLightPuzzleBoard puzzleBoard;
	private Turn					 currentTurn;

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
	
	public void saveGame(File f) throws IOException {
//		TODO You can ZipPersister or write your one persister you dont need an extern jar for zip streams  
//		FileOutputStream  fos    = new FileOutputStream(f);
//		LzmaOutputStream  los    = new LzmaOutputStream.Builder(fos).build();
//		BufferedOutputStream bos = new BufferedOutputStream(los);
//		ObjectOutputStream oos   = new ObjectOutputStream(bos);
//		
//		oos.writeObject(puzzleBoard);
//		oos.writeObject(turnManager);
//		oos.flush();
//		oos.close();
	} // public void saveGame(File f)
	
	
	public void loadGame(File f) throws FileNotFoundException, IOException, ClassNotFoundException {
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
	public IBeamsOfLightPuzzleBoard getCurrentModel() 
	{
		return puzzleBoard;
		
	} // public IBeamsOfLightPuzzleBoard getCurrentModel()
	

	@Override
	public IBeamsOfLightPuzzleBoard newGame(int x, int y) throws Exception 
	{
		puzzleBoard = new BeamsOfLightPuzzleBoard();
		return null;
	} // public IBeamsOfLightPuzzleBoard newGame(int x, int y)
	

	@Override
	public Turn doTurn(int x, int y, LightTileState oldTileState, LightTileState newTileState) throws Exception 
	{
		Turn oTurn = new Turn(puzzleBoard, x, y, oldTileState, newTileState);
		turnManager.addEdit(oTurn);
		currentTurn = oTurn;
		
		return oTurn;
	} // public void doTurn(int x, int y, char orientaion, boolean isEnd)
	

	@Override
	public boolean isUndoable() throws Exception {
		return turnManager.canUndo();
	} // public boolean isUndoable() 
	

	@Override
	public boolean isRedoable() throws Exception {
		return turnManager.canRedo();
	} // public boolean isRedoable()
	

	@Override
	public void setUndoMark() throws Exception {
		currentTurn.mark();
	} // public void setUndoMark()
	

	@Override
	public IBeamsOfLightPuzzleBoard returnToNextUndoMark() throws Exception {
		return null;
	} // public IBeamsOfLightPuzzleBoard returnToNextUndoMark()
	

	@Override
	public IBeamsOfLightPuzzleBoard returnToStableState() throws Exception {
		return null;
		
	} // public IBeamsOfLightPuzzleBoard returnToStableStaate() s

	public void undo()
	{
		turnManager.undo();
	}
} // public class LightController
