package de.bwvaachen.beamoflightgame.ui;

import java.awt.Graphics;

import javax.swing.undo.UndoManager;

public class UndoRedoButton extends java.awt.Button {
	private UndoManager undoManager;
	public UndoRedoButton(UndoManager um) {
		undoManager = um;
	}
	public void paint(Graphics g) {
		g.drawString(undoManager.getPresentationName(), 0, 0);
	}
}
