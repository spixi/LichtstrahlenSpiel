package de.bwvaachen.beamoflightgame.logic.solver;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public interface ISolverBuilderContext {
	//Fluent interface pattern
	
	//Example:
	//
	//ISolver mySolver =
	//SolverBuilder.build().with(AwesomeStrategy.class).with(AnotherStrategy.class).forBoard(theBoard);
	
	public ISolverBuilderContext with(Class<? extends IStrategy> s) throws InstantiationException, IllegalAccessException;
	public ISolver forBoard(IBeamsOfLightPuzzleBoard bj);
}
