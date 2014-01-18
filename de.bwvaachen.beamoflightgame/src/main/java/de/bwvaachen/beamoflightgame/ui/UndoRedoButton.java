package de.bwvaachen.beamoflightgame.ui;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import de.bwvaachen.beamoflightgame.controller.TurnUndoManager;

public class UndoRedoButton extends AbstractUndoRedoButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3752060571993629986L;

	public UndoRedoButton(TurnUndoManager um) {
		super(um);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
		undoManager.undoOrRedo();
		}
		catch (CannotUndoException ex) {
			JOptionPane.showMessageDialog(null,"RÃ¼ckgÃ¤ngig nicht mÃ¶glich");
		}
		catch (CannotRedoException ex) {
			JOptionPane.showMessageDialog(null,"Wiederholen nicht mÃ¶glich");
		}
	}
	
	@Override
	protected void update() {
		this.setEnabled(undoManager.canUndoOrRedo());
		this.setText(undoManager.getPresentationName());
	}
}
