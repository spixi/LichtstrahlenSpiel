package de.bwvaachen.beamoflightgame.ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
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