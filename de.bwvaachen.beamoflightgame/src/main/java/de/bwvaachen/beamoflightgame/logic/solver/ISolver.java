package de.bwvaachen.beamoflightgame.logic.solver;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public interface ISolver {
	public boolean solve(IBeamsOfLightPuzzleBoard b);
	public ISolver setSuccessor();
}
