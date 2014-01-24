package de.bwvaachen.beamoflightgame.ui;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

public class PrototypModelJanko extends BeamsOfLightPuzzleBoard {
	
 //siehe resources/non-free/Beispiel_*.bmp
 //

	public PrototypModelJanko() {
		init(7,7);
	}
	

	@Override
	public void init(int x, int y) {
		super.init(x,y);
		
		putTile(new NumberTile(this,2,0,0));
		putTile(new LightTile(this,1,0));
		putTile(new LightTile(this,2,0));
		putTile(new LightTile(this,3,0));
		putTile(new LightTile(this,4,0));
		putTile(new NumberTile(this,2,5,0));
		putTile(new LightTile(this,6,0));
		putTile(new LightTile(this,0,1));
		putTile(new LightTile(this,1,1));
		putTile(new LightTile(this,2,1));
		putTile(new NumberTile(this,3,3,1));
		putTile(new LightTile(this,4,1));
		putTile(new LightTile(this,5,1));
		putTile(new NumberTile(this,3,6,1));
		putTile(new LightTile(this,0,2));
		putTile(new LightTile(this,1,2));
		putTile(new NumberTile(this,6,2,2));
		putTile(new LightTile(this,3,2));
		putTile(new LightTile(this,4,2));
		putTile(new LightTile(this,5,2));
		putTile(new LightTile(this,6,2));
		putTile(new LightTile(this,0,3));
		putTile(new NumberTile(this,2,1,3));
		putTile(new LightTile(this,2,3));
		putTile(new LightTile(this,3,3));
		putTile(new LightTile(this,4,3));
		putTile(new NumberTile(this,5,5,3));
		putTile(new LightTile(this,6,3));
		putTile(new LightTile(this,0,4));
		putTile(new LightTile(this,1,4));
		putTile(new LightTile(this,2,4));
		putTile(new LightTile(this,3,4));
		putTile(new NumberTile(this,3,4,4));
		putTile(new LightTile(this,5,4));
		putTile(new LightTile(this,6,4));
		putTile(new NumberTile(this,6,0,5));
		putTile(new LightTile(this,1,5));
		putTile(new LightTile(this,2,5));
		putTile(new NumberTile(this,1,3,5));
		putTile(new LightTile(this,4,5));
		putTile(new LightTile(this,5,5));
		putTile(new LightTile(this,6,5));
		putTile(new LightTile(this,0,6));
		putTile(new NumberTile(this,3,1,6));
		putTile(new LightTile(this,2,6));
		putTile(new LightTile(this,3,6));
		putTile(new LightTile(this,4,6));
		putTile(new LightTile(this,5,6));
		putTile(new NumberTile(this,1,6,6));
		
	}

}
