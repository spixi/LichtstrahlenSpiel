package de.bwvaachen.beamoflightgame.logic;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import de.bwvaachen.beamoflightgame.helper.ITileVisitor;
import de.bwvaachen.beamoflightgame.model.AbstractTile;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITileState;

public class MaximumIterationsExceededException extends UnsolvablePuzzleException {
	private static final long serialVersionUID = -5182021097440559937L;

	private static class NullTile extends AbstractTile<ITileState> {
		protected NullTile(IBeamsOfLightPuzzleBoard board) {
			super(board, 0, 0, ITileState.class);
		}

		@Override
		public void accept(ITileVisitor v) {}
		
	}
	
	public MaximumIterationsExceededException(IBeamsOfLightPuzzleBoard b) {
		super(new NullTile(b));
	}
}
