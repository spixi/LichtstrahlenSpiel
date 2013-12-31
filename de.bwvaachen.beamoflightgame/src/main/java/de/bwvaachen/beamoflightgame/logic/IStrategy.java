package de.bwvaachen.beamoflightgame.logic;

import de.bwvaachen.beamoflightgame.model.ITile;

public interface IStrategy {
	//The complexity states how difficult a strategy is.
	//Strategies with complexities of 1 - 9 are considered to be trivial
	//Strategies with complexities of 10 - 99 are more challenging
	//Strategies with complexities of 100 - 999 are hard
	public double getComplexity();
	public void init(ITile t);
	public boolean isAppliableForTile(ITile t);
	
	public boolean tryToSolve() throws PuzzleException;
}
