package de.bwvaachen.beamoflightgame.ui;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import static de.bwvaachen.beamoflightgame.i18n.I18N._;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.undo.CannotUndoException;
import de.bwvaachen.beamoflightgame.controller.TurnUndoManager;

/**
 * 
 * A class that capsules the Undo-Button.
 */
public class UndoButton extends AbstractUndoRedoButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6212990560228610159L;

	public UndoButton(TurnUndoManager um) {
		super(um);
	}
	
	/**
	 * The action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
		undoManager.undo();
		}
		catch (CannotUndoException ex) {
			JOptionPane.showMessageDialog(null,_("UndoImpossible"));
		}
	}
	
	/**
	 * Enables or disables the Button and update the caption.
	 */	
	@Override
	protected void update() {
		this.setEnabled(undoManager.canUndo());
		this.setText(undoManager.getUndoPresentationName());
	}
}
