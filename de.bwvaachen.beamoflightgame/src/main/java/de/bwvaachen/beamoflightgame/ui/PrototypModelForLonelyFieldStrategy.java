package de.bwvaachen.beamoflightgame.ui;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import de.bwvaachen.beamoflightgame.model.IChangeableTile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

public class PrototypModelForLonelyFieldStrategy extends BeamsOfLightPuzzleBoard {
	
	//	 _______________________
	//	|	  	|	  	|	  	|
	//	|  ---	|	_ 	|	  	|
	//	|	| 	|	| 	|	- 	|
	//	|	| 	|	| 	|	| 	|
	//	|___|___|___|___|___|___|
	//  |	  	|	  	|	| 	|
	//	|	  	|	  	|	| 	|
	//	|	 2	|	2	|	|	|
	//	|	 	|	  	|	| 	|
	//	|_______|_______|___|___|
	//	|	| 	|	| 	|	| 	|
	//	|	| 	|	| 	|	| 	|
	//	|	| 	|	| 	|	| 	|
	//	| ----	|	_ 	|	|	|
	//	|_______|_______|___|___|
	//	|	  	|	  	|	  	|
	//	|	  	|	  	|	  	|
	//	|	|---|-------|	5	|
	//	|	  	|	  	|	  	|
	//	|_______|_______|_______|

	public PrototypModelForLonelyFieldStrategy() {
		init(3,4);
	}
	

	@Override
	public void init(int x, int y) {
		super.init(x,y);
		
		IChangeableTile t;
		
		putTile(new LightTile(this,0,0));
		putTile(new LightTile(this,1,0));
		putTile(new LightTile(this,2,0));
		putTile(new NumberTile(this,2,0,1));
		putTile(new NumberTile(this,2,1,1));
		putTile(new LightTile(this,2,1));
		putTile(new LightTile(this,0,2));
		putTile(new LightTile(this,1,2));
		putTile(new LightTile(this,2,2));
		putTile(new LightTile(this,0,3));
		putTile(new LightTile(this,1,3));
		putTile(new NumberTile(this,5,2,3));

		//t = (IChangeableTile)getTileAt(0,0);
		//t.setState(LightTileState.NORTH);
		
//		t = (IChangeableTile)getTileAt(2,1);
//		t.setState(LightTileState.NORTH);
//
//		t = (IChangeableTile)getTileAt(2,2);
//		t.setState(LightTileState.NORTH);
//		
		System.out.println(this);
		
	}

}
