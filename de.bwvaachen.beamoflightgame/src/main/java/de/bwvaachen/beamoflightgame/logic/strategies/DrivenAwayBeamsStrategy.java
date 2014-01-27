package de.bwvaachen.beamoflightgame.logic.strategies;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.NumberTileState;

public class DrivenAwayBeamsStrategy extends AbstractStrategy<NumberTileState> {

	@Override
	public double getComplexity() {
		return 100;
	}

	@Override
	public boolean isApplicableForTile(ITile<?> t) {
		return (t instanceof NumberTile) && ((NumberTile) t).getRemainingLightRange() > 0;
	}
	
	@Override
	public boolean tryToSolve() {
		
		//Wird aus Zeitmangel nicht implementiert
		return false;
		// TODO Auto-generated method stub
	}

}
