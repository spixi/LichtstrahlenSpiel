package de.bwvaachen.beamoflightgame.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
 
public interface ILightController {
	public IBeamsOfLightPuzzleBoard getCurrentModel();
	public int countEmptyFields();
	public int countLightFields();
	public int countLightedFields();
	
    public void save(File f) throws IOException;
	public void load(File f) throws FileNotFoundException, IOException, ClassNotFoundException;
}
