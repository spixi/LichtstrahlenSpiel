package de.bwvaachen.beamoflightgame.helper;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.bwvaachen.beamoflightgame.controller.Turn;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public interface IPersistenceHelper {
	Pair<IBeamsOfLightPuzzleBoard, List<Turn>> load(File path) throws IOException, WrongCodecException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	/**
	 * 
	 * @param path
	 * @param board
	 * @param turns 
	 * @throws IOException
	 */
	void save(File path,IBeamsOfLightPuzzleBoard board, List<Turn> turns, IBeamsOfLightPuzzleBoard solution) throws IOException;
}
