package de.bwvaachen.beamoflightgame.ui;

import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.undo.UndoManager;

public abstract class AbstractUndoRedoButton extends JButton implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8601760417647229746L;
	protected UndoManager undoManager;
	private final static String DUMMY_STRING = Character.toString('\u00A0');
	protected AbstractUndoRedoButton(UndoManager um) {
		undoManager = um;
		update();
		this.addActionListener(this);
	}
	@Override
	public final void paint(Graphics g) {
		update();
		if (this.getText().isEmpty()) {
			setText(DUMMY_STRING);
		}
		super.paint(g);
	}
	
	protected abstract void update();
}