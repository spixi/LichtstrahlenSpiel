package de.bwvaachen.beamoflightgame.model;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import de.bwvaachen.beamoflightgame.helper.ITileVisitor;

public class LightTile extends AbstractTile<LightTileState> implements IChangeableTile<LightTileState>  {
	public LightTile(IBeamsOfLightPuzzleBoard board, int x, int y) {
		this(board, x, y, null);
	}
	
	public LightTile(IBeamsOfLightPuzzleBoard board, int x, int y, LightTileState state) {
		super(board, x, y, LightTileState.class);
		setState(state, true);
	}

	@Override
	public void accept(ITileVisitor v) {
		v.visitLightTile(this);
	}

	@Override
	public void setState(LightTileState state, boolean significant) {
		if(state == null) state = LightTileState.EMPTY;
		setTileState(state,significant);	
	}
	
	
}
