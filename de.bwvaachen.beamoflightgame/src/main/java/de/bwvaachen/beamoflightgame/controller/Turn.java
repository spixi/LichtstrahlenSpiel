/**
* Copyright (C) 2013 Marius Spix, Bastian Winzen, Andreas Pauls, Christian Fruehholz, Georg Braun
*
* This file is part of the lichtspiel project.
*
* lichtspiel is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* lichtspiel is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with lichtspiel. If not, see <http://www.gnu.org/licenses/>.
*/

/**
* @file
* Turn class
*/

package de.bwvaachen.beamoflightgame.controller;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTileState;

/**
 * @author mspix
 * @author cfruehholz
 *
 */
public class Turn implements UndoableEdit  
{
	public static transient final int FLAG_ALIVE = 0x1, FLAG_HAS_BEEN_DONE = 0x2, FLAG_SIGNIFICANT = 0x4, FLAG_ERROR = 0x10;
	private int flags;
	private IBeamsOfLightPuzzleBoard board;
	private int x, y;
	private LightTileState oldTileState, newTileState;
	
	private void setFlag(int f) {
		flags |= f;
	}
	
	private void unsetFlag(int f) {
		flags &= ~f;
	}
	
	public Turn(IBeamsOfLightPuzzleBoard b, int x, int y, LightTileState oldTileState, LightTileState newTileState) {
		board        = b;
		this.x       = x;
		this.y       = y;
		this.oldTileState = oldTileState;
		this.newTileState = newTileState;
		flags |= FLAG_ALIVE;
		flags |= FLAG_HAS_BEEN_DONE;
		redo();
	}

	public int getFlags() {
		return flags;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public LightTileState getOldTileState() {
		return oldTileState;
	}

	public LightTileState getNewTileState() {
		return newTileState;
	}

	public boolean addEdit(UndoableEdit anEdit) {
		return false;
	}

	public boolean canRedo() {
		return (flags & FLAG_ALIVE | ~FLAG_HAS_BEEN_DONE) != 0;
	}

	public boolean canUndo() {
		return (flags & FLAG_ALIVE & FLAG_HAS_BEEN_DONE) != 0;
	}

	public void die() {
		unsetFlag(FLAG_ALIVE);
	}

	public String getPresentationName() {
		// TODO
		return "Zug";
	}

	public String getRedoPresentationName() {
		return getPresentationName() + " wiederholen";
	}

	public String getUndoPresentationName() {
		return getPresentationName() + " rueckgängig machen.";
	}
	
	public void mark() {
		setFlag(FLAG_SIGNIFICANT);
	}
	
	public void unmark() {
		unsetFlag(FLAG_SIGNIFICANT);
	}
	
	@Override
	public boolean isSignificant() {
		return (flags & FLAG_SIGNIFICANT) != 0;
	}

	public void redo() throws CannotRedoException {
		if(!canRedo()) throw new CannotRedoException();
		setFlag(FLAG_HAS_BEEN_DONE);
		ITile tile = board.getTileAt(x, y);
		((LightTile) tile).setState(newTileState);
	}

	public boolean replaceEdit(UndoableEdit anEdit) {
		return false;
	}

	public void undo() throws CannotUndoException {
		if(!canUndo()) throw new CannotUndoException();
		unsetFlag(FLAG_HAS_BEEN_DONE);
		ITile tile = board.getTileAt(x, y);
		((LightTile) tile).setState(oldTileState);
	}



}
