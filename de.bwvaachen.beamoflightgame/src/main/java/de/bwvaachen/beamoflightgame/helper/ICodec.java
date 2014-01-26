package de.bwvaachen.beamoflightgame.helper;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

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
