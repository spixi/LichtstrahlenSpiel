package de.bwvaachen.beamoflightgame.model.impl;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import de.bwvaachen.beamoflightgame.helper.AbstractTileVisitor;
import de.bwvaachen.beamoflightgame.helper.ITileVisitor;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.ITileState;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.NumberTile;

public class BeamsOfLightPuzzleBoard implements IBeamsOfLightPuzzleBoard, ChangeListener, UndoableEditListener {

	private int width, height;
	private ITile[][] tiles;
	private LinkedList<NumberTile> numberTiles;
	private ConcurrentLinkedQueue<ITile> tileQueue = new ConcurrentLinkedQueue<ITile>();
	private HashSet<ChangeListener> changeListeners = new HashSet<ChangeListener>();
	private HashSet<UndoableEditListener> undoableEditListeners = new HashSet<UndoableEditListener>();

	@Override
	public void addChangeListener(ChangeListener cl) {
		for(ITile t: this) {
			t.addChangeListener(cl);
		}
		changeListeners.add(cl);
	}

	@Override
	public void addUndoableEditListener(UndoableEditListener ul) {
		for(ITile t: this) {
			t.addUndoableEditListener(ul);
		}
		undoableEditListeners.add(ul);		
	}

	/**
	 * @author Marius
	 * @param tile The tile to enqueue
	 * 
	 * A thread-safe method to add tiles, also if the board has not been initialized yet.
	 * The tiles are stored in an internal list and committed when the flush() method is called.
	 */
	@Override
	public void enqueueTile(ITile tile) {
		tileQueue.add(tile);
	}

	/**
	 * @author Marius
	 * 
	 * @see enqueueTile(ITile tile)
	 */
	@Override
	public void flush() {
		for(ITile t: tileQueue) {
			putTile(t);
		}
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getNumOfNumberTiles() {
		//return numberTiles.values().size();
		return numberTiles.size();
	}
	
	@Override
	public ITile getTileAt(int x, int y) throws IndexOutOfBoundsException {
		return tiles[x][y];
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public boolean hasField(int x, int y) {
		return ( (x>(-1)) && (x<getWidth()) && (y>(-1)) && (y<getHeight()) );
	}

	@Override
	public void init(int x, int y) {
		width  = x;
		height = y;
		
		tiles       = new ITile[width][height];
		numberTiles = new LinkedList<NumberTile>();
	}
	
	@Override
	public boolean isPlacementOfTileStatePossible(ITileState state,
			int x, int y) {
		//TODO implementation
		return true;
	}

	@Override
	public Iterator<ITile> iterator() {
		return new Iterator<ITile>() {
			int x = 0, y = 0;

			@Override
			public boolean hasNext() {
				return (x < getWidth());
			}

			@Override
			public ITile next() {
				ITile next = getTileAt(x,y);
				if (++y==getHeight()) {
					y=0;
					x++;
				};
				return next;
			}

			@Override
			public void remove() throws UnsupportedOperationException {
				throw new UnsupportedOperationException();
			}

		};
	}

	@Override
	public void putTile(ITile t) {
		t.accept(new ITileVisitor() {
			@Override
			public void visitLightTile(LightTile t) {
				BeamsOfLightPuzzleBoard.this.putTileInternal(t);
				
			}
			
			@Override
			public void visitNumberTile(NumberTile t) {
				BeamsOfLightPuzzleBoard.this.putTileInternal(t);
				BeamsOfLightPuzzleBoard.this.numberTiles.add(t);
			}
		});
	}

	private void putTileInternal(ITile tile) {
		tiles[tile.getX()][tile.getY()] = tile;
		tile.addChangeListener(this);
		tile.addUndoableEditListener(this);
	}

	@Override
	public void removeChangeListener(ChangeListener cl) {
		changeListeners.remove(cl);
	}
	
	@Override
	public void removeUndoableEditListener(UndoableEditListener ul) {
		undoableEditListeners.remove(ul);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				sb.append(getTileAt(x,y)+" ");
			}
			sb.append('\n');
		}

		return sb.toString();
	}

	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		// Propagate the event
		for(UndoableEditListener l: undoableEditListeners) {
			l.undoableEditHappened(e);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// Propagate the event
		for(ChangeListener l: changeListeners) {
			l.stateChanged(e);
		}	
	}
	public IBeamsOfLightPuzzleBoard clone()
	{
		IBeamsOfLightPuzzleBoard ret = new BeamsOfLightPuzzleBoard();
		ret.init(this.width,this.height);
		for(int h =0; h< this.height;h++)
		{
			for(int w = 0; w<this.width;w++)
			{
				ITile t = this.getTileAt(w, h);
				if(t instanceof NumberTile)
				{
					NumberTile num = (NumberTile)t;
					ret.putTile(new NumberTile(ret,num.getNumber(),w,h));
				}
				else if (t instanceof LightTile)
				{
					LightTile lig = (LightTile)t;
					ret.putTile(new LightTile(ret,w,h,lig.getTileState()));
				}
			}
		}
		
		return ret;
	}

}
