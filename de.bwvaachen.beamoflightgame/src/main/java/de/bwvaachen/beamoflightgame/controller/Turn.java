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
	
	public Turn(IBeamsOfLightPuzzleBoard b, int x, int y, ITileState oldTileState, ITileState newTileState) {
		board        = b;
		this.x       = x;
		this.y       = y;
		this.oldTileState = oldTileState;
		this.newTileState = newTileState;
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
		//TODO
		return (flags & FLAG_ALIVE | ~FLAG_HAS_BEEN_DONE) != 0;
	}

	@Override
	public boolean canUndo() {
		System.out.println(flags & FLAG_ALIVE);
		return ((flags & ((FLAG_ALIVE | FLAG_HAS_BEEN_DONE))) != 0);
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
		return "Zug";
	}

	@Override
	public String getRedoPresentationName() {
		return String.format("%s wiederholen", getPresentationName());
	}

	@Override
	public String getUndoPresentationName() {
		return String.format("%s rÃ¼ckgÃ¤ngig machen", getPresentationName());
	}

	@Override
	public boolean isSignificant() {
		return (flags & FLAG_SIGNIFICANT) != 0;
	}

	@Override
	public void redo() throws CannotRedoException {
		if(!canRedo()) throw new CannotRedoException();
		setFlag(FLAG_HAS_BEEN_DONE);
		IChangeableTile tile = (IChangeableTile) board.getTileAt(x, y);
		tile.setState(newTileState);
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
		IChangeableTile tile = (IChangeableTile) board.getTileAt(x, y);
		synchronized(tile) {
			tile.setUndoMode(true);
			tile.setState(oldTileState);
			tile.setUndoMode(false);
		}
	}


	void unsetFlag(int f) {
		flags &= ~f;
	}


}
