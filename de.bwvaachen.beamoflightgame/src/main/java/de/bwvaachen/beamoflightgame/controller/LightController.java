package de.bwvaachen.beamoflightgame.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.undo.UndoManager;

import lzma.sdk.lzma.Decoder;
import lzma.streams.LzmaInputStream;
import lzma.streams.LzmaOutputStream;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ILightTile;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTileState;

public class LightController implements ILightController {
	private UndoManager              turnManager;
	private IBeamsOfLightPuzzleBoard puzzleBoard;

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
	
	public void save(File f) throws IOException {
		FileOutputStream  fos    = new FileOutputStream(f);
		LzmaOutputStream  los    = new LzmaOutputStream.Builder(fos).build();
		BufferedOutputStream bos = new BufferedOutputStream(los);
		ObjectOutputStream oos   = new ObjectOutputStream(bos);
		
		oos.writeObject(puzzleBoard);
		oos.writeObject(turnManager);
		oos.flush();
		oos.close();
	}
	
	public void load(File f) throws FileNotFoundException, IOException, ClassNotFoundException {
		FileInputStream   fis   = new FileInputStream(f);
		BufferedInputStream bis = new BufferedInputStream(fis);
		LzmaInputStream   lis   = new LzmaInputStream(bis, new Decoder());
		ObjectInputStream ois   = new ObjectInputStream(lis);
		
		puzzleBoard = (IBeamsOfLightPuzzleBoard)ois.readObject();
		turnManager = (UndoManager)ois.readObject();
		
		ois.close();
	}

}
