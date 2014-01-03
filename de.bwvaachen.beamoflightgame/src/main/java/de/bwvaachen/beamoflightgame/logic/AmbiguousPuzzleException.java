package de.bwvaachen.beamoflightgame.logic;

import de.bwvaachen.beamoflightgame.model.ITile;


public class AmbiguousPuzzleException extends PuzzleException {

	public AmbiguousPuzzleException(ITile source) {
		super(source);
	}

	private static final long serialVersionUID = 3581124968777939523L;

}
