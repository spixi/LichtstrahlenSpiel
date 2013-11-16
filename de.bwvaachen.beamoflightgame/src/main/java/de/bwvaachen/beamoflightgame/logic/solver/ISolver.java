package de.bwvaachen.beamoflightgame.logic.solver;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public interface ISolver {
	
	public void solve(IBeamsOfLightPuzzleBoard board) throws PuzzleException;
	
	
	//The level of a puzzle is determined by the formula
	//log(sum(complexity)/count(turns))
	public int getLevel();

}
