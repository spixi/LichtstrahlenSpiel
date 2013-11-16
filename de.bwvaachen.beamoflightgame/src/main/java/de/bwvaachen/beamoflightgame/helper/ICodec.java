package de.bwvaachen.beamoflightgame.helper;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import de.bwvaachen.beamoflightgame.controller.Turn;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public interface ICodec {
	void boardToOutputstream(OutputStream output, IBeamsOfLightPuzzleBoard board);
	void turnsToOutputstream(OutputStream output, List<Turn> turns) ;
	
	IBeamsOfLightPuzzleBoard boardFromInputstream(InputStream input) throws WrongCodecException;
	List<Turn> turnsFromInputstream(InputStream input, IBeamsOfLightPuzzleBoard board) throws WrongCodecException;

}
