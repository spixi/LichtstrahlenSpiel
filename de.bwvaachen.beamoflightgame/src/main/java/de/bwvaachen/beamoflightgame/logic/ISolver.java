package de.bwvaachen.beamoflightgame.logic;

public interface ISolver {
	
	//The level of a puzzle is determined by the formula
	//log(sum(complexity)/count(turns))
	public double getLevel();


	void solve() throws PuzzleException;

}
