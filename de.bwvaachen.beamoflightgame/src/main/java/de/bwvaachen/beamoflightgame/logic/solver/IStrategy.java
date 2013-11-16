package de.bwvaachen.beamoflightgame.logic.solver;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public interface IStrategy {
	public boolean solve(IBeamsOfLightPuzzleBoard b);
	
	//The complexity states how difficult a strategy is.
	//Strategies with complexities of 1 - 9 are considered to be trivial
	//Strategies with complexities of 10 - 99 are more challenging
	//Strategies with complexities of 100 - 999 are hard
	public int getComplexity();
}
