package de.bwvaachen.beamoflightgame.logic;

import de.bwvaachen.beamoflightgame.model.ITile;

public abstract class PuzzleException extends Exception {
	private static final long serialVersionUID = 3848346149119700376L;
	private ITile<?> source;
	
	protected PuzzleException(ITile source) {
		this.source = source;
	}
	
	public ITile<?> getSource() {
		return source;
	}

}
