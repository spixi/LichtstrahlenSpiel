package de.bwvaachen.beamoflightgame.model;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

public class NumberTileState implements ITileState {
	private final int number;
	
	public NumberTileState(final int num) {
		this.number = num;
	}
	public boolean equals(ITileState tileState)
	{
		try{
			NumberTileState nts = (NumberTileState) tileState;
			return nts.getNumber() == this.getNumber();
		}
		catch (Exception e){
			return false;
		}
	}
	public int getNumber() {
		return number;
	}
	@Override
	public String toString() {
		return ((Integer) number).toString();
	}
}
