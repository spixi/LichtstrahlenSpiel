package de.bwvaachen.beamoflightgame.model;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.ITileVisitor;

public class NumberTile extends AbstractTile<NumberTileState> implements IChangeableTile<NumberTileState> {
	public NumberTile(final IBeamsOfLightPuzzleBoard board, final int number, int x, int y) {
		super(board, x, y, NumberTileState.class);
		setTileState(new NumberTileState(number), true);
	}

	@Override
	public void accept(ITileVisitor v) {
		v.visitNumberTile(this);
	}

	public int getNumber() {
		// TODO Auto-generated method stub
		return getTileState().getNumber();
	}

	public int getRemainingLightRange() {
		int counter = 0;
		BoardTraverser traverser = new BoardTraverser(this.board, this.getX(), this.getY());
		for(LightTileState lts : LightTileState.allDirections()) {
			traverser.reset();
			while(traverser.shift(lts.getTraverseDirection())){
				if(traverser.get().getTileState().equals(lts)) {
					counter++;
				}
				else break;
			}
		}
		return getNumber() - counter;
	}
	
	@Override
	public void setState(NumberTileState state, boolean significant) {
		setTileState(state,significant);	
	}
	

}
