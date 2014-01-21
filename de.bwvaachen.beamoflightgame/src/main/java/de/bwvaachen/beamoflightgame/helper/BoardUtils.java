package de.bwvaachen.beamoflightgame.helper;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.util.HashMap;

import de.bwvaachen.beamoflightgame.model.IChangeableTile;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.ITileState;

public final class  BoardUtils<T extends ITileState>{
	private static HashMap multitons;
	private Class<T> clazz;
	
	static {
		multitons = new HashMap();
	}
	
	private BoardUtils(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public static <U extends ITileState> BoardUtils<U> getInstance(Class<U> clazz) {
		BoardUtils<U> instance = null;
		instance = (BoardUtils<U>) multitons.get(clazz);
			if(instance == null) {
				synchronized(multitons) {
					instance = (BoardUtils<U>) multitons.get(clazz);
					if(instance == null) {
						instance = new BoardUtils<U>(clazz);
						multitons.put(clazz, new BoardUtils<U>(clazz));
					}
				}
			}
		return instance;
	}
	
	public int fillBoard(ITile<T> start, int numOfFields, TraverseDirection dir, T state) {
		BoardTraverser traverser = start.getTraverser();
		int filledTiles = 0;
		while(--numOfFields >= 0) {
			ITile tile = traverser.get();
			if(tile.isStateAllowed(clazz) && tile.isStateChangeable()) {
				//Change the state
				//Only the first change should be significant, therefore numOfFields==0
				((IChangeableTile) tile).setState(state, numOfFields==0);
				filledTiles++;
			}
			else break;
			//shift the traverser to the next tile
			if(!traverser.shift(dir)) break;
		}
		return filledTiles;
	}

}
