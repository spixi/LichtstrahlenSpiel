package de.bwvaachen.beamoflightgame.helper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.bwvaachen.beamoflightgame.controller.Turn;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public interface IPersistenceHelper {
	/**
	 * 
	 * @param path
	 * @param board
	 * @param turns 
	 * @throws IOException
	 */
	void save(File path,IBeamsOfLightPuzzleBoard board, List<Turn> turns) throws IOException;
	Pair<IBeamsOfLightPuzzleBoard, List<Turn>> load(File path) throws IOException, WrongCodecException, InstantiationException, IllegalAccessException, ClassNotFoundException;
}
