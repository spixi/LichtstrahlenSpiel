package de.bwvaachen.beamoflightgame.helper;

import java.io.File;
import java.io.IOException;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public interface IPersistenceHelper {
	void save(File path,IBeamsOfLightPuzzleBoard board, LinkedList<>) throws IOException;
	void load(File path);
}
