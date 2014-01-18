package de.bwvaachen.beamoflightgame.ui;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.undo.UndoManager;

import de.bwvaachen.beamoflightgame.controller.TurnUndoManager;

//AbstractButton
//|__JMenuItem
//|__JButton
public abstract class AbstractUndoRedoButton extends JButton implements ActionListener, ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8601760417647229746L;
	protected UndoManager undoManager;
	protected AbstractUndoRedoButton(TurnUndoManager um) {
		undoManager = um;
		um.addChangeListener(this);
		update();
		this.addActionListener(this);
	}
	
	@Override
	public final void paint(Graphics g) {
		update();
		super.paint(g);
	}
	
	protected abstract void update();

	@Override
	public void stateChanged(ChangeEvent e) {
		update();
	}
}