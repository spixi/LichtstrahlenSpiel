package de.bwvaachen.beamoflightgame.helper;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import de.bwvaachen.beamoflightgame.controller.Turn;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public interface ICodec {
	IBeamsOfLightPuzzleBoard boardFromInputstream(InputStream input) throws WrongCodecException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void boardToOutputstream(OutputStream output, IBeamsOfLightPuzzleBoard board);
	
	List<Turn> turnsFromInputstream(InputStream input, IBeamsOfLightPuzzleBoard board) throws WrongCodecException;
	void turnsToOutputstream(OutputStream output, List<Turn> turns) ;

}
