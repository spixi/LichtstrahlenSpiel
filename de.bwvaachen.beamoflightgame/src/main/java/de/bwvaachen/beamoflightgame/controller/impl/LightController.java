package de.bwvaachen.beamoflightgame.controller.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.undo.CannotUndoException;

import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.controller.Turn;
import de.bwvaachen.beamoflightgame.controller.TurnUndoManager;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

public class LightController implements ILightController {
	private TurnUndoManager          turnManager;
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
		
		//generate the file
		//flag if g ame, e mpty or d raft
		char flag = ' ';
		int hight,width;
		
		if(this.getUndoManager().canRedo()) flag = 'g'; //if there are Turns to undo - its a game!

		//TODO check if it is a draft or an empty game!
		
		hight = puzzleBoard.getHeight();
		width = puzzleBoard.getWidth();
		
		
		
	} // public void saveGame(File f)
	
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
	
	/**
	 * 
	 * @param s 
	 * @return
	 * @author Andi
	 */
	private ITile getTileRepresentation(String s)
	{
		String[] ar = s.split("");
		return null;
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
} // public class LightController
