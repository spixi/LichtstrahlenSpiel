/**
* Copyright (C) 2013 Marius Spix, Bastian Winzen, Andreas Pauls, Christian Frühholz, Georg Braun
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

package de.bwvaachen.braun.beamoflightpuzzle.controller;

import java.awt.image.TileObserver;
import java.io.Serializable;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import de.bwvaachen.braun.beamoflightpuzzle.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.braun.beamoflightpuzzle.model.ILightTile;
import de.bwvaachen.braun.beamoflightpuzzle.model.ITile;
import de.bwvaachen.braun.beamoflightpuzzle.model.LightTileState;

public class Turn implements UndoableEdit, Serializable  {
	private boolean alive, hasBeenDone;
	private IBeamsOfLightPuzzleBoard board;
	private int x, y;
	private LightTileState oldTileState, newTileState;
	
	public Turn(IBeamsOfLightPuzzleBoard b, int x, int y, LightTileState oldTileState, LightTileState newTileState) {
		board        = b;
		this.x       = x;
		this.y       = y;
		this.oldTileState = oldTileState;
		this.newTileState = newTileState;
		alive       = true;
		hasBeenDone = true;
	}

	public boolean addEdit(UndoableEdit anEdit) {
		return false;
	}

	public boolean canRedo() {
		return alive && !hasBeenDone ? board.isPlacementOfTileStatePossible(newTileState, x, y) : false;
	}

	public boolean canUndo() {
		return alive && hasBeenDone ? board.isPlacementOfTileStatePossible(oldTileState, x, y) : false;
	}

	public void die() {
		alive = false;
	}

	public String getPresentationName() {
		// TODO
		return "Zug";
	}

	public String getRedoPresentationName() {
		return getPresentationName() + " wiederholen";
	}

	public String getUndoPresentationName() {
		return getPresentationName() + " rückgängig machen.";
	}

	public boolean isSignificant() {
		// TODO
		return true;
	}

	public void redo() throws CannotRedoException {
		if(!canRedo()) throw new CannotRedoException();
		hasBeenDone = true;
		ITile tile = board.getTileAt(x, y);
		((ILightTile) tile).setState(newTileState);
	}

	public boolean replaceEdit(UndoableEdit anEdit) {
		return false;
	}

	public void undo() throws CannotUndoException {
		if(!canUndo()) throw new CannotUndoException();
		hasBeenDone = false;
		ITile tile = board.getTileAt(x, y);
		((ILightTile) tile).setState(oldTileState);
	}

}
