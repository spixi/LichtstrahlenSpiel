package de.bwvaachen.beamoflightgame.logic;

import de.bwvaachen.beamoflightgame.model.ITile;


public class UnsolvablePuzzleException extends PuzzleException {

	public UnsolvablePuzzleException(ITile source) {
		super(source);
	}

	private static final long serialVersionUID = 2296201870548629536L;

}
