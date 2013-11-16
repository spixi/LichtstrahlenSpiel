package de.bwvaachen.beamoflightgame.logic.solver;

public interface ISolverBuilderContext {
	public ISolverBuilderContext with(Class<? extends IStrategy> s) throws InstantiationException, IllegalAccessException;
	public ISolver get();
}
