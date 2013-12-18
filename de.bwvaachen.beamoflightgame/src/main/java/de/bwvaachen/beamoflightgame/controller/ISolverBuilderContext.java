package de.bwvaachen.beamoflightgame.controller;

import de.bwvaachen.beamoflightgame.logic.ISolver;
import de.bwvaachen.beamoflightgame.logic.IStrategy;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public interface ISolverBuilderContext {
	public ISolverBuilderContext and(Class<? extends IStrategy> s) throws InstantiationException, IllegalAccessException;
	public ISolver forBoard(IBeamsOfLightPuzzleBoard b);
}
