package de.bwvaachen.beamoflightgame.controller.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.undo.UndoManager;

import org.easymock.EasyMock;

import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public class LightController implements ILightController {
	private UndoManager              turnManager;
	private IBeamsOfLightPuzzleBoard puzzleBoard;

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
	public IBeamsOfLightPuzzleBoard getCurrentModel() {
		
		return EasyMock . createMock ( IBeamsOfLightPuzzleBoard.class ) ;
		
	} // public IBeamsOfLightPuzzleBoard getCurrentModel()
	

	@Override
	public IBeamsOfLightPuzzleBoard newGame(int x, int y) throws Exception {
		return null;
	} // public IBeamsOfLightPuzzleBoard newGame(int x, int y)
	

	@Override
	public void doTurn(int x, int y, char orientaion, boolean isEnd)
			throws Exception {
		
	} // public void doTurn(int x, int y, char orientaion, boolean isEnd)
	

	@Override
	public boolean isUndoable() throws Exception {
		return false;
	} // public boolean isUndoable() 
	

	@Override
	public boolean isRedoable() throws Exception {
		return false;
	} // public boolean isRedoable()
	

	@Override
	public void setUndoMark() throws Exception {
		
	} // public void setUndoMark()
	

	@Override
	public IBeamsOfLightPuzzleBoard returnToNextUndoMark() throws Exception {
		return null;
	} // public IBeamsOfLightPuzzleBoard returnToNextUndoMark()
	

	@Override
	public IBeamsOfLightPuzzleBoard returnToStableStaate() throws Exception {
		return null;
		
	} // public IBeamsOfLightPuzzleBoard returnToStableStaate() s

} // public class LightController
