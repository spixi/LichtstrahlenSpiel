package de.bwvaachen.beamoflightgame.controller;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import static de.bwvaachen.beamoflightgame.i18n.I18N.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.IChangeableTile;
import de.bwvaachen.beamoflightgame.model.ITileState;

/**
 * @author mspix
 * @author cfruehholz
 *
 */
public class Turn implements UndoableEdit  
{
	public static transient final int FLAG_ALIVE = 0x1, FLAG_HAS_BEEN_DONE = 0x2, FLAG_SIGNIFICANT = 0x4, FLAG_ERROR = 0x10, FLAG_MARKER = 0x20;
	
	private int flags;
	private IBeamsOfLightPuzzleBoard board;
	private int x, y;
	private ITileState oldTileState, newTileState;
	private int turnNumber = 0 ;
	
	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	public Turn(IBeamsOfLightPuzzleBoard b, int x, int y, ITileState oldTileState, ITileState newTileState, int turnNumber) {
		board        = b;
		this.x       = x;
		this.y       = y;
		this.oldTileState = oldTileState;
		this.newTileState = newTileState;
		this.turnNumber = turnNumber ; 
		flags |= FLAG_ALIVE;
		flags |= FLAG_HAS_BEEN_DONE;
		flags |= FLAG_SIGNIFICANT;
	}
	
	@Override
	public boolean addEdit(UndoableEdit anEdit) {
		return false;
	}
	
	@Override
	public boolean canRedo() {
		return hasFlag(FLAG_ALIVE) && !hasFlag(FLAG_HAS_BEEN_DONE);
	}

	@Override
	public boolean canUndo() {
		return hasFlag(FLAG_ALIVE | FLAG_HAS_BEEN_DONE);
	}

	@Override
	public void die() {
		unsetFlag(FLAG_ALIVE);
	}

	public int getFlags() {
		return flags;
	}

	@Override
	public String getPresentationName() {
		// TODO
		return _f("TurnNo", 0);
	}

	@Override
	public String getRedoPresentationName() {
		return _f("RepeatTurn", getPresentationName());
	}

	@Override
	public String getUndoPresentationName() {
		return _f("UndoTurn", getPresentationName());
	}
	
	public boolean hasFlag(int flag) {
		return (flags&flag) == flag;
	}

	@Override
	public boolean isSignificant() {
		return hasFlag(FLAG_SIGNIFICANT);
	}

	@Override
	public void redo() throws CannotRedoException {
		if(!canRedo()) throw new CannotRedoException();
		setFlag(FLAG_HAS_BEEN_DONE);
		changeTile(newTileState);
	}
	
	@Override
	public boolean replaceEdit(UndoableEdit anEdit) {
		return false;
	}

	void setFlag(int f) {
		flags |= f;
	}

	@Override
	public String toString() {
		final char delimiter = ' ';
		StringBuffer buf = new StringBuffer();
		buf.append(x);
		buf.append(delimiter);
		buf.append(y);
		buf.append(delimiter);
		buf.append(oldTileState);
		buf.append(delimiter);
		buf.append(newTileState);
		buf.append(delimiter);
		buf.append(flags);
		return buf.toString();
	}

	@Override
	public void undo() throws CannotUndoException {
		if(!canUndo()) throw new CannotUndoException();
		unsetFlag(FLAG_HAS_BEEN_DONE);
		changeTile(oldTileState);
	}
	
	private synchronized void changeTile(ITileState state) {
			IChangeableTile tile = (IChangeableTile) board.getTileAt(x, y);
			tile.setUndoMode(true);
			tile.setState(state,this.hasFlag(FLAG_SIGNIFICANT));
			tile.setUndoMode(false);
	}


	void unsetFlag(int f) {
		flags &= ~f;
	}

	public void setSignificant(boolean significant) {
		if (significant)
			setFlag(FLAG_SIGNIFICANT);
		else
			unsetFlag(FLAG_SIGNIFICANT);
		
	}


}
