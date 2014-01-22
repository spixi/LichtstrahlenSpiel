package de.bwvaachen.beamoflightgame.model;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/
import static de.bwvaachen.beamoflightgame.i18n.I18N.*;
import java.util.Hashtable;
import java.util.LinkedHashSet;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import de.bwvaachen.beamoflightgame.controller.Turn;
import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.Pair;

public abstract class AbstractTile<T extends ITileState> implements ITile<T> {
	protected final IBeamsOfLightPuzzleBoard board;
	private final int y, x;
	private final Class<T> allowedStateClass;
	private ITileState tileState;
	protected LinkedHashSet<ChangeListener> changeListeners;
	protected LinkedHashSet<UndoableEditListener> undoableEditListeners;
	private transient boolean undoMode = false;
	
	protected AbstractTile(final IBeamsOfLightPuzzleBoard board, final int x, final int y, Class<T> allowedStateClass) {
		this.board             = board;
		this.y               = y;
		this.x               = x;
		this.allowedStateClass = allowedStateClass;
		this.changeListeners   = new LinkedHashSet<ChangeListener>();
		this.undoableEditListeners   = new LinkedHashSet<UndoableEditListener>();
	}

	@Override
	public void addChangeListener(ChangeListener cl) {
		changeListeners.add(cl);
	}
	
	@Override
	public void addUndoableEditListener(UndoableEditListener ul) {
		undoableEditListeners.add(ul);
	}
	
	public final boolean equals(ITile<?> t)
	{
		//Never redefine this method in a derivated class!
		return t.getTileState().equals(this.getTileState());
	}
	
	@Override
	public IBeamsOfLightPuzzleBoard getBoard() {
		return board;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getTileState() {
		return (T)tileState;
	}
	
	@Override
	public BoardTraverser getTraverser() {
		return new BoardTraverser(this);
	}
	
	@Override
	public final int getX() {
		return x;
	}
	
	@Override
	public final int getY() {
		return y;
	}
	
	@Override
	public boolean isStateChangeable() {
		return this instanceof IChangeableTile;
	}
	
	@Override
	public void put() {
		board.putTile(this);
	}
	
	@Override
	public void removeChangeListener(ChangeListener cl) {
		changeListeners.remove(cl);
	}
	
	@Override
	public void removeUndoableEditListener(UndoableEditListener ul) {
		undoableEditListeners.remove(ul);
	}
	
	public boolean isStateAllowed(Class<? extends LightTileState> stateClass) {
		return allowedStateClass.isAssignableFrom(stateClass);
	}
	
	public final void restoreState(Hashtable<?, ?> state) {
		if (! isStateChangeable()) throw new UnsupportedOperationException();
		ITileState received      = (ITileState) state.get("state");
        
		if(received == null) {
			return;
		}
		
		Class<?>   receivedClass = received.getClass();
		
		if (! isStateAllowed((Class<? extends LightTileState>) receivedClass))
			throw new IllegalStateException(_f("AbstractTileStateClassNotAllowed", receivedClass, allowedStateClass));
		tileState = received;
	}
	protected final void setTileState(T newState, boolean significant) {
		ITileState oldState  = this.tileState;
		this.tileState       = newState;
		for(ChangeListener l : changeListeners) {
			l.stateChanged(new ChangeEvent(new Pair<ITileState,ITile<T>>(oldState, this)));
		}
		
		if (undoMode) return; // Don't raise an event in UndoMode!!!
		
		for (UndoableEditListener l : undoableEditListeners) {
			Turn t = new Turn (board, x, y, oldState, newState);
			t.setSignificant(significant);
			l.undoableEditHappened(
					new UndoableEditEvent(this, t)
			);
		}
	}
	
	@Override
	public void setUndoMode(boolean onOff) {
		undoMode = onOff;
	}
	
	public final void storeState(Hashtable<Object, Object> state) {
		if (! isStateChangeable()) throw new UnsupportedOperationException();
		state.put("state", tileState);
	}
	
	@Override
	public String toString() {
		return getTileState().toString();
	}
}
