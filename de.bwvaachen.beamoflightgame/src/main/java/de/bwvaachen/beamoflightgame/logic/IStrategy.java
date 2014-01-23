package de.bwvaachen.beamoflightgame.logic;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.util.Collection;

import de.bwvaachen.beamoflightgame.logic.solver.AbstractSolver;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.ITileState;

public interface IStrategy<T extends ITileState> {
	//The complexity states how difficult a strategy is.
	//Strategies with complexities of 1 - 9 are considered to be trivial
	//Strategies with complexities of 10 - 99 are more challenging
	//Strategies with complexities of 100 - 999 are hard
	public double getComplexity();
	public void init(ITile<T> t);
	public boolean isAppliableForTile(ITile<?> t);
	public boolean hasHooks();
	public Collection<? extends AbstractSolver.Hook> getHooks();
	
	public boolean tryToSolve() throws UnsolvablePuzzleException, AmbiguousPuzzleException;
}
